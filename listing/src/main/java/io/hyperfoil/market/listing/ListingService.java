package io.hyperfoil.market.listing;

import io.hyperfoil.market.listing.client.OfferingAndContactInfo;
import io.hyperfoil.market.listing.client.OfferingDetails;
import io.hyperfoil.market.listing.client.OfferingList;
import io.hyperfoil.market.listing.client.OfferingOverview;
import io.hyperfoil.market.listing.model.VehicleFeature;
import io.hyperfoil.market.listing.model.VehicleOffer;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.Cache;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Collections;

@Path("/")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ListingService {
    private static final Logger log = Logger.getLogger(ListingService.class);

    @PersistenceContext(unitName = "listing")
    EntityManager em;

    @GET
    @Path("/list")
    public OfferingList list(@QueryParam("page") int page, @QueryParam("perPage") int perPage) {
        return new OfferingList(
                page,
                perPage,
                em.createNamedQuery(VehicleOffer.QUERY_COUNT, Number.class).getSingleResult().intValue(),
                em.createNamedQuery(VehicleOffer.QUERY_OVERVIEW, OfferingOverview.class).setFirstResult(page * perPage).setMaxResults(perPage).getResultList()
        );
    }

    @GET
    @Path("/offering/{id}")
    public OfferingDetails get(@PathParam("id") long id) {
        VehicleOffer vehicleOffer = em.find(VehicleOffer.class, id, Collections.singletonMap("javax.persistence.fetchgraph", em.getEntityGraph(VehicleOffer.WITH_GALLERY)));
        if (vehicleOffer == null) {
            throw new EntityNotFoundException();
        } else {
            return new OfferingDetails(vehicleOffer);
        }
    }

    @POST
    @Path("/offering")
    public void create(@HeaderParam("authorization") String authorization, OfferingAndContactInfo requestBody) {
        String token = null;
        if (authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring("Bearer ".length());
        }
        // TODO fetch data from user service and fill in contact details automatically
        log.info("Received new offering, token: " + token);
    }

    @GET
    @Path("/allfeatures")
    @Cache
    public Collection<VehicleFeature> allFeatures() {
        return em.createNamedQuery(VehicleFeature.QUERY_ALL, VehicleFeature.class).getResultList();
    }
}

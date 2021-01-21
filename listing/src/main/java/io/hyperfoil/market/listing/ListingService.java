package io.hyperfoil.market.listing;

import io.hyperfoil.market.listing.client.OfferingAndContactInfo;
import io.hyperfoil.market.vehicle.dto.Offering;
import io.hyperfoil.market.vehicle.dto.OfferingList;
import io.hyperfoil.market.vehicle.model.VehicleFeature;
import io.hyperfoil.market.vehicle.repository.VehicleListingRepository;
import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.Cache;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

@Path("/")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ListingService {
    private static final Logger log = Logger.getLogger(ListingService.class);

    @Inject
    VehicleListingRepository repository;

    @GET
    @Path("/list")
    public OfferingList list(@QueryParam("page") int page, @QueryParam("perPage") int perPage) {
        return repository.allOfferings(page, perPage);
    }

    @GET
    @Path("/offering/{id}")
    public Offering get(@PathParam("id") long id) {
        return repository.findById(id);
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
        return repository.allFeatures();
    }
}

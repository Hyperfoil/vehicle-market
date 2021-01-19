package io.hyperfoil.market.listing;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.Cache;

import io.hyperfoil.market.listing.client.GalleryItem;
import io.hyperfoil.market.listing.client.Offering;
import io.hyperfoil.market.listing.client.OfferingList;
import io.hyperfoil.market.listing.client.OfferingAndContactInfo;
import io.hyperfoil.market.vehicle.model.VehicleDescription;
import io.hyperfoil.market.vehicle.model.VehicleFeature;

@Path("/")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ListingService {
    private static final Logger log = Logger.getLogger(ListingService.class);
    private static final Offering O1 = new Offering();
    private static final Offering O2 = new Offering();

    static {
        VehicleDescription d1 = new VehicleDescription(
                "ACME Cars",
                "Fooryon",
                2018,
                "Manual 6-speed",
                "Front-Wheel Drive",
                "2.0 CDTi, 123kW",
                "hatchback",
                "Diesel",
                5,
                "EURO6a"
        );

        O1.id = 1L;
        O1.model = d1;
        O1.trimLevel = "Barneo";
        O1.mileage = 12345;
        O1.year = 2019;
        O1.color = "Dark Red";
        O1.prevOwners = 1;
        O1.inspectionValidUntil = new Date(2021, 6, 27);
        O1.addFeature(VehicleFeature.ABS).addFeature(VehicleFeature.RAIN_SENSOR).addFeature(VehicleFeature.SPORT_SEATS).addFeature(VehicleFeature.STOP_START);
        O1.gallery.add(new GalleryItem("/images/car?type=hatchback&color=%23b52828", "Front image"));
        O1.gallery.add(new GalleryItem("/images/car?type=sedan&color=%23b52828", "Front image"));

        VehicleDescription d2 = new VehicleDescription(
                "Diabolico",
                "Daredevil",
                2012,
                "Automatic 666-speed",
                "Rear-Wheel Drive",
                "4.0, 666kW",
                "Supercar",
                "Gasoline",
                1,
                "EURO1a"
        );

        O2.id = 2L;
        O2.model = d2;
        O2.mileage = 66666;
        O2.year = 2012;
        O2.color = "Evil Pink";
        O2.prevOwners = 1;
        O2.history = "Blablabla...";
        O2.gallery.add(new GalleryItem("/images/car?type=supercar&color=%23ff9cfa", "See this beast!"));
    }

    @GET
    @Path("/list")
    public OfferingList list(@QueryParam("page") int page, @QueryParam("perPage") int perPage) {
        return new OfferingList(page, perPage, 123, Arrays.asList(O1, O2));
    }

    @GET
    @Path("/offering/{id}")
    public Offering get(@PathParam("id") long id) {
        if (id == 1) return O1;
        if (id == 2) return O2;
        throw new WebApplicationException(404);
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
    public List<VehicleFeature> allFeatures() {
        return VehicleFeature.ALL;
    }
}

package io.hyperfoil.market.listing;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import io.hyperfoil.market.listing.client.GalleryItem;
import io.hyperfoil.market.listing.client.Offering;
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
        VehicleDescription d1 = new VehicleDescription();
        d1.setId(1L);
        d1.setMake("ACME Cars");
        d1.setModel("Fooryon");
        d1.setYear(2018);
        d1.setTrany("Manual 6-speed");
        d1.setDrive("Front-Wheel Drive");
        d1.setEngine("2.0 CDTi, 123kW");
        d1.setVClass("hatchback");
        d1.setFuel("Diesel");
        d1.setSeats(5);
        d1.setEmissions("EURO6a");

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

        VehicleDescription d2 = new VehicleDescription();
        d2.setId(2L);
        d2.setMake("Diabolico");
        d2.setModel("Daredevil");
        d2.setYear(2012);
        d2.setTrany("Automatic 666-speed");
        d2.setDrive("Rear-Wheel Drive");
        d2.setEngine("4.0, 666kW");
        d2.setVClass("Supercar");
        d2.setFuel("Gasoline");
        d2.setSeats(1);

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
    public Collection<Offering> list(@QueryParam("page") int page, @QueryParam("perPage") int perPage) {
        return Arrays.asList(O1, O2);
    }

    @GET
    @Path("/offering/{id}")
    public Offering get(@PathParam("id") long id) {
        if (id == 1) return O1;
        if (id == 2) return O2;
        throw new WebApplicationException(404);
    }

}

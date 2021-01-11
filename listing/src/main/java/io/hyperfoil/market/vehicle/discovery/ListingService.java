package io.hyperfoil.market.vehicle.discovery;

import java.util.Collection;
import java.util.Collections;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.logging.Logger;

import io.hyperfoil.market.vehicle.model.VehicleOffer;

@Path("/")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ListingService {
    private static final Logger log = Logger.getLogger(ListingService.class);

    @GET
    @Path("/list")
    public Collection<VehicleOffer> list(@QueryParam("page") int page, @QueryParam("perPage") int perPage) {
        return Collections.emptyList();
    }

}

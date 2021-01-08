package io.hyperfoil.market.vehicle.discovery;

import io.hyperfoil.market.vehicle.repository.VehicleDescriptionRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/vehicle")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VehicleDiscoveryService {

    private static final Logger logger = Logger.getLogger(VehicleDiscoveryService.class.getName());

    @Inject
    VehicleDescriptionRepository repository;

    /**
     * Gets a vehicle with the given id.
     *
     * @param id the id to search
     * @return A response object containing the vehicle.
     */
    @GET
    @Path("/id/{id}")
    public Response byId(@PathParam("id") Long id) {
        try {
            return Response.ok(repository.findById(id)).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving vehicle ", e);
            return Response.serverError().build();
        }
    }

}

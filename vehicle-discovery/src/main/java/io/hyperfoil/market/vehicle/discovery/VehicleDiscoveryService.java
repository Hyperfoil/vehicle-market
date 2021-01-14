package io.hyperfoil.market.vehicle.discovery;

import io.hyperfoil.market.vehicle.repository.VehicleDescriptionRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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

    /**
     * Gets a list of all of the vehicle models for a given make.
     *
     * @param  make  the make to search
     * @return A response object containing a list of models.
     */
    @GET
    @Path("/models/{make}")
    public Response getModels(@PathParam("make") String make) {
        try {
            return Response.ok(repository.getModels(make)).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE,"Error retrieving the list of models ", e);
            return Response.serverError().build();
        }
    }

    /**
     * Gets a list of all of the vehicle makes (manufactures).
     *
     * @return A response object containing a list of makes.
     */
    @GET
    @Path("/makes")
    public Response makes() {
        try {
            return Response.ok(repository.getMakes()).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving makes ", e);
            return Response.serverError().build();
        }
    }

    /**
     * Gets a list of all of the vehicle years.
     *
     * @return A response object containing a list of years.
     */
    @GET
    @Path("/years")
    public Response getYears() {
        try {
            return Response.ok(repository.getYears()).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE, "Error retrieving the list of years ", e);
            return Response.serverError().build();
        }
    }
    /**
     * Gets a list of all of the vehicle transmissions.
     *
     * @return A response object containing a list of transmissions.
     */
    @GET
    @Path("/transmissions")
    public Response getTransmissions() {
        try {
            return Response.ok(repository.getTransmissions()).build();
        } catch (Exception e) {
            logger.info("Error retrieving the list of transmissions " + e.getMessage());
            return Response.serverError().build();
        }
    }

    /**
     * Gets a list of all of the vehicle drivetrains.
     *
     * @return A response object containing a list of drivetrains.
     */
    @GET
    @Path("/drivetrains")
    public Response getDrivetrains() {
        try {
            return Response.ok(repository.getDrivetrains()).build();
        } catch (Exception e) {
            logger.info("Error retrieving the list of drivetrains " + e.getMessage());
            return Response.serverError().build();
        }
    }


    /**
     * Gets a list of all of the vehicle classes.
     *
     * @return A response object containing a list of vehicle classes.
     */
    @GET
    @Path("/classes")
    public Response getClasses() {
        try {
            return Response.ok(repository.getClasses()).build();
        } catch (Exception e) {
            logger.info("Error retrieving the list of classes " + e.getMessage());
            return Response.serverError().build();
        }
    }

    /**
     * Gets a count of vehicle descriptions.
     *
     * @return A response object containing a list of years as integer objects.
     */
    @GET
    @Path("/count")
    public Response getCount() {
        try {
            return Response.ok(repository.count()).build();
        } catch (Exception e) {
            logger.info("Error retrieving the count of vehicle descriptions" + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Searches for a vehicle or list of vehicles from provided search criteria.
     * The search criteria is passed in as query parameters to the REST service.
     * All of the search parameters are optional
     *
     * @return A response object containing a list of of vehicles.
     */
    @GET
    @Path("search")
    public Response search(@Context UriInfo info) {
        try {
            return Response.ok(repository.search(info.getQueryParameters())).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE,"Error retrieving the list of vehicles ", e);
            return Response.serverError().build();
        }
    }
}

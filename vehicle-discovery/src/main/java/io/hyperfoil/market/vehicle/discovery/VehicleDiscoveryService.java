package io.hyperfoil.market.vehicle.discovery;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.hyperfoil.market.vehicle.discovery.VehicleDescription.*;
import static javax.ws.rs.core.Response.ok;

@Path("/vehicle")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VehicleDiscoveryService {

    private static final Logger logger = Logger.getLogger(VehicleDiscoveryService.class.getName());

    @PersistenceContext(unitName = "discovery")
    EntityManager em;

    /**
     * Gets a vehicle with the given id.
     *
     * @param id the id to search
     * @return A vehicle description.
     */
    @GET
    @Path("/id/{id}")
    public VehicleDescription byId(@PathParam("id") Long id) {
        return em.find(VehicleDescription.class, id);
    }

    /**
     * Gets a list of all of the vehicle models for a given make.
     *
     * @param  make  the make to search
     * @return A list of models.
     */
    @GET
    @Path("/models/{make}")
    public Collection<String> getModels(@PathParam("make") String make) {
        return em.createNamedQuery(DISTINCT_MODELS, String.class).setParameter("m", make).getResultList();
    }

    /**
     * Gets a list of all of the vehicle makes (manufactures).
     *
     * @return A list of makes.
     */
    @GET
    @Path("/makes")
    public Collection<String> makes() {
        return em.createNamedQuery(DISTINCT_MAKES, String.class).getResultList();
    }

    /**
     * Gets a list of all of the vehicle years.
     *
     * @return A list of years.
     */
    @GET
    @Path("/years")
    public Collection<Integer> getYears() {
        return em.createNamedQuery(DISTINCT_YEARS, Integer.class).getResultList();
    }

    /**
     * Gets a list of all of the vehicle transmissions.
     *
     * @return A list of transmissions.
     */
    @GET
    @Path("/transmissions")
    public Collection<String> getTransmissions() {
        return em.createNamedQuery(DISTINCT_TRANSMISSIONS, String.class).getResultList();
    }

    /**
     * Gets a list of all of the vehicle drivetrains.
     *
     * @return A list of drivetrains.
     */
    @GET
    @Path("/drivetrains")
    public Collection<String> getDrivetrains() {
        return em.createNamedQuery(DISTINCT_DRIVERTRAIN, String.class).getResultList();
    }


    /**
     * Gets a list of all of the vehicle classes.
     *
     * @return A list of vehicle classes.
     */
    @GET
    @Path("/classes")
    public Collection<String> getClasses() {
        return em.createNamedQuery(DISTINCT_VCLASS, String.class).getResultList();
    }

    /**
     * Gets a count of vehicle descriptions.
     *
     * @return The number of total vehicle descriptions.
     */
    @GET
    @Path("/count")
    public Long getCount() {
        return em.createNamedQuery(QUERY_COUNT, Number.class).getSingleResult().longValue();
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
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<VehicleDescription> query = builder.createQuery(VehicleDescription.class);
            Root<VehicleDescription> vehicle = query.from(VehicleDescription.class);

            List<Predicate> restrictions = new ArrayList<>();
            for (Map.Entry<String, List<String>> entry : info.getQueryParameters().entrySet()) {
                switch (entry.getKey()) {
                    case "make":
                        restrictions.add(builder.equal(vehicle.<String>get("make"), entry.getValue().stream().findFirst().orElse("") ));
                        break;
                    case "model":
                        restrictions.add(builder.equal(vehicle.<String>get("model"), entry.getValue().stream().findFirst().orElse("") ));
                        break;
                    case "fuel":
                        restrictions.add(builder.equal(vehicle.<String>get("fuel"), entry.getValue().stream().findFirst().orElse("") ));
                        break;
                    case "year":
                        restrictions.add(builder.equal(vehicle.<Integer>get("year"), entry.getValue().stream().findFirst().stream().findFirst().map(Integer::parseInt).orElse(0) ));
                        break;
                    case "drivetrain":
                        restrictions.add(builder.equal(vehicle.<String>get("drive"), entry.getValue().stream().findFirst().stream().findFirst().orElse("") ));
                        break;
                    case "transmission":
                        restrictions.add(builder.equal(vehicle.<String>get("trany"), entry.getValue().stream().findFirst().stream().findFirst().orElse("") ));
                        break;
                    case "vclass":
                        restrictions.add(builder.equal(vehicle.<String>get("vClass"), entry.getValue().stream().findFirst().stream().findFirst().orElse("") ));
                        break;
                    case "version":
                        // ignore the version field for searching.
                        break;
                    default:
                        logger.warning("Unknown Query Parameter encountered! key=" + entry.getKey() + " value=" + entry.getValue().get(0));
                        break;
                }
            }
            return ok(em.createQuery(query.where(restrictions.toArray(new Predicate[] {}))).getResultList()).build();
        }
        catch (Exception e) {
            logger.log(Level.SEVERE,"Error retrieving the list of vehicles ", e);
            return Response.serverError().build();
        }
    }
}

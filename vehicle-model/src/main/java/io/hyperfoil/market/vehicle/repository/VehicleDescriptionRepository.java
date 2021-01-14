package io.hyperfoil.market.vehicle.repository;

import io.hyperfoil.market.vehicle.model.VehicleDescription;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RequestScoped
public class VehicleDescriptionRepository {

    private static final Logger logger = Logger.getLogger(VehicleDescriptionRepository.class.getName());

    @PersistenceContext(unitName = "vehicle")
    EntityManager entityManager;

    /**
     * Saves a description to database. Used on load operations.
     */
    public void addVehicle(VehicleDescription description) {
        entityManager.persist(description);
    }

    /**
     * Gets a vehicle with the given id.
     */
    public VehicleDescription findById(Long id) {
        return entityManager.find(VehicleDescription.class, id);
    }

    /**
     * Gets a list of all of the vehicle makes (manufactures).
     */
    public Collection<String> getMakes(){
        return entityManager.createNamedQuery(VehicleDescription.DISTINCT_MAKES, String.class).getResultList();
    }

    /**
     * Gets a list of all of the vehicle models for a given make.
     */
    public Collection<String> getModels(String make){
        return entityManager.createNamedQuery(VehicleDescription.DISTINCT_MODELS, String.class).setParameter("m", make).getResultList();
    }

    /**
     * Gets a list of all of the vehicle years.
     */
    public Collection<Integer> getYears(){
        return entityManager.createNamedQuery(VehicleDescription.DISTINCT_YEARS, Integer.class).getResultList();
    }

    /**
     * Gets a list of all of the vehicle transmissions.
     */
    public Collection<String> getTransmissions(){
        return entityManager.createNamedQuery(VehicleDescription.DISTINCT_TRANSMISSIONS, String.class).getResultList();
    }

    /**
     * Gets a list of all of the vehicle drivetrains.
     */
    public Collection<String> getDrivetrains(){
        return entityManager.createNamedQuery(VehicleDescription.DISTINCT_DRIVERTRAIN, String.class).getResultList();
    }

    /**
     * Gets a list of all of the vehicle classes.
     */
    public Collection<String> getClasses(){
        return entityManager.createNamedQuery(VehicleDescription.DISTINCT_VCLASS, String.class).getResultList();
    }

    /**
     * Gets a count of vehicle description records in the database.
     */
    public Long count(){
        return entityManager.createNamedQuery(VehicleDescription.QUERY_COUNT, Number.class).getSingleResult().longValue();
    }

    /**
     * Searches for a vehicle or list of vehicles from provided search criteria.
     * The search criteria is passed in as a vehicle object.
     * Only the fields of the passed in object that set will be used for searching for the vehicle.
     * This method ignores the id field, use the getVehicle method instead.
     *
     * @param  searchFields  the Vehicle object containing the attributes to be searched
     */
    public Collection<VehicleDescription> search(Map<String, List<String>> searchFields){
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VehicleDescription> query = builder.createQuery(VehicleDescription.class);
        Root<VehicleDescription> vehicle = query.from(VehicleDescription.class);

        List<Predicate> restrictions = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : searchFields.entrySet()) {
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
        return entityManager.createQuery(query.where(restrictions.toArray(new Predicate[] {}))).getResultList();
    }

}

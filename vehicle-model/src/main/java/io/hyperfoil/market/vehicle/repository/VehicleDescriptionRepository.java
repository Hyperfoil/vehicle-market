package io.hyperfoil.market.vehicle.repository;

import io.hyperfoil.market.vehicle.model.VehicleDescription;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@RequestScoped
public class VehicleDescriptionRepository {

    @PersistenceContext(unitName = "vehicle")
    EntityManager entityManager;

    /**
     * Saves a description to database. Used on load operations.
     */
    public void addVehicle(VehicleDescription description) {
        entityManager.persist(description);
    }

    /**
     * Gets a list of all of the vehicle makes (manufactures).
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

}

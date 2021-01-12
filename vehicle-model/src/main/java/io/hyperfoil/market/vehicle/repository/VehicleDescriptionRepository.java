package io.hyperfoil.market.vehicle.repository;

import io.hyperfoil.market.vehicle.model.VehicleDescription;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequestScoped
public class VehicleDescriptionRepository {

    @PersistenceContext(unitName = "vehicle")
    EntityManager entityManager;

    public VehicleDescription save(VehicleDescription description) {
        if (description.getId() == null) {
            entityManager.persist(description);
        } else {
            description = entityManager.merge(description);
        }
        return description;
    }
    
    public VehicleDescription findById(Long id) {
        return entityManager.find(VehicleDescription.class, id);
    }

}

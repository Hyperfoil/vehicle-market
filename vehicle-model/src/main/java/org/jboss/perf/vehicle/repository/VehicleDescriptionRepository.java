package org.jboss.perf.vehicle.repository;

import org.jboss.perf.vehicle.model.VehicleDescription;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequestScoped
public class VehicleDescriptionRepository {

    @PersistenceContext(unitName = "vehicle-description")
    private EntityManager entityManager;

    public VehicleDescription findById(Long id) {
        return entityManager.find(VehicleDescription.class, id);
    }

}

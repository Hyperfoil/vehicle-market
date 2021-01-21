package io.hyperfoil.market.vehicle.repository;

import io.hyperfoil.market.vehicle.dto.Offering;
import io.hyperfoil.market.vehicle.dto.OfferingList;
import io.hyperfoil.market.vehicle.model.VehicleFeature;
import io.hyperfoil.market.vehicle.model.VehicleOffer;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

@RequestScoped
public class VehicleListingRepository {

    private static final Logger logger = Logger.getLogger(VehicleListingRepository.class.getName());

    @PersistenceContext(unitName = "vehicle")
    EntityManager entityManager;

    /**
     * Gets a vehicle offering with the given id.
     */
    public Offering findById(Long id) {
        VehicleOffer vehicleOffer = entityManager.find(VehicleOffer.class, id, Collections.singletonMap("javax.persistence.fetchgraph", entityManager.getEntityGraph(VehicleOffer.WITH_GALLERY)));
        if (vehicleOffer == null) {
            throw new EntityNotFoundException();
        } else {
            return new Offering(vehicleOffer);
        }
    }

    /**
     * Gets a range of vehicle offerings.
     */
    public OfferingList allOfferings(int page, int pageSize) {
        return new OfferingList(
                page,
                pageSize,
                entityManager.createNamedQuery(VehicleOffer.QUERY_COUNT, Number.class).getSingleResult().intValue(),
                entityManager.createNamedQuery(VehicleOffer.QUERY_FOR_DTO, VehicleOffer.class).setFirstResult(page * pageSize).setMaxResults(pageSize).getResultList()
        );
    }

    /**
     * Gets a collection of all VehicleFeatures
     */
    public Collection<VehicleFeature> allFeatures() {
        return entityManager.createNamedQuery(VehicleFeature.QUERY_ALL, VehicleFeature.class).getResultList();
    }

}

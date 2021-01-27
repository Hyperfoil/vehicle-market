package io.hyperfoil.market.listing.client;

import io.hyperfoil.market.listing.model.VehicleFeature;
import io.hyperfoil.market.listing.model.VehicleGalleryItem;
import io.hyperfoil.market.listing.model.VehicleOffer;

import java.util.Collection;
import java.util.Date;

/**
 * Client representation of {@link VehicleOffer}
 */
public class OfferingDetails {
    public OfferingOverview overview;
    public int prevOwners;
    public Date inspectionValidUntil;
    public String history;
    public Collection<VehicleFeature> features;
    public Collection<VehicleGalleryItem> gallery;

    public OfferingDetails() {
        // public empty constructor
    }
    
    public OfferingDetails(VehicleOffer offer) {
        this.overview = new OfferingOverview(offer);
        this.prevOwners = offer.prevOwners;
        this.inspectionValidUntil = offer.inspectionValidUntil;
        this.history = offer.history;
        this.features = offer.features;
        this.gallery = offer.gallery;
    }
}

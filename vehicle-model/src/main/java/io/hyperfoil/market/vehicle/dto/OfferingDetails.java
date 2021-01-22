package io.hyperfoil.market.vehicle.dto;

import io.hyperfoil.market.vehicle.model.VehicleDescription;
import io.hyperfoil.market.vehicle.model.VehicleFeature;
import io.hyperfoil.market.vehicle.model.VehicleGalleryItem;
import io.hyperfoil.market.vehicle.model.VehicleOffer;

import java.util.Collection;
import java.util.Date;

/**
 * Client representation of {@link io.hyperfoil.market.vehicle.model.VehicleOffer}
 */
public class OfferingDetails {
    public long id;
    public long price;
    public VehicleDescription model;
    public String trimLevel;
    public long mileage;
    public int year;
    public String color;
    public int prevOwners;
    public Date inspectionValidUntil;
    public String history;
    public Collection<VehicleFeature> features;
    public Collection<VehicleGalleryItem> gallery;

    public OfferingDetails(VehicleOffer offer) {
        this.id = offer.id;
        this.price = offer.price;
        this.model = offer.model;
        this.trimLevel = offer.trimLevel;
        this.mileage = offer.mileage;
        this.year = offer.year;
        this.color = offer.colorDescription;
        this.prevOwners = offer.prevOwners;
        this.inspectionValidUntil = offer.inspectionValidUntil;
        this.history = offer.history;
        this.features = offer.features;
        this.gallery = offer.gallery;
    }
}

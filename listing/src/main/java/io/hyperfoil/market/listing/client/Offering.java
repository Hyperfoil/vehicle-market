package io.hyperfoil.market.listing.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import io.hyperfoil.market.vehicle.model.VehicleDescription;
import io.hyperfoil.market.vehicle.model.VehicleFeature;

/**
 * Client representation of {@link io.hyperfoil.market.vehicle.model.VehicleOffer}
 */
public class Offering {
   public long id;
   public VehicleDescription model;
   public String trimLevel;
   public long mileage;
   public int year;
   public String color;
   public int prevOwners;
   public Date inspectionValidUntil;
   public String history;
   public Collection<VehicleFeature> features = new ArrayList<>();
   public Collection<GalleryItem> gallery = new ArrayList<>();

   public Offering addFeature(VehicleFeature feature) {
      features.add(feature);
      return this;
   }
}

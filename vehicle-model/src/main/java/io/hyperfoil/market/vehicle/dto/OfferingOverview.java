package io.hyperfoil.market.vehicle.dto;

import io.hyperfoil.market.vehicle.model.VehicleGalleryItem;
import io.hyperfoil.market.vehicle.model.VehicleOffer;

/**
 * Client representation of {@link VehicleOffer}
 */
public class OfferingOverview {
    public long id;
    public long price;
    public String make;
    public String modelName;
    public String trany;
    public String fuel;
    public String emissions;
    public String engine;
    public String imageURL;
    public String trimLevel;
    public String history;
    public long mileage;
    public int year;
    public String color;

    public OfferingOverview(long id, long price, String make, String modelName, String trany, String fuel, String emissions, String engine, String imageURL, String trimLevel, String history, long mileage, int year, String color) {
        this.id = id;
        this.price = price;
        this.make = make;
        this.modelName = modelName;
        this.trany = trany;
        this.fuel = fuel;
        this.emissions = emissions;
        this.engine = engine;
        this.imageURL = imageURL;
        this.trimLevel = trimLevel;
        this.history = history;
        this.mileage = mileage;
        this.year = year;
        this.color = color;
    }
}

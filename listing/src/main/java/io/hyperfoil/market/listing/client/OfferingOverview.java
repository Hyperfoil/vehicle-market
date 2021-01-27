package io.hyperfoil.market.listing.client;

import io.hyperfoil.market.listing.model.VehicleOffer;

/**
 * Client representation of {@link VehicleOffer}
 */
public class OfferingOverview {
    public long id;
    public String make;
    public String model;
    public String trany;
    public String fuel;
    public String emissions;
    public String engine;
    public String imageURL;
    public String trimLevel;
    public String history;
    public String vClass;
    public long mileage;
    public int year;
    public int seats;
    public String color;

    public OfferingOverview() {
        // public empty constructor
    }

    public OfferingOverview(long id, String make, String model, String trany, String vClass, String fuel, int seats, String emissions, String engine, String imageURL, String trimLevel, String history, long mileage, int year, String color) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.trany = trany;
        this.vClass = vClass;
        this.fuel = fuel;
        this.seats = seats;
        this.emissions = emissions;
        this.engine = engine;
        this.imageURL = imageURL;
        this.trimLevel = trimLevel;
        this.history = history;
        this.mileage = mileage;
        this.year = year;
        this.color = color;
    }

    public OfferingOverview(VehicleOffer vo) {
        this.id = vo.id;
        this.make = vo.make;
        this.model = vo.model;
        this.trany = vo.trany;
        this.fuel = vo.fuel;
        this.emissions = vo.emissions;
        this.engine = vo.engine;
        this.imageURL = vo.mainImage.url;
        this.trimLevel = vo.trimLevel;
        this.history = vo.history;
        this.vClass = vo.vClass;
        this.mileage = vo.mileage;
        this.year = vo.year;
        this.seats = vo.seats;
        this.color = vo.colorDescription;
    }
}

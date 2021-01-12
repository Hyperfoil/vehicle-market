package io.hyperfoil.market.vehicle.model;

public final class VehicleFeature {
   public static final VehicleFeature ABS = new VehicleFeature("ABS", "ABS", Category.SAFETY, "Anti-blocking system helps you not to crash.");
   public static final VehicleFeature RAIN_SENSOR = new VehicleFeature("RAIN_SENSOR", "Rain sensor", Category.OTHER, "Turns windscreen wipers on when you least expect it.");
   public static final VehicleFeature SPORT_SEATS = new VehicleFeature("SPORT_SEATS", "Sport seats", Category.INTERIOR, null);
   public static final VehicleFeature STOP_START = new VehicleFeature("STOP_START", "Stop Start System", Category.INTERIOR, "Destroys your battery 2x faster.");
   // TODO: all the gadgets!

   public enum Category {
      INTERIOR,
      INFOTAINMENT,
      EXTERIOR,
      SAFETY,
      OTHER
   }

   /**
    * Refer to {@link VehicleFeature}
    */
   public String id;

   /**
    * Human readable, localized...
    */
   public String name;

   public Category category;

   public String description;

   public VehicleFeature() {
   }

   private VehicleFeature(String id, String name, Category category, String description) {
      this.id = id;
      this.category = category;
      this.name = name;
      this.description = description;
   }
}

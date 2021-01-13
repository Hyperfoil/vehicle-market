package io.hyperfoil.market.vehicle.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name = "V_DESCRIPTION", indexes = {
        @Index(columnList = "YEAR", name = "V_DESCRIPTION_YEAR"),
        @Index(columnList = "MAKE", name = "V_DESCRIPTION_MAKE"),
        @Index(columnList = "MODEL", name = "V_DESCRIPTION_MODEL"),
        @Index(columnList = "TRANY", name = "V_DESCRIPTION_TRANY"),
        @Index(columnList = "DRIVE", name = "V_DESCRIPTION_DRIVE"),
        @Index(columnList = "VCLASS", name = "V_DESCRIPTION_VCLASS"),
        @Index(columnList = "FUEL", name = "V_DESCRIPTION_FUEL"),
})
@NamedQueries({
        @NamedQuery(name=VehicleDescription.QUERY_COUNT,
                query="SELECT COUNT(vd.id) FROM VehicleDescription vd"),
        @NamedQuery(name=VehicleDescription.QUERY_GET_VD_BY_IDS,
                query="SELECT vd FROM VehicleDescription vd where vd.id in :ids"),
//        @NamedQuery(name=VehicleDescription.QUERY_BY_CATEGORY,
//                query="SELECT vd FROM VehicleDescription vd where vd.category = :category",
//                hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"),
//                        @QueryHint(name="javax.persistence.cache.storeMode", value="USE")}),
        @NamedQuery(name=VehicleDescription.DISTINCT_YEARS,
                query="SELECT DISTINCT (vd.year) FROM VehicleDescription vd",
                hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"),
                        @QueryHint(name="javax.persistence.cache.storeMode", value="USE")}),
        @NamedQuery(name=VehicleDescription.DISTINCT_TRANSMISSIONS,
                query="SELECT DISTINCT (vd.trany) FROM VehicleDescription vd",
                hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"),
                        @QueryHint(name="javax.persistence.cache.storeMode", value="USE")}),
        @NamedQuery(name=VehicleDescription.DISTINCT_DRIVERTRAIN,
                query="SELECT DISTINCT (vd.drive) FROM VehicleDescription vd",
                hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"),
                        @QueryHint(name="javax.persistence.cache.storeMode", value="USE")}),
        @NamedQuery(name=VehicleDescription.DISTINCT_VCLASS,
                query="SELECT DISTINCT (vd.vClass) FROM VehicleDescription vd",
                hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"),
                        @QueryHint(name="javax.persistence.cache.storeMode", value="USE")}),
        @NamedQuery(name=VehicleDescription.DISTINCT_MAKES,
                query="SELECT DISTINCT (vd.make) FROM VehicleDescription vd",
                hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"),
                        @QueryHint(name="javax.persistence.cache.storeMode", value="USE")}),
        @NamedQuery(name=VehicleDescription.DISTINCT_MODELS,
                query="SELECT DISTINCT (vd.model) FROM VehicleDescription vd WHERE vd.make=:m",
                hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"),
                        @QueryHint(name="javax.persistence.cache.storeMode", value="USE")})
})
public class VehicleDescription {

    public static final String QUERY_COUNT = "VehicleDescription.getCount";
    public static final String QUERY_GET_VD_BY_IDS = "VehicleDescription.getVdByIds";
    public static final String QUERY_BY_CATEGORY = "VehicleDescription.distinctCategory";
    public static final String QUERY_BY_MAKE = "VehicleDescription.byMake";
    public static final String DISTINCT_YEARS = "VehicleDescription.distinctYears";
    public static final String DISTINCT_TRANSMISSIONS = "VehicleDescription.distinctTransmissions";
    public static final String DISTINCT_DRIVERTRAIN = "VehicleDescription.distinctDrivetrain";
    public static final String DISTINCT_VCLASS = "VehicleDescription.distinctVClass";
    public static final String DISTINCT_MAKES = "VehicleDescription.distinctMakes";
    public static final String DISTINCT_MODELS = "VehicleDescription.distinctModels";

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private int year;

    @Column(nullable = false)
    private String trany;

    @Column(nullable = false)
    private String drive;

    // displacement, type, power: 1.2 TCe, 92kW
    @Column(nullable = false)
    private String engine;

    @Column(nullable = false)
    private String vClass;

    @Column(nullable = false)
    private String fuel;

    @Column(nullable = false)
    private int seats;

    // EURO6...
    @Column(nullable = false)
    private String emissions;

    // --- //

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTrany() {
        return trany;
    }

    public void setTrany(String trany) {
        this.trany = trany;
    }

    public String getDrive() {
        return drive;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getVClass() {
        return vClass;
    }

    public void setVClass(String vClass) {
        this.vClass = vClass;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public String getEmissions() {
        return emissions;
    }

    public void setEmissions(String emissions) {
        this.emissions = emissions;
    }
}

package io.hyperfoil.market.vehicle.discovery;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.SequenceGenerator;
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
@NamedQuery(name=VehicleDescription.QUERY_COUNT, query="SELECT COUNT(vd.id) FROM VehicleDescription vd")
@NamedQuery(name=VehicleDescription.QUERY_GET_VD_BY_IDS, query="SELECT vd FROM VehicleDescription vd where vd.id in :ids")
//@NamedQuery(name=VehicleDescription.QUERY_BY_CATEGORY, query="SELECT vd FROM VehicleDescription vd where vd.category = :category",
//        hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"), @QueryHint(name="javax.persistence.cache.storeMode", value="USE")})
@NamedQuery(name=VehicleDescription.DISTINCT_YEARS, query="SELECT DISTINCT (vd.year) FROM VehicleDescription vd",
        hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"), @QueryHint(name="javax.persistence.cache.storeMode", value="USE")})
@NamedQuery(name=VehicleDescription.DISTINCT_TRANSMISSIONS, query="SELECT DISTINCT (vd.trany) FROM VehicleDescription vd",
        hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"), @QueryHint(name="javax.persistence.cache.storeMode", value="USE")})
@NamedQuery(name=VehicleDescription.DISTINCT_DRIVERTRAIN, query="SELECT DISTINCT (vd.drive) FROM VehicleDescription vd",
        hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"), @QueryHint(name="javax.persistence.cache.storeMode", value="USE")})
@NamedQuery(name=VehicleDescription.DISTINCT_VCLASS, query="SELECT DISTINCT (vd.vClass) FROM VehicleDescription vd",
        hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"), @QueryHint(name="javax.persistence.cache.storeMode", value="USE")})
@NamedQuery(name=VehicleDescription.DISTINCT_MAKES,  query="SELECT DISTINCT (vd.make) FROM VehicleDescription vd",
        hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"), @QueryHint(name="javax.persistence.cache.storeMode", value="USE")})
@NamedQuery(name=VehicleDescription.DISTINCT_MODELS, query="SELECT DISTINCT (vd.model) FROM VehicleDescription vd WHERE vd.make=:m",
        hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"), @QueryHint(name="javax.persistence.cache.storeMode", value="USE")})
public class VehicleDescription {

    public static final String QUERY_COUNT = "VehicleDescription.getCount";
    public static final String QUERY_GET_VD_BY_IDS = "VehicleDescription.getVdByIds";
    public static final String QUERY_BY_CATEGORY = "VehicleDescription.distinctCategory";
    public static final String DISTINCT_YEARS = "VehicleDescription.distinctYears";
    public static final String DISTINCT_TRANSMISSIONS = "VehicleDescription.distinctTransmissions";
    public static final String DISTINCT_DRIVERTRAIN = "VehicleDescription.distinctDrivetrain";
    public static final String DISTINCT_VCLASS = "VehicleDescription.distinctVClass";
    public static final String DISTINCT_MAKES = "VehicleDescription.distinctMakes";
    public static final String DISTINCT_MODELS = "VehicleDescription.distinctModels";

    public VehicleDescription() {
        // no-args constructor required by JPA
    }

    public VehicleDescription(String make, String model, int year, String trany, String drive, String engine, String vClass, String fuel, int seats, String emissions) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.trany = trany;
        this.drive = drive;
        this.engine = engine;
        this.vClass = vClass;
        this.fuel = fuel;
        this.seats = seats;
        this.emissions = emissions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", allocationSize = 1000, sequenceName = "V_DESCRIPTION_SEQUENCE")
    public Long id;

    @Column(nullable = false)
    public String make;

    @Column(nullable = false)
    public String model;

    @Column(nullable = false)
    public int year;

    @Column(nullable = false)
    public String trany;

    @Column(nullable = false)
    public String drive;

    // displacement, type, power: 1.2 TCe, 92kW
    @Column(nullable = false)
    public String engine;

    @Column(nullable = false)
    public String vClass;

    @Column(nullable = false)
    public String fuel;

    @Column(nullable = false)
    public int seats;

    // EURO6...
    @Column(nullable = false)
    public String emissions;

}

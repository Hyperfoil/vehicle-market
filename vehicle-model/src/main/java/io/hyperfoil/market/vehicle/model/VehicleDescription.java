package io.hyperfoil.market.vehicle.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
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
public class VehicleDescription {

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

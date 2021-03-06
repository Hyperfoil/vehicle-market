package io.hyperfoil.market.listing.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ConstraintMode;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Collection;

@Entity
@Cacheable
@Table(name = "V_OFFER")
@NamedQuery(name = VehicleOffer.QUERY_ALL, query = "SELECT vo FROM VehicleOffer vo")
@NamedQuery(name = VehicleOffer.QUERY_COUNT, query = "SELECT COUNT(vo.id) FROM VehicleOffer vo")
@NamedQuery(name = VehicleOffer.QUERY_OVERVIEW, query = "SELECT new io.hyperfoil.market.listing.client.OfferingOverview(vo.id, vo.make, vo.model, vo.trany, vo.vClass, vo.fuel, vo.seats, vo.emissions, vo.engine, image.url, vo.trimLevel, vo.history, vo.mileage, vo.year, vo.colorDescription) FROM VehicleOffer vo LEFT JOIN vo.mainImage image")
@NamedQuery(name = VehicleOffer.DELETE_ALL, query = "DELETE FROM VehicleOffer")
@NamedEntityGraph(name = VehicleOffer.WITH_GALLERY, attributeNodes = @NamedAttributeNode("gallery"))
public class VehicleOffer {

    public static final String QUERY_ALL = "VehicleOffer.all";
    public static final String QUERY_COUNT = "VehicleOffer.count";
    public static final String QUERY_OVERVIEW = "VehicleOffer.overview";
    public static final String WITH_GALLERY = "VehicleOffer.withGallery";
    public static final String DELETE_ALL = "VehicleOffer.deleteAll";

    @Id
    @GeneratedValue
    public Long id;

    @Column(nullable = false)
    public String make;

    @Column(nullable = false)
    public String model;

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

    // --- //

    // Base, Economy, Caravelle, Chick Magnet...
    @Column
    public String trimLevel;

    @Column(nullable = false)
    public long mileage;

    @Column(nullable = false)
    public int year;

    @Column(nullable = false)
    public String colorDescription;

    // this is for the dummy car image color
    @Column(nullable = false)
    public String rgbColor;

    @Column
    public int prevOwners;

    @Column
    public LocalDate inspectionValidUntil;

    // bought in country, service book...
    @Column
    public String history;

    // those from VehicleFeatures class
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    public Collection<VehicleFeature> features;

    // image displayed in overview
    @OneToOne
    public VehicleGalleryItem mainImage;

    // identifiers for image paths
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "V_OFFER", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    @Fetch(FetchMode.SUBSELECT)
    public Collection<VehicleGalleryItem> gallery;
}

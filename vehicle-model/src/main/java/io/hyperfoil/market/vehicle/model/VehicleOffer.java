package io.hyperfoil.market.vehicle.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

@Entity
@Cacheable
@Table(name = "V_OFFER")
@NamedQuery(name = VehicleOffer.QUERY_ALL, query = "SELECT vo FROM VehicleOffer vo")
@NamedQuery(name = VehicleOffer.QUERY_COUNT, query = "SELECT COUNT(vo.id) FROM VehicleOffer vo")
@NamedQuery(name = VehicleOffer.QUERY_FOR_DTO, query = "SELECT vo FROM VehicleOffer vo JOIN FETCH vo.model")
@NamedEntityGraph(name = VehicleOffer.WITH_GALLERY, attributeNodes = @NamedAttributeNode("gallery"))
public class VehicleOffer {

    public static final String QUERY_ALL = "VehicleOffer.all";
    public static final String QUERY_COUNT = "VehicleOffer.count";
    public static final String QUERY_FOR_DTO = "VehicleOffer.forDTO";
    public static final String WITH_GALLERY = "VehicleOffer.withGallery";

    @Id
    @GeneratedValue
    public Long id;

    @ManyToOne(optional = false)
    public VehicleDescription model;

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
    public Date inspectionValidUntil;

    // bought in country, service book...
    @Column
    public String history;

    // those from VehicleFeatures class
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    public Collection<VehicleFeature> features;

    // identifiers for image paths
    @OneToMany
    @JoinColumn(name = "V_OFFER")
    @Fetch(FetchMode.SUBSELECT)
    public Collection<VehicleGalleryItem> gallery;
}

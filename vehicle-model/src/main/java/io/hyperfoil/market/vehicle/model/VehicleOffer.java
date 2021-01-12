package io.hyperfoil.market.vehicle.model;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name = "V_OFFER")
public class VehicleOffer {
   @Id
   @GeneratedValue
   public Long id;

   @ManyToOne(optional = false)
   @JoinColumn(name="DESCRIPTION_ID")
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
   @ElementCollection
   public Collection<String> features;

   // identifiers for image paths
   @ElementCollection
   public Collection<String> gallery;
}

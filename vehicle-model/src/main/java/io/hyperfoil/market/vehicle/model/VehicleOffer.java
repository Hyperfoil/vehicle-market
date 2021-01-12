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
   private Long id;

   @ManyToOne(optional = false)
   @JoinColumn(name="DESCRIPTION_ID")
   VehicleDescription model;

   // Base, Economy, Caravelle, Chick Magnet...
   @Column
   String trimLevel;

   @Column(nullable = false)
   private long mileage;

   @Column(nullable = false)
   private int manufactureYear;

   @Column(nullable = false)
   private String colorDescription;

   // this is for the dummy car image color
   @Column(nullable = false)
   private String rgbColor;

   @Column
   private int prevOwners;

   @Column
   private Date inspectionValidUntil;

   // bought in country, service book...
   @Column
   private String history;

   // those from VehicleFeatures class
   @ElementCollection
   Collection<String> features;

   // identifiers for image paths
   @ElementCollection
   Collection<String> gallery;
}

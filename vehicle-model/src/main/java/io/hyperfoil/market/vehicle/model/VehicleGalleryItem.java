package io.hyperfoil.market.vehicle.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name = "V_GALLERY")
public class VehicleGalleryItem {

    @Id
    @GeneratedValue
    public Long id;

    public String url;

    public String title;

}

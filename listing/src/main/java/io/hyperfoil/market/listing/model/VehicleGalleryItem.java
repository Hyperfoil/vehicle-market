package io.hyperfoil.market.listing.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name = "V_GALLERY")
@NamedQuery(name = VehicleGalleryItem.DELETE_ALL, query = "DELETE FROM VehicleGalleryItem")
public class VehicleGalleryItem {
    public static final String DELETE_ALL = "VehicleGalleryItem.deleteAll";

    @Id
    @GeneratedValue
    public Long id;

    public String url;

    public String title;

}

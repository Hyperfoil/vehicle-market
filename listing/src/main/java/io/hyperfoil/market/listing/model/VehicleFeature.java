package io.hyperfoil.market.listing.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.QueryHint;
import javax.persistence.Table;

@Entity
@Cacheable
@Table(name = "V_FEATURE")
@NamedQuery(name = VehicleFeature.QUERY_ALL, query = "SELECT vf FROM VehicleFeature vf",
        hints={ @QueryHint(name="javax.persistence.cache.retrieveMode", value="USE"), @QueryHint(name="javax.persistence.cache.storeMode", value="USE")})
public class VehicleFeature {

    public static final String QUERY_ALL = "VehicleFeature.all";

    @Id
    @GeneratedValue
    public Long id;

    // Human readable, localized...
    @Column(nullable = false)
    public String name;

    @Column(nullable = false)
    @Enumerated
    public VehicleFeatureCategory category;

    public String description;
}

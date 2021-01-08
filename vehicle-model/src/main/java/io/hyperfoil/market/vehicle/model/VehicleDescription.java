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
        @Index(columnList = "DRIVE", name = "V_DESCRIPTION_DRIVE") ,
        @Index(columnList = "VCLASS", name = "V_DESCRIPTION_VCLASS")
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

    @Column(nullable = false)
    private String vClass;

}

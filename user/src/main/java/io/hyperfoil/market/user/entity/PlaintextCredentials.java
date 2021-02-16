package io.hyperfoil.market.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 * This is not a production-grade entity: it contains a plaintext password for debug/benchmark purposes.
 * Regular registration should not create this item (to prevent people from inadvertently disclosing
 * their passwords), only the randomized loader should use that.
 */
@Entity
@NamedQuery(name = PlaintextCredentials.FIND_ALL, query = "SELECT pc FROM PlaintextCredentials pc")
@NamedQuery(name = PlaintextCredentials.DELETE_ALL, query = "DELETE FROM PlaintextCredentials")
public class PlaintextCredentials {
   public static final String FIND_ALL = "PlaintextCredentials.findAll";
   public static final String DELETE_ALL = "PlaintextCredentials.deleteAll";

   @Id
   public Long id;

   @OneToOne(fetch = FetchType.EAGER)
   @MapsId
   public User user;

   @Column
   public String password;
}

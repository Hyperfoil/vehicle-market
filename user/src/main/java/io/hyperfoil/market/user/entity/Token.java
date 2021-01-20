package io.hyperfoil.market.user.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
public class Token {
   public static final String FIND_BY_TOKEN = "FIND_BY_TOKEN";

   @Id
   @Column(nullable = false)
   public String token;

   @ManyToOne
   public User user;

   @Column(nullable = false)
   public Timestamp expires;
}

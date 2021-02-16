package io.hyperfoil.market.user.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = Token.DELETE_ALL, query = "DELETE FROM Token")
public class Token {
   public static final String DELETE_ALL = "Token.deleteAll";
   @Id
   @Column(nullable = false)
   public String token;

   @ManyToOne
   public User user;

   @Column(nullable = false)
   public Timestamp expires;
}

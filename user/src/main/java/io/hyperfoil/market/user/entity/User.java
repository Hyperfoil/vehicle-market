package io.hyperfoil.market.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "users", indexes = {
      @Index(name="user_username", columnList = "username")
   }, uniqueConstraints = {
      @UniqueConstraint(name = "unique_username", columnNames = "username")
   })
@NamedQuery(name = User.FIND_BY_USERNAME, query = "SELECT u FROM User u WHERE u.username = :u")
@NamedQuery(name = User.DELETE_ALL, query = "DELETE FROM User")
public class User {
   public static final String FIND_BY_USERNAME = "User.findByUsername";
   public static final String DELETE_ALL = "User.deleteAll";

   @Id
   @GeneratedValue
   public Long id;

   @Column(nullable = false)
   public String username;

   @Column(nullable = false)
   public byte[] salt;

   @Column(nullable = false)
   public byte[] passhash;

   @Column(nullable = false)
   public String firstName;

   @Column(nullable = false)
   public String lastName;

   @Column
   public String email;

   @Column
   public String phone;
}

package io.hyperfoil.market.user;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import io.hyperfoil.market.user.client.Registration;
import io.hyperfoil.market.user.client.UserInfo;
import io.hyperfoil.market.user.entity.Token;
import io.hyperfoil.market.user.entity.User;

@Path("/")
@RequestScoped
public class UserService {
   static final int SALT_BYTES = 16;
   static final int TOKEN_BYTES = 24;

   @Inject
   @PersistenceContext(unitName = "user")
   EntityManager entityManager;

   @Inject
   Config config;

   @POST
   @Path("login")
   @Consumes("application/x-www-form-urlencoded")
   @Transactional
   public Response login(@FormParam("username") String username, @FormParam("password") String password)
         throws InvalidKeySpecException, NoSuchAlgorithmException {
      if (username == null || password == null) {
         throw new WebApplicationException(Response.Status.BAD_REQUEST);
      }
      try {
         User user = entityManager.createNamedQuery(User.FIND_BY_USERNAME, User.class).getSingleResult();
         byte[] passhash = computeHash(password, user.salt, config.hashIterations);
         if (Arrays.equals(passhash, user.passhash)) {
            Token token = new Token();
            byte[] tokenBytes = new byte[TOKEN_BYTES];
            new SecureRandom().nextBytes(tokenBytes);
            token.token = Base64.getEncoder().encodeToString(tokenBytes);
            token.user = user;
            token.expires = new Timestamp(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(1));
            entityManager.persist(token);
            return Response.ok(token.token).build();
         }
      } catch (NoResultException e) {
         // user was not found but we won't tell
      }
      return Response.status(Response.Status.UNAUTHORIZED).build();
   }

   @POST
   @Path("logout")
   @Transactional
   public Response logout(String token) {
      Token t = entityManager.find(Token.class, token);
      if (t == null) {
         return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      entityManager.remove(t);
      return Response.ok().build();
   }

   @GET
   @Path("info")
   @Produces("application/json")
   public Response info(@QueryParam("token") String token) {
      Token t = entityManager.find(Token.class, token);
      if (t == null) {
         return Response.status(Response.Status.UNAUTHORIZED).build();
      }
      return Response.ok(new UserInfo(t.user.username, t.user.firstName, t.user.lastName, t.user.email, t.user.phone)).build();
   }

   @POST
   @Path("create")
   @Consumes("application/json")
   @Transactional
   public void createUser(Registration registration)
         throws NoSuchAlgorithmException, InvalidKeySpecException {
      User user = new User();
      user.username = registration.user.username;
      user.firstName = registration.user.firstName;
      user.lastName = registration.user.lastName;
      user.email = registration.user.email;
      user.phone = registration.user.phone;
      user.salt = new byte[SALT_BYTES];
      new SecureRandom().nextBytes(user.salt);
      user.passhash = computeHash(registration.password, user.salt, config.hashIterations);

      entityManager.persist(user);
   }

   static byte[] computeHash(String password, byte[] salt, int iterationCount) throws NoSuchAlgorithmException, InvalidKeySpecException {
      PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, 128);
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      return factory.generateSecret(spec).getEncoded();
   }
}

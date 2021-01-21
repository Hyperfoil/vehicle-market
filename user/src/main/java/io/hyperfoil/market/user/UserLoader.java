package io.hyperfoil.market.user;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import io.hyperfoil.market.user.entity.Token;
import io.hyperfoil.market.user.entity.User;
import io.hyperfoil.nagen.loader.NameGenerator;

@Path("/user-loader")
public class UserLoader {
   private static final NameGenerator NAME_GENERATOR = NameGenerator.getInstance();

   @Inject
   @PersistenceContext(unitName = "user")
   EntityManager entityManager;

   @Inject
   Config config;

   @POST
   @Transactional // TODO: maybe we should split each batch to its transaction
   public void load(@QueryParam("users") int users, @QueryParam("tokens") int tokens, @QueryParam("batch") @DefaultValue("100") int batch) throws InvalidKeySpecException, NoSuchAlgorithmException {
      if (batch <= 0) {
         batch = 100;
      }
      for (int i = 0; i < users; ++i) {
         User user = randomUser();
         entityManager.persist(user);
         int userTokens = (i + 1) * tokens / users - i * tokens / users;
         for (int j = 0; j < userTokens; ++j) {
            entityManager.persist(randomToken(user));
         }
         if (i % batch == batch - 1){
            entityManager.flush();
         }
      }
   }

   private User randomUser() throws InvalidKeySpecException, NoSuchAlgorithmException {
      User user = new User();
      user.firstName = NAME_GENERATOR.getRandomFirstName();
      user.lastName = NAME_GENERATOR.getRandomLastName();
      ThreadLocalRandom random = ThreadLocalRandom.current();
      int suffix = random.nextInt(1000);
      user.email = NAME_GENERATOR.getRandomEmail(user.firstName, user.lastName, suffix);
      user.username = user.firstName + "." + user.lastName + suffix;
      user.phone = NAME_GENERATOR.getRandomPhone();
      user.salt = new byte[UserService.SALT_BYTES];
      // We are intentionally NOT using cryptographically safe source of random
      random.nextBytes(user.salt);
      user.passhash = UserService.computeHash(NAME_GENERATOR.getRandomPassword(), user.salt, config.hashIterations);
      return user;
   }

   private Token randomToken(User user) {
      Token token = new Token();
      token.user = user;
      byte[] bytes = new byte[UserService.TOKEN_BYTES];
      // We are intentionally NOT using cryptographically safe source of random
      ThreadLocalRandom random = ThreadLocalRandom.current();
      random.nextBytes(bytes);
      token.token = Base64.getEncoder().encodeToString(bytes);
      token.expires = new Timestamp(System.currentTimeMillis() + random.nextLong(TimeUnit.DAYS.toMillis(1)));
      return token;
   }
}

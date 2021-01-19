package io.hyperfoil.market.user;

import javax.activation.MimeType;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import io.hyperfoil.market.user.client.UserInfo;

@Path("/")
public class UserService {

   @POST
   @Path("login")
   @Consumes("application/x-www-form-urlencoded")
   public String login(@FormParam("username") String username, @FormParam("password") String password) {
      if (username == null || password == null) {
         throw new WebApplicationException(Response.Status.BAD_REQUEST);
      }
      if (username.equals("user") && password.equals("pwd")) {
         return "this-is-a-token-for-validated-user";
      }
      throw new WebApplicationException(Response.Status.UNAUTHORIZED);
   }

   @POST
   @Path("logout")
   public void logout() {

   }

   @GET
   @Path("info")
   @Produces("application/json")
   public UserInfo info(@QueryParam("token") String token) {
      return new UserInfo("user", "Freddy", "Krueger", "freddy@hyperfoil.io", "+420777123456");
   }
}

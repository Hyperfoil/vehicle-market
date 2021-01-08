package io.hyperfoil.market;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.jboss.resteasy.annotations.cache.Cache;

@ApplicationScoped
@Path("images")
public class ImageService {
   @GET
   @Path("car")
   @Cache
   public String car(@QueryParam("type") String type, @QueryParam("color") String color) {

      return null;
   }
}

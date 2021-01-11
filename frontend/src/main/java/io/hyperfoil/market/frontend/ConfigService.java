package io.hyperfoil.market.frontend;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.vertx.core.json.JsonObject;

@Path("config")
public class ConfigService {
   @ConfigProperty(name = "discovery.service.url", defaultValue = "http://localhost:8081")
   String discoveryServiceUrl;

   @ConfigProperty(name = "listing.service.url", defaultValue = "http://localhost:8082")
   String listingServiceUrl;

   @GET
   @Produces("text/javascript")
   public String getConfig() {
      JsonObject config = new JsonObject();
      config.put("discoveryUrl", discoveryServiceUrl);
      config.put("listingUrl", listingServiceUrl);
      return "window.vehicleMarketConfig = " + config.encodePrettily();
   }
}

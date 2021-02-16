package io.hyperfoil.market.discovery;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

@Path("/vehicle")
@RegisterRestClient(configKey="discovery-service")
public interface DiscoveryServiceInterface {

    @GET
    @Path("/random/{maxResults}")
    List<VehicleDescription> random(@PathParam("maxResults") int maxResults);

}

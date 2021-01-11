package io.hyperfoil.market.frontend;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;

import org.jboss.logging.Logger;
import org.jboss.resteasy.annotations.cache.Cache;

@ApplicationScoped
@Path("images")
public class ImageService {
   private static final Logger log = Logger.getLogger(ImageService.class);
   private static final String REPLACE_TOKEN = "__replace_color_here__";
   private final Map<String, ParsedImage> images = new ConcurrentHashMap<>();

   @GET
   @Path("car")
   @Cache
   public String car(@QueryParam("type") String type, @QueryParam("color") String color) {
      ParsedImage image = images.computeIfAbsent(type, t -> {
         InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(type + ".svg");
         if (stream == null) {
            log.errorf("No resource %s.svg", type);
            return null;
         } else {
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            try {
               while ((length = stream.read(buffer)) != -1) {
                  result.write(buffer, 0, length);
               }
               String template = result.toString(StandardCharsets.UTF_8.name());
               int index = template.indexOf(REPLACE_TOKEN);
               if (index < 0) {
                  log.errorf("Failed to find replace token in %s:\n%s", type, template);
                  return null;
               }
               return new ParsedImage(template.substring(0, index), template.substring(index + REPLACE_TOKEN.length()));
            } catch (IOException e) {
               log.errorf(e, "Failed to load image type %s", type);
               return null;
            }
         }
      });
      if (image == null) {
         throw new WebApplicationException("Cannot load image for type " + type);
      } else {
         return image.withColor(color);
      }
   }

   private static class ParsedImage {
      final String prefix;
      final String suffix;

      private ParsedImage(String prefix, String suffix) {
         this.prefix = prefix;
         this.suffix = suffix;
      }

      public String withColor(String color) {
         return prefix + color + suffix;
      }
   }
}

package io.hyperfoil.market.listing;

import io.hyperfoil.market.listing.model.VehicleFeature;
import io.hyperfoil.market.listing.model.VehicleGalleryItem;
import io.hyperfoil.market.listing.model.VehicleOffer;
import io.hyperfoil.market.discovery.DiscoveryServiceInterface;
import io.hyperfoil.market.discovery.VehicleDescription;
import io.quarkus.narayana.jta.runtime.TransactionConfiguration;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/loader")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ListingLoaderService {
    private static final Logger log = Logger.getLogger(ListingLoaderService.class);

    private static final int VEHICLE_BATCH_SIZE = 1000;

    private static final LocalDate TODAY = LocalDate.now();

    private static final List<String> TRIMS = List.of("Everything", "Everything and some more", "Prestige", "Luxurious", "Basic", "Minimalistic", "Nothing special", "Special edition", "Style", "Budget", "Value", "Sport", "Racing", "Sport line");

    private static final List<String> HISTORIES = List.of("Well kept", "Belonged to a priest", "Belonged to a nun", "Belonged to a teacher", "Belonged to a nurse", "Belonged to a fire-fighter", "Mostly highway", "Non-smoker", "As good as new", "Never left the garage", "Has seen better days", "It's not what it used to be", "Just some scratches here and there", "All the belts have been changed recently", "With brand new tires!", "Still smells fresh", "Never crashed", "It even has the sock stickers", "Very reliable", "Leaks a little bit of oil", "Opportunity!!!", "Many upgrades", "Special Edition", "Very rare. Only 55 in the country", "A classic", "They don't make them like they used to", "Lots of torque", "Always starts at first try", "Hurry up, there is lots of interest in this car", "Call for further details");

    private static final List<String> COLORS = List.of("White", "Red", "Pink", "Purple", "Silver", "Grey", "Brown", "Orange", "Gold", "Black", "Blue", "Green", "Salmon", "Magenta", "Cyan", "Dirty");

    private static final List<String> TITLES = List.of("Front view", "Side view", "Rear view", "Top view", "Interior view", "Engine view");

    private static final List<String> IMAGES = List.of("cabriolet", "hatchback", "micro", "pickup", "roadster", "sedan", "supercar", "van", "vintage");

    @PersistenceContext(unitName = "listing")
    EntityManager em;

    @Inject
    @RestClient
    DiscoveryServiceInterface discoveryService;

    @POST
    @Transactional
    @Path("/features")
    public String loadFeatures() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream("features.json")) {
            VehicleFeature[] features = mapper.readValue(stream, new TypeReference<>() {});
            for (VehicleFeature feature : features) {
                em.merge(feature);
            }
            return "Inserted " + features.length + " features.\n";
        }
    }

    @POST
    @Transactional
    @TransactionConfiguration(timeout = 600) // TODO: quarkus specific
    @Path("/offering/{quantity}")
    public Response loadOfferings(@PathParam("quantity") int quantity) {
        log.info("Proceeding with loading " + quantity + " offers");

        Random random = new Random(42); // TODO: remove fixed seed

        List<VehicleFeature> allFeatures = em.createNamedQuery(VehicleFeature.QUERY_ALL, VehicleFeature.class).getResultList();
        if (allFeatures.isEmpty()) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                  .entity("No vehicle features are defined.").build();
        }
        List<VehicleDescription> vehicles = Collections.emptyList();
        List<String> images = new ArrayList<>(IMAGES);

        while (quantity-- > 0) {
            if (vehicles.isEmpty()) {
                vehicles = discoveryService.random(Integer.min(VEHICLE_BATCH_SIZE, quantity + 1));
                if (vehicles.isEmpty()) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                          .entity("Discovery service does not contain any vehicles.").build();
                }
            }
            VehicleDescription vehicle = vehicles.remove(vehicles.size() - 1);

            VehicleOffer vo = new VehicleOffer();

            vo.make = vehicle.make;
            vo.model = vehicle.model;
            vo.year = vehicle.year + random.nextInt(3);
            vo.drive = vehicle.drive;
            vo.engine = vehicle.engine;
            vo.emissions = vehicle.emissions;
            vo.fuel = vehicle.fuel;
            vo.seats = vehicle.seats;
            vo.trany = vehicle.trany;
            vo.vClass = vehicle.vClass;

            Collections.shuffle(allFeatures, random);
            vo.features = allFeatures.subList(0, random.nextInt(allFeatures.size()));

            vo.colorDescription = COLORS.get(random.nextInt(COLORS.size()));
            vo.rgbColor = String.format("%06x", random.nextInt(0xFFFFFF));
            vo.inspectionValidUntil = TODAY.plusDays(random.nextInt(365));
            vo.mileage = random.nextInt(200_000);
            vo.prevOwners = random.nextInt(5);
            vo.trimLevel = TRIMS.get(random.nextInt(TRIMS.size()));
            vo.history = HISTORIES.get(random.nextInt(HISTORIES.size()));

            Collections.shuffle(images, random);
            List<VehicleGalleryItem> gallery = galleryItemGenerator(1 + random.nextInt(3), images, vo.rgbColor);
            vo.mainImage = gallery.get(0);
            vo.gallery = gallery;

            em.persist(vo);

            if (quantity % 1000 == 0) {
                log.info("Remaining " + quantity + " offers");
            }
        }
        return Response.ok("Inserted " + quantity + " offers.\n").build();
    }

    private List<VehicleGalleryItem> galleryItemGenerator(int quantity, List<String> images, String color) {
        List<VehicleGalleryItem> list = new ArrayList<>(quantity);
        while (quantity-- > 0) {
            VehicleGalleryItem item = new VehicleGalleryItem();
            item.title = TITLES.get(quantity); 
            item.url = "/images/car?type=" + images.get(quantity) + "&color=%23" + color;

            list.add(item);
        }
        return list;
    }

    @DELETE
    @Path("/offering")
    @Transactional
    public String deleteAll() {
        int deleted = em.createNamedQuery(VehicleOffer.DELETE_ALL).executeUpdate();
        // TODO: do proper cascading delete!
        em.createNamedQuery(VehicleGalleryItem.DELETE_ALL).executeUpdate();
        return "Deleted " + deleted + " offers.\n";
    }
}

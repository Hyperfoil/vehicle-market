package io.hyperfoil.market.vehicle.discovery;

import io.hyperfoil.market.vehicle.model.VehicleDescription;
import io.hyperfoil.market.vehicle.repository.VehicleDescriptionRepository;
import io.quarkus.narayana.jta.runtime.TransactionConfiguration;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

@Path("/vehicle")
@RequestScoped
public class FuelEconomyLoader {
    private static final Logger logger = Logger.getLogger(VehicleDiscoveryService.class.getName());

    @Inject
    VehicleDescriptionRepository repository;

    /**
     * Loads vehicle descriptions from local CSV file
     */
    @GET
    @Transactional
    @Path("/load-local")
    public Response loadLocal() throws IOException {
        logger.info("Loading database from local CSV file");

        loadStream(getClass().getClassLoader().getResourceAsStream("vehicles.csv"));
        return Response.ok().build();
    }

    /**
     * Loads vehicle descriptions from remote CSV file (fetched from the US department of energy)
     */
    @GET
    @Transactional
    @TransactionConfiguration(timeout = 600) // TODO: quarkus specific
    @Path("/load-remote")
    public Response loadRemote() throws IOException {
        String url = "https://www.fueleconomy.gov/feg/epadata/vehicles.csv"; // TODO: store in config
        logger.info("Loading database from remote CSV file in URL: " + url);

        loadStream(new URL(url).openStream());
        return Response.ok().build();
    }

    private void loadStream(InputStream stream) throws IOException {
        int i = 0;
        for (CSVRecord record : CSVFormat.DEFAULT.withHeader().parse(new InputStreamReader(stream))) {
            loadRecord(record);

            if (++i % 100 == 0) {
                logger.info("Processed " + i);
            }
        }
        logger.info("Processed a total of " + i + " vehicle descriptions");
    }

    private void loadRecord(CSVRecord record) {
        VehicleDescription description = new VehicleDescription();

        description.setDrive(record.get("drive"));
        description.setEmissions(record.get("co2TailpipeGpm") + " grams/mile");
        description.setEngine(record.get("engId") + ": " + record.get("displ") + "l " + record.get("cylinders") + " cylinders");
        description.setFuel(record.get("fuelType"));
        description.setMake(record.get("make"));
        description.setModel(record.get("model"));
        description.setTrany(record.get("trany"));
        description.setVClass(record.get("VClass"));
        description.setYear(Integer.parseInt(record.get("year")));

        repository.save(description);
    }

}

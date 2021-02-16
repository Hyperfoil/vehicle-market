package io.hyperfoil.market.vehicle.discovery;

import io.quarkus.narayana.jta.runtime.TransactionConfiguration;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.logging.Logger;

@Path("/vehicle-loader")
@RequestScoped
public class FuelEconomyLoader {
    private static final Logger logger = Logger.getLogger(VehicleDiscoveryService.class.getName());

    @PersistenceUnit(unitName = "discovery")
    EntityManager em;

    /**
     * Loads vehicle descriptions from local CSV file
     */
    @POST
    @Transactional
    @Path("/local")
    public Response loadLocal() throws IOException {
        logger.info("Loading database from local CSV file");

        int inserted = loadStream(getClass().getClassLoader().getResourceAsStream("vehicles.csv"));
        return Response.ok("Inserted " + inserted + " models.").build();
    }

    /**
     * Loads vehicle descriptions from remote CSV file (fetched from the US department of energy)
     */
    @POST
    @Transactional
    @TransactionConfiguration(timeout = 600) // TODO: quarkus specific
    @Path("/remote")
    public Response loadRemote() throws IOException {
        String url = "https://www.fueleconomy.gov/feg/epadata/vehicles.csv"; // TODO: store in config
        logger.info("Loading database from remote CSV file in URL: " + url);

        int inserted = loadStream(new URL(url).openStream());
        return Response.ok("Inserted " + inserted + " models.\n").build();
    }

    private int loadStream(InputStream stream) throws IOException {
        int i = 0;
        for (CSVRecord record : CSVFormat.DEFAULT.withHeader().parse(new InputStreamReader(stream))) {
            em.persist(new VehicleDescription(
                    record.get("make"),
                    record.get("model"),
                    Integer.parseInt(record.get("year")),
                    record.get("trany"),
                    record.get("drive"),
                    /* record.get("engId") + ": " + */ record.get("displ") + "l " + record.get("cylinders") + " cylinders",
                    record.get("VClass"),
                    record.get("fuelType1"),
                    0,
                    parseCO2Record(record.get("co2TailpipeGpm"))
            ));

            if (++i % 1000 == 0) {
                logger.info("Processed " + i);
            }
        }
        logger.info("Processed a total of " + i + " vehicle descriptions");
        return i;
    }

    private String parseCO2Record(String co2TailpipeGpm) {
        int decimal = co2TailpipeGpm.indexOf('.');

        return co2TailpipeGpm.substring(0, decimal == -1 ? co2TailpipeGpm.length() : decimal + 2) + " CO2";
    }

    @DELETE
    @Transactional
    public String deleteAll() {
        int delete = em.createNamedQuery(VehicleDescription.DELETE_ALL).executeUpdate();
        return "Deleted " + delete + " models.\n";
    }

}

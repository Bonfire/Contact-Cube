package smallproject;

import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import smallproject.handler.AdminHandler;
import smallproject.handler.LoginHandler;
import smallproject.handler.RegistrationHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Matthew
 */
public class ApiServer {

    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "somefuckingpassword?";
    private static final String DATABASE_NAME = "small_project";
    private final ServletHandler handler = new ServletHandler();
    private static Logger log = null;

    static {
        InputStream stream = ApiServer.class.getClassLoader().
                getResourceAsStream("logging.properties");
        try {
            LogManager.getLogManager().readConfiguration(stream);
            log = Logger.getLogger(ApiServer.class.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ApiServer() {

        log.info("Starting API Server...");

        // set the data source and create the connection
        final HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/" + DATABASE_NAME);
        ds.setUsername(DATABASE_USERNAME);
        ds.setPassword(DATABASE_PASSWORD);

        // create the access point for JDBI and set the data source
        final Jdbi dbi = Jdbi.create(ds);
        log.info("Successfully connected to database");
        dbi.installPlugin(new SqlObjectPlugin());

        // register the handlers to their respective URLs
        handler.addServletWithMapping(new ServletHolder(new RegistrationHandler(dbi)), "/register");
        log.info("Created mapping for Registration Handler");

        handler.addServletWithMapping(new ServletHolder(new LoginHandler(dbi)), "/login");
        log.info("Created mapping for Login Handler");

        // just for misc. admin commands
        handler.addServletWithMapping(new ServletHolder(new AdminHandler(dbi)), "/admin");
        log.info("Created mapping for Admin Handler");


    }

    public static void main(String[] args) throws Exception {

        // create an instance of the Jetty server and tell it to listen on port 8080
        final Server server = new Server(8080);

        // create an instance of this ApiServer and set the server's handler
        final ApiServer api = new ApiServer();
        server.setHandler(api.handler);

        // start the server
        server.start();
        log.info("Server is now listening on port: 8080");
        server.join();

    }


}

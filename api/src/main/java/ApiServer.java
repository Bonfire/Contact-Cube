import com.zaxxer.hikari.HikariDataSource;
import handler.RegistrationHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jdbi.v3.core.Jdbi;

/**
 * @author Matthew
 */
public class ApiServer {

    private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "somefuckingpassword?";
    private static final String DATABASE_NAME = "small_project";

    public static void main(String[] args) throws Exception {

        // create an instance of the Jetty server and tell it to listen on port 8080
        final Server server = new Server(8080);

        // create an instance of this ApiServer and set the server's handler
        final ApiServer api = new ApiServer();
        server.setHandler(api.handler);

        // start the server
        server.start();
        server.join();

    }

    private final ServletHandler handler = new ServletHandler();

    ApiServer() {

        // create a gson singleton to be used throughout the handlers

        // set the data source and create the connection
        final HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/" + DATABASE_NAME);
        ds.setUsername(DATABASE_USERNAME);
        ds.setPassword(DATABASE_PASSWORD);

        // create the access point for JDBI and set the data source
        final Jdbi dbi = Jdbi.create(ds);

        // register the handlers to their respective URLs
        handler.addServletWithMapping(new ServletHolder(new RegistrationHandler(dbi)), "/register");

    }

}

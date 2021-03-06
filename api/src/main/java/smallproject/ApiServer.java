package smallproject;

import com.zaxxer.hikari.HikariDataSource;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import smallproject.dao.ContactDao;
import smallproject.dao.SessionDao;
import smallproject.dao.UserDao;
import smallproject.handler.AdminHandler;
import smallproject.handler.DashboardHandler;
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

        // create tables
        dbi.useExtension(UserDao.class, UserDao::createTable);
        dbi.useExtension(SessionDao.class, SessionDao::createTable);
        dbi.useExtension(ContactDao.class, ContactDao::createTable);

        // register the handlers to their respective URLs
        handler.addServletWithMapping(new ServletHolder(new RegistrationHandler(dbi)), "/register");
        log.info("Created mapping for Registration Handler");

        handler.addServletWithMapping(new ServletHolder(new LoginHandler(dbi)), "/login");
        log.info("Created mapping for Login Handler");

        handler.addServletWithMapping(new ServletHolder(new DashboardHandler(dbi)), "/dashboard");
        log.info("Created mapping for Dashboard Handler");

        // just for misc. admin commands
        handler.addServletWithMapping(new ServletHolder(new AdminHandler(dbi)), "/admin");
        log.info("Created mapping for Admin Handler");


    }

    public static void main(String[] args) throws Exception {

        // create an instance of the Jetty server and tell it to listen on port 8080
        final Server server = new Server();

        final HttpConfiguration https = new HttpConfiguration();
        https.addCustomizer(new SecureRequestCustomizer());

        final SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStorePath(ApiServer.class.getResource("/contactcube.jks").toExternalForm());
        sslContextFactory.setKeyStorePassword("123456");
        sslContextFactory.setKeyManagerPassword("123456");

        final ServerConnector connector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory, "http/1.1"),
                new HttpConnectionFactory(https));
        connector.setPort(8080);

        server.setConnectors(new Connector[]{connector});


        // create an instance of this ApiServer and set the server's handler
        final ApiServer api = new ApiServer();
        server.setHandler(api.handler);

        // start the server
        server.start();
        log.info("Server is now listening on port: 8080");
        server.join();

    }


}

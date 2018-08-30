package handler;

import model.Registrant;
import org.skife.jdbi.v2.DBI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Matthew
 */
public class RegistrationHandler extends AbstractHandler {

    public RegistrationHandler(final DBI dbi) {
        super(dbi);
    }


    /**
     * Handle POST requests to the registration handler
     *
     * @param req                   the servlet request
     * @param resp                  the response
     * @throws ServletException     there was an issue with the input
     * @throws IOException          there was an issue with the connection
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // get the IP that the request came from
        final String ip = this.getIpAddress(req);
        System.out.println("POST request received from: " + ip);

        // deserialize the JSON to a registrant
        final Registrant registrant = gson.fromJson(req.getReader(), Registrant.class);
        if (registrant == null) {
            // there was an issue with the JSON payload, display error
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // quick debugging
        System.out.println("Received a registration request from: " + ip + " for the username \"" +
                registrant.name + "\" with the password hash \"" + registrant.password + "\"");

        // say that the request was a success
        resp.setStatus(HttpServletResponse.SC_OK);

    }
}

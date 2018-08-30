package smallproject.handler;

import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.extension.ExtensionFactory;
import smallproject.dao.UserDao;
import smallproject.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Matthew
 */
public class RegistrationHandler extends AbstractHandler {

    private static final String SQL_LOOKUP_USER = "select id,name,password from users where name = ?";

    public RegistrationHandler(final Jdbi dbi) {
        super(dbi);
        dbi.useExtension(UserDao.class, UserDao::createTable);
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

        // deserialize the JSON to a user

        try {
            final User user = gson.fromJson(req.getReader(), User.class);
            if (user == null) {
                System.out.println("Unable to parse user.");
                // there was an issue with the JSON payload, display error
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }

            // insert the user into the database and get the userId
            long id = dbi.withExtension(UserDao.class, dao -> dao.insert(user));
            System.out.println("User " + user.name + " created with ID: " + id);

        } catch (final Exception ignored) {
            ignored.printStackTrace();
        }


        // say that the request was a success
        resp.setStatus(HttpServletResponse.SC_OK);

    }



}

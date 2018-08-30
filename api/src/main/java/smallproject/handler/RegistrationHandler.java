package smallproject.handler;

import com.google.gson.JsonObject;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
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

    private static final String ERROR_DESERIALIZE_FAIL = "unable to construct user from payload";

    public RegistrationHandler(final Jdbi dbi) {
        super(dbi);
        dbi.useExtension(UserDao.class, UserDao::createTable);
    }

    /**
     * Handle POST requests to the registration handler
     *
     * @param req  the servlet request
     * @param response the response
     * @throws ServletException there was an issue with the input
     * @throws IOException      there was an issue with the connection
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

        // get the IP that the request came from
        final String ip = this.getIpAddress(req);

        // json object used for the response
        final JsonObject payload = new JsonObject();

        // deserialize the JSON to a user
        final User user = gson.fromJson(req.getReader(), User.class);
        if (user == null) {
            // there was an issue with the JSON payload, display error
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            payload.addProperty("status", "error");
            payload.addProperty("message", ERROR_DESERIALIZE_FAIL);
            response.getWriter().write(payload.toString());
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }

        try (final Handle h = dbi.open()) {
            UserDao dao = h.attach(UserDao.class);
            final User registeredUser = dao.getUserByEmail(user.email);
            if (registeredUser != null && registeredUser.id != -1) {
                payload.addProperty("status", "error");
                payload.addProperty("message", "user already exists!");
            } else {
                // insert the user into the database and get the userId
                long id = dao.insert(user);
                payload.addProperty("status", "success");
                payload.addProperty("userId", id);
            }
        }

        // say that the request was a success
        ok(response, payload);

    }

    protected void ok(final HttpServletResponse response, final JsonObject payload) throws IOException {
        respond(response, HttpServletResponse.SC_OK, payload);
    }

    private void respond(final HttpServletResponse response, final int opcode, final JsonObject payload) throws IOException {
        response.setStatus(opcode);
        response.getWriter().write(payload.toString());
        response.getWriter().flush();
    }

}

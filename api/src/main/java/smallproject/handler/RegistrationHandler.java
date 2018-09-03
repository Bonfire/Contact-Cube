package smallproject.handler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import smallproject.dao.SessionDao;
import smallproject.dao.UserDao;
import smallproject.model.Session;
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
     * @param req      the servlet request
     * @param response the response
     * @throws ServletException there was an issue with the input
     * @throws IOException      there was an issue with the connection
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

        // get the IP that the request came from
        final String ip = this.getIpAddress(req);

        final User user = new Gson().fromJson(req.getReader(), User.class);
        if (user == null || user.email == null || user.firstname == null || user.lastname == null) {
            log.warning("Registration failed for user: " + user);
            // there was an issue with the JSON payload, display error
            error(response, ERROR_DESERIALIZE_FAIL);
            return;
        }


        // open connection to the database
        try (final Handle h = dbi.open()) {
            // attach the handle to the userdao
            UserDao dao = h.attach(UserDao.class);
            // look up the email in the database
            final User registeredUser = dao.getUserByEmail(user.email);
            if (registeredUser != null && registeredUser.id != -1) {
                // user already exists, lets see if the password is correct...
                final User authenticatedUser = dao.userLogin(user.email, user.password);
                if (authenticatedUser == null) {
                    // email exists in the database, but password was wrong, return an error
                    log.warning("Registration failed for user: \"" + user.email + "\", user already exists!");
                    error(response, "user already exists!");
                } else {
                    // user email already exists... but password is correct, so let's just log them in
                    final SessionDao sessionDao = h.attach(SessionDao.class);
                    final Session session = sessionDao.create(authenticatedUser, ip);
                    sendSession(response, session);
                }
            } else {
                dao.insert(user);
                final SessionDao sessionDao = h.attach(SessionDao.class);
                final Session session = sessionDao.create(user, ip);
                sendSession(response, session);
            }
        }

    }

    private void sendSession(HttpServletResponse response, Session session) throws IOException {
        if (session != null) {
            log.info("Session sent for user: " + session.getUserId() + ", token: " + session.getToken());
            final JsonObject payload = new JsonObject();
            payload.addProperty("token", session.token);
            ok(response, payload);
        }
    }

    private void error(final HttpServletResponse response, final String message) throws IOException {
        final JsonObject payload = new JsonObject();
        payload.addProperty("error", message);
        respond(response, HttpServletResponse.SC_BAD_REQUEST, payload);
    }

    private void ok(final HttpServletResponse response, final JsonObject payload) throws IOException {
        respond(response, HttpServletResponse.SC_OK, payload);
    }

    private void respond(final HttpServletResponse response, final int opcode, final JsonObject payload) throws IOException {
        response.setStatus(opcode);
        response.getWriter().write(payload.toString());
        response.getWriter().flush();
    }

}

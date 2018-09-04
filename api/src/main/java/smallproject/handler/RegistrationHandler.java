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
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * @author Matthew
 */
public class RegistrationHandler extends AbstractHandler {

    private static final Predicate<String> PATTERN_EMAIL = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$").asPredicate();

    private static final String ERROR_DESERIALIZE_FAIL = "unable to construct user from payload";
    private static final String ERROR_INVALID_EMAIL = "invalid email address";
    private static final String ERROR_INVALID_PASSWORD = "invalid password";
    private static final String ERROR_INVALID_FIRST_NAME = "invalid first name";
    private static final String ERROR_INVALID_LAST_NAME = "invalid last name";

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

        // take the payload and deserialize it to a User
        final User registrant = new Gson().fromJson(getPayload(req.getReader()), User.class);
        if (registrant == null) {
            // if the object is null that means deserialization failed... return error
            error(response, ERROR_DESERIALIZE_FAIL);
            return;
        }

        // validate email address
        if (registrant.email == null || registrant.email.isEmpty() || !PATTERN_EMAIL.test(registrant.email)) {
            error(response, ERROR_INVALID_EMAIL);
            return;
        }

        // validate password
        if (registrant.password == null || registrant.password.isEmpty()) {
            error(response, ERROR_INVALID_PASSWORD);
            return;
        }

        // validate first name
        if (registrant.firstname == null || registrant.firstname.isEmpty()) {
            error(response, ERROR_INVALID_FIRST_NAME);
            return;
        }

        // validate last name
        if (registrant.lastname == null || registrant.lastname.isEmpty()) {
            error(response, ERROR_INVALID_LAST_NAME);
            return;
        }

        // open connection to the database
        try (final Handle h = dbi.open()) {
            // attach the handle to the userdao
            UserDao dao = h.attach(UserDao.class);
            // look up the email in the database
            final User registeredUser = dao.getUserByEmail(registrant.email);
            if (registeredUser != null && registeredUser.id != -1) {
                // user already exists, lets see if the password is correct...
                final User authenticatedUser = dao.userLogin(registrant.email, registrant.password);
                if (authenticatedUser == null) {
                    // email exists in the database, but password was wrong, return an error
                    log.warning("Registration failed for user: \"" + registrant.email + "\", user already exists!");
                    error(response, "user already exists!");
                } else {
                    // user email already exists... but password is correct, so let's just log them in
                    final SessionDao sessionDao = h.attach(SessionDao.class);
                    final Session session = sessionDao.create(authenticatedUser, ip);
                    sendSession(response, session);
                }
            } else {
                long id = dao.insert(registrant);
                registrant.setId(id);
                final SessionDao sessionDao = h.attach(SessionDao.class);
                final Session session = sessionDao.create(registrant, ip);
                sendSession(response, session);
            }
        }
        req.getReader().close();
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

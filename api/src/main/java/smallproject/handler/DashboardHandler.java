package smallproject.handler;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
public class DashboardHandler extends AbstractHandler {

    public DashboardHandler(final Jdbi dbi) {
        super(dbi);
        dbi.useExtension(UserDao.class, UserDao::createTable);
    }

    /**
     * Handle POST requests to the dashboard handler
     *
     * @param req      the servlet request
     * @param response the response
     * @throws ServletException there was an issue with the input
     * @throws IOException      there was an issue with the connection
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {

        // get the IP that the request came from
        final String ip = this.getIpAddress(req);

        final JsonElement element = new JsonParser().parse(getPayload(req.getReader()));
        if (!element.isJsonObject()) {
            badRequest(response, "payload is not a valid JSON object!");
            return;
        }

        // json has been verified, we can safefly convert it to a JSON object
        final JsonObject json = element.getAsJsonObject();

        // ensure the session token was sent with the JSON object
        if (!json.has("token")) {
            // there was no token, 2319!
            error(response, HttpServletResponse.SC_FORBIDDEN, "token not present!");
            return;
        }

        // token was provided, lets make sure it is valid...
        final String token = json.get("token").getAsString();
        if (token == null || token.isEmpty() || token.length() != 64) {
            badRequest(response, "invalid token received!");
            return;
        }

        // everything was good, but we don't do anything yet
        final JsonObject payload = new JsonObject();
        payload.addProperty("status", "not implemented yet!");
        ok(response, payload);

    }

}

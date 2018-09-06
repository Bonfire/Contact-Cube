package smallproject.handler;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import smallproject.dao.ContactDao;
import smallproject.dao.SessionDao;
import smallproject.dao.UserDao;
import smallproject.model.Session;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Matthew
 */
public abstract class AbstractHandler extends HttpServlet {

    static final String KEY_SUCCESS = "success";

    // GSON singleton to be used by every handler for deserialization/serialization of JSON
    static final Gson gson = new Gson();

    static final Logger log = Logger.getGlobal();

    final Jdbi dbi;

    AbstractHandler(final Jdbi dbi) {
        this.dbi = dbi;
    }

    String getIpAddress(final HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }

    String getPayload(final BufferedReader reader) {
        try {
            return String.join("", CharStreams.readLines(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    void badRequest(final HttpServletResponse response, final String message) throws IOException {
        error(response, HttpServletResponse.SC_BAD_REQUEST, message);
    }

    void ok(final HttpServletResponse response, final JsonObject payload) throws IOException {
        respond(response, HttpServletResponse.SC_OK, payload);
    }

    private void respond(final HttpServletResponse response, final int opcode, final JsonObject payload) throws IOException {
        response.setStatus(opcode);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.getWriter().write(payload.toString());
        log.info("status: " + opcode + ", payload: " + payload.toString());
        response.getWriter().flush();
    }

    void error(final HttpServletResponse response, final int opcode, final String message) throws IOException {
        final JsonObject payload = new JsonObject();
        payload.addProperty("error", message);
        respond(response, opcode, payload);
    }

    void sendSession(HttpServletResponse response, Session session) throws IOException {
        if (session != null) {
            log.info("Session sent for user: " + session.userId + ", token: " + session.token);
            final JsonObject payload = new JsonObject();
            payload.addProperty("token", session.token);
            ok(response, payload);
        }
    }

}

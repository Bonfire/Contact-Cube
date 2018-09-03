package smallproject.handler;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import org.jdbi.v3.core.Jdbi;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * @author Matthew
 */
public abstract class AbstractHandler extends HttpServlet {

    // GSON singleton to be used by every handler for deserialization/serialization of JSON
    static final Gson gson = new Gson();

    protected static final Logger log = Logger.getGlobal();

    protected final Jdbi dbi;

    AbstractHandler(final Jdbi dbi) {
        this.dbi = dbi;
    }

    protected String getIpAddress(final HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }

    protected String getPayload(final BufferedReader reader) {
        try {
            return String.join("", CharStreams.readLines(reader));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

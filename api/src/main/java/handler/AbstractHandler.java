package handler;

import com.google.gson.Gson;
import org.skife.jdbi.v2.DBI;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Matthew
 */
public abstract class AbstractHandler extends HttpServlet {

    // GSON singleton to be used by every handler for deserialization/serialization of JSON
    protected static final Gson gson = new Gson();

    protected final DBI dbi;

    AbstractHandler(final DBI dbi) {
        this.dbi = dbi;
    }

    protected String getIpAddress(final HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) ip = request.getRemoteAddr();
        return ip;
    }

}

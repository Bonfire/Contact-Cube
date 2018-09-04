package smallproject.handler;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import smallproject.dao.SessionDao;
import smallproject.dao.UserDao;
import smallproject.model.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Matthew
 */
public class AdminHandler extends AbstractHandler {

    public AdminHandler(final Jdbi dbi) {
        super(dbi);
        dbi.useExtension(UserDao.class, UserDao::createTable);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String action = req.getParameter("a");
        if (action == null) {
            error(resp, "bad request");
            return;
        }

        final JsonObject obj = new JsonObject();

        switch (action) {
            case "truncate_users":
                dbi.useExtension(UserDao.class, UserDao::truncateTable);
                obj.addProperty("status", "users truncated!");
                log.info("Cleared user table");
                break;
            case "truncate_all":
                dbi.useExtension(UserDao.class, UserDao::truncateTable);
                log.info("cleared user table");
                dbi.useExtension(SessionDao.class, SessionDao::truncateTable);
                log.info("cleared session table");
                obj.addProperty("status", "all databases wiped!");
                break;
            case "user_count": {
                long count = dbi.withExtension(UserDao.class, UserDao::userCount);
                obj.addProperty("userCount", count);
                log.info("User count: " + count);
                break;
            }
            case "list_users": {
                final JsonArray array = new JsonArray();
                long count = dbi.withExtension(UserDao.class, UserDao::userCount);
                List<User> users = dbi.withExtension(UserDao.class, UserDao::allUsers);
                users.forEach(user -> {
                    final JsonObject userObj = new JsonObject();
                    userObj.addProperty("email", user.email);
                    userObj.addProperty("firstname", user.firstname);
                    userObj.addProperty("lastname", user.lastname);
                    array.add(userObj);
                });
                obj.addProperty("userCount", count);
                obj.add("users", array);
                break;
            }
        }

        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(obj.toString());
        resp.getWriter().flush();

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

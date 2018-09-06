package smallproject.handler;

import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jdbi.v3.core.Jdbi;
import smallproject.dao.ContactDao;
import smallproject.dao.SessionDao;
import smallproject.dao.UserDao;
import smallproject.model.Contact;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

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
        try {
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

            final long userId = dbi.withExtension(SessionDao.class, dao -> dao.userIdForSession(ip, token));
            if (userId <= 0) {
                badRequest(response, "invalid token received!");
                return;
            }

            if (!json.has("action")) {
                badRequest(response, "no action specified");
                return;
            }

            final String action = json.get("action").getAsString();
            final JsonObject payload = new JsonObject();

            switch (action) {
                case "get_contacts":
                    final List<Contact> contacts = dbi.withExtension(ContactDao.class, dao -> dao.getContactsForUserId(userId));
                    final JsonArray array = new JsonArray();
                    contacts.forEach(contact -> array.add(gson.toJsonTree(contact, Contact.class)));
                    payload.add("contacts", array);
                    payload.addProperty("success", "contacts retrieved");
                    break;
                case "add_contact":
                    final Contact contact = gson.fromJson(json, Contact.class);
                    if (contact == null) {
                        badRequest(response, "Unable to contruct contact object from JSON payload");
                        return;
                    }
                    contact.setUserId(userId);
                    long id = dbi.withExtension(ContactDao.class, dao -> dao.addContact(contact));
                    payload.addProperty("success", "contact added");
                    payload.addProperty("id", id);
                    break;
                case "remove_contacts":
                    if (!json.has("ids"))  {
                        badRequest(response, "no 'ids' key found in the JSON payload");
                        return;
                    }
                    final JsonElement _array = json.get("ids");
                    if (!_array.isJsonArray()) {
                        badRequest(response, "key 'ids' is not a JSON array");
                        return;
                    }
                    final JsonArray ids = _array.getAsJsonArray();
                    final Set<Integer> failed = Sets.newHashSet();
                    ids.forEach(e -> {
                        final String str = e.getAsString();
                        if (str != null && !str.isEmpty()) {
                            try {
                                final int uid = Integer.parseInt(str);
                                if (!dbi.withExtension(ContactDao.class, dao -> dao.removeContact(uid, userId)))
                                    failed.add(uid);
                            } catch (final Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    });
                    payload.addProperty("success",
                            failed.isEmpty() ? "all contacts removed" : "some UIDs couldn't be removed");
                    break;

            }

            ok(response, payload);
        } catch (final Exception e) {
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;
        }
    }

}

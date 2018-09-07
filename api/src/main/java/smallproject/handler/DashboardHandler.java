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

    private static final String ERROR_INVALID_PAYLOAD = "Payload is not a valid JSON object!";
    private static final String ERROR_INVALID_TOKEN = "invalid token received!";
    private static final String ERROR_MISSING_TOKEN = "Payload does not contain the 'token' key";
    private static final String ERROR_MISSING_ACTION = "Paylod does not contain the 'action' key";
    private static final String ERROR_MISSING_REMOVE_IDS = "Payload does not contain the 'ids' key";
    private static final String ERROR_MISMATCH_REMOVE_IDS = "Removal key 'ids' must be a JSON array!";
    private static final String ERROR_DESERIALIZE_CONTACT_FAIL = "Unable to deserialize JSON object to Contact object";

    private static final String SUCCESS_CONTACTS_RETREIVED = "contacts retreived";
    private static final String SUCCESS_CONTACT_ADDED = "contact added";
    private static final String SUCCESS_REMOVE_ALL = "all contacts removed";
    private static final String SUCCESS_LOGGED_OUT = "logged out";

    private static final String WARNING_REMOVE_SOME = "some UIDs couldn't be removed";

    public DashboardHandler(final Jdbi dbi) {
        super(dbi);
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
                badRequest(response, ERROR_INVALID_PAYLOAD);
                return;
            }

            // json has been verified, we can safefly convert it to a JSON object
            final JsonObject json = element.getAsJsonObject();

            // ensure the session token was sent with the JSON object
            if (!json.has("token")) {
                // there was no token, 2319!
                error(response, HttpServletResponse.SC_FORBIDDEN, ERROR_MISSING_TOKEN);
                return;
            }

            // token was provided, lets make sure it is valid...
            final String token = json.get("token").getAsString();
            if (token == null || token.isEmpty() || token.length() != 64) {
                badRequest(response, ERROR_INVALID_TOKEN);
                return;
            }

            final long userId = dbi.withExtension(SessionDao.class, dao -> dao.userIdForSession(ip, token));
            if (userId <= 0) {
                badRequest(response, ERROR_INVALID_TOKEN);
                return;
            }

            if (!json.has("action")) {
                badRequest(response, ERROR_MISSING_ACTION);
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
                    payload.addProperty(KEY_SUCCESS, SUCCESS_CONTACTS_RETREIVED);
                    break;
                case "add_contact":
                    final Contact contact = gson.fromJson(json, Contact.class);
                    if (contact == null) {
                        badRequest(response, ERROR_DESERIALIZE_CONTACT_FAIL);
                        return;
                    }
                    contact.setUserId(userId);
                    long id = dbi.withExtension(ContactDao.class, dao -> dao.addContact(contact));
                    payload.addProperty(KEY_SUCCESS, SUCCESS_CONTACT_ADDED);
                    payload.addProperty("id", id);
                    break;
                case "remove_contacts":
                    if (!json.has("ids"))  {
                        badRequest(response, ERROR_MISSING_REMOVE_IDS);
                        return;
                    }
                    final JsonElement _array = json.get("ids");
                    if (!_array.isJsonArray()) {
                        badRequest(response, ERROR_MISMATCH_REMOVE_IDS);
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
                    payload.addProperty(KEY_SUCCESS,
                            failed.isEmpty() ? SUCCESS_REMOVE_ALL : WARNING_REMOVE_SOME);
                    break;
                case "logout":
                    dbi.useExtension(SessionDao.class, dao -> dao.logout(userId, token));
                    payload.addProperty(KEY_SUCCESS, SUCCESS_LOGGED_OUT);
                    break;
            }

            ok(response, payload);
        } catch (final Exception e) {
            error(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
            return;
        }
    }

}

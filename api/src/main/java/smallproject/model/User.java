package smallproject.model;

import com.google.gson.annotations.SerializedName;

/**
 * Used for JSON serialization/deserialization
 *
 * @author Matthew
 */
public class User {

    public int id = -1;
    public String email;        // email/username of the user
    public String password;     // password HASH of the user
    public String firstname;    // first name of the user
    public String lastname;     // last name of the user

}

package smallproject.model;

import com.google.gson.annotations.SerializedName;

/**
 * Used for JSON serialization/deserialization
 *
 * @author Matthew
 */
public class User {

    public int id;
    public String name;        // username of the registrant
    public String password;    // password HASH of the registrant

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

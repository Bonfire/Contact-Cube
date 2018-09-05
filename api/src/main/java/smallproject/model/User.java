package smallproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used for JSON serialization/deserialization
 *
 * @author Matthew
 */
@NoArgsConstructor
@Data
public class User {

    public long id = -1;
    public String email;        // email/username of the user
    public String password;     // password HASH of the user
    public String firstname;    // first name of the user
    public String lastname;     // last name of the user

}

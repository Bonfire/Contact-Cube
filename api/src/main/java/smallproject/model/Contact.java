package smallproject.model;

import lombok.*;

/**
 * @author Matthew
 */
@NoArgsConstructor
@Data
public class Contact {

    public int id;
    public String firstName, lastName;
    public String address, city, state, zip;
    public String phone;
    public String email;
    public int userId;

}

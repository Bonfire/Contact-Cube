package smallproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Matthew
 */
@NoArgsConstructor
@Data
public class Session {

    public long userId;
    public String token;
    public String ip;

}

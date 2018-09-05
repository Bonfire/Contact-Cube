package smallproject.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Matthew
 */
@NoArgsConstructor
@Data
public class Session {

    public String token;
    public String ip;
    public long userId;

}

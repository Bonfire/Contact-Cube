package smallproject.model;

/**
 * @author Matthew
 */
public class Session {

    public String token;
    public String ip;
    public int userId;

    public Session() {
    }

    public Session(final int userId, final String ip, final String token) {
        this.userId = userId;
        this.ip = ip;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}

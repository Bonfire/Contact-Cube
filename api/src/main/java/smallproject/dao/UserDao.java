package smallproject.dao;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindFields;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import smallproject.model.User;

import java.util.List;

/**
 * @author Matthew
 */
public interface UserDao {

    @SqlUpdate(
            "CREATE TABLE IF NOT EXISTS users " +
                    "(" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "email VARCHAR(255) NOT NULL," +
                    "password VARCHAR(255) NOT NULL," +
                    "firstname VARCHAR(255) NOT NULL," +
                    "lastname VARCHAR(255) NOT NULL" +
                    ")"
    )
    void createTable();

    @SqlUpdate("TRUNCATE TABLE users")
    void truncateTable();

    @SqlQuery("SELECT count(*) FROM users")
    int userCount();

    @SqlQuery("SELECT * from users")
    @RegisterBeanMapper(User.class)
    List<User> allUsers();

    /**
     * Inserts the user into the database
     *
     * @param user the user to insert into the database
     * @return the created ID (auto-increment) of the user
     */
    @SqlUpdate("INSERT INTO users (email, password, firstname, lastname) values (:email, :password, :firstname, :lastname)")
    @GetGeneratedKeys("id")
    long insert(@BindFields final User user);

    @SqlQuery("SELECT * FROM users WHERE email = ?")
    @RegisterBeanMapper(User.class)
    User getUserByEmail(final String email);

    /**
     * Looks up into the database for an matching email and password pair. If one is found, it will return
     * the {@link User} object. If not, it will return <tt>null</tt>.
     *
     * @param email    the email to lookup
     * @param password the password corresponding to the email
     * @return User if a matching pair is found
     * <tt>null</tt> if no matching user found
     */
    @SqlQuery("SELECT * FROM users WHERE email = ? AND password = ?")
    @RegisterBeanMapper(User.class)
    User userLogin(final String email, final String password);

}

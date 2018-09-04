package smallproject.dao;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindFields;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;
import smallproject.model.User;

import java.util.List;

/**
 * @author Matthew
 */
public interface UserDao {

    @SqlUpdate(
            "CREATE TABLE IF NOT EXISTS `users` (\n" +
                    "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
                    "  `dateCreated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "  `dateLastLoggedIn` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                    "  `firstName` varchar(50) NOT NULL,\n" +
                    "  `lastName` varchar(50) NOT NULL,\n" +
                    "  `email` varchar(254) NOT NULL,\n" +
                    "  `password` char(64) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci"
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
    User _checkLogin(final String email, final String password);

    @SqlUpdate("UPDATE users SET dateLastLoggedIn = CURRENT_TIMESTAMP WHERE id = :id")
    void loggedIn(@BindFields final User user);

    /**
     * Performs a login.
     *
     * @param email
     * @param password
     * @return
     */
    @Transaction
    default User login(final String email, final String password) {

        final User user = _checkLogin(email, password);
        if (user != null)
            this.loggedIn(user);
        return user;

    }

}

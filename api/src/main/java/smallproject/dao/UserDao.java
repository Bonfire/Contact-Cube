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
                    "name VARCHAR(255) NOT NULL," +
                    "password VARCHAR(255) NOT NULL" +
                    ")"
    )
    void createTable();

    /**
     * Inserts the user into the database
     *
     * @param user the user to insert into the database
     * @return the created ID (auto-increment) of the user
     */
    @SqlUpdate("INSERT INTO users (name, password) values (:name, :password)")
    @GetGeneratedKeys("id")
    long insert(@BindFields final User user);

    @SqlQuery("SELECT * FROM users WHERE name = ?")
    @RegisterBeanMapper(User.class)
    List<User> getUsersByName(final String name);

    @SqlQuery("SELECT * FROM users WHERE name = ?")
    @RegisterBeanMapper(User.class)
    User getUserByName(final String name);

}


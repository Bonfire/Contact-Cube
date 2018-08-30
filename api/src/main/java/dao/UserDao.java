package dao;

import model.User;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

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
    long insert(User user);

}


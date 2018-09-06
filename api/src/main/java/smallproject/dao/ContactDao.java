package smallproject.dao;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.BindFields;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import smallproject.model.Contact;
import smallproject.model.User;

import java.util.List;

/**
 * @author Matthew
 */
public interface ContactDao {

    /**
     * Creates the contacts table if it does not exist
     */
    @SqlUpdate("CREATE TABLE IF NOT EXISTS `contacts` (\n" +
            "  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,\n" +
            "  `firstName` varchar(50) NOT NULL,\n" +
            "  `lastName` varchar(50) NOT NULL,\n" +
            "  `address` varchar(150) DEFAULT NULL,\n" +
            "  `city` varchar(50) DEFAULT NULL,\n" +
            "  `state` varchar(24) DEFAULT NULL,\n" +
            "  `zip` varchar(10) DEFAULT NULL,\n" +
            "  `phone` varchar(15) DEFAULT NULL,\n" +
            "  `email` varchar(254) DEFAULT NULL,\n" +
            "  `userID` int(10) unsigned NOT NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  KEY `userID` (`userID`),\n" +
            "  CONSTRAINT `contacts_ibfk_1` FOREIGN KEY (`userID`) REFERENCES `users` (`id`)\n" +
            ") ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4")
    void createTable();

    @SqlQuery("SELECT * FROM contacts WHERE userId = ?")
    @RegisterBeanMapper(Contact.class)
    List<Contact> getContactsForUserId(final long userId);

    @SqlUpdate("INSERT INTO contacts (firstName, lastName, address, city, state, zip, phone, email, userID) " +
            "VALUES (:firstName, :lastName, :address, :city, :state, :zip, :phone, :email, :userId)")
    @GetGeneratedKeys("id")
    long addContact(@BindFields final Contact contact);

    @SqlUpdate("DELETE FROM contacts WHERE id = ? AND userId = ?")
    boolean removeContact(final int uid, final long userId);

}

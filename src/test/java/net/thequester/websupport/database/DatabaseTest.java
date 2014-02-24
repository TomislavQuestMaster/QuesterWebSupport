package net.thequester.websupport.database;

import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;
import net.thequester.websupport.model.QuestType;
import net.thequester.websupport.model.User;
import org.junit.Before;
import org.junit.Test;
import utility.TestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author tdubravcevic
 */
public class DatabaseTest {

	Database database;

	@Before
	public void setUp() {

		Connection connection = TestUtils.getLocalConnection(null);
		dropTable(connection, "quests");
		createQuestsTable(connection);

		dropTable(connection, "users");
		createUsersTable(connection);


		database = new Database(connection);
	}

	@Test
	public void insertingQuestDetails() throws DatabaseException {

		QuestDetails details = new QuestDetails(0, "prvi", "", 1, 1, QuestType.TOURIST, "", "tomo");

		database.insertQuestDetails(details);
	}

	@Test
	public void insertingUser() throws DatabaseException {

		User user1 = new User("tomo", "1234", "tomo@gmail.com");
		User user2 = new User("ja", "1234", "ja@gmail.com");

		database.insertUser(user1);
		database.insertUser(user2);
	}

	@Test
	public void distanceQuerying() throws DatabaseException {

		QuestDetails details = new QuestDetails(0, "Cvjetno", "", 45.792646, 15.960657, QuestType.TOURIST, "", "tomo");
		database.insertQuestDetails(details);

		Filter filter = new Filter(45.793364, 15.946323, 1114);
		List<QuestDetails> quests = database.getNearbyQuests(filter);

		assertEquals(1, quests.size());

		filter = new Filter(45.793364, 15.946323, 1113);
		quests = database.getNearbyQuests(filter);

		assertEquals(0, quests.size());
	}

	private boolean createQuestsTable(Connection connection) {

		Statement stmt;
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			return false;
		}

		try {
			stmt.execute("CREATE TABLE quests( id INT AUTO_INCREMENT, questName varchar(128), description varchar(500)" +
								 ", latitude double, longitude double, questType varchar(128), url varchar(128), owner varchar(128)" +
								 ", primary key (id));");
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	private boolean createUsersTable(Connection connection) {

		Statement stmt;
		try {
			stmt = connection.createStatement();
		} catch (SQLException e) {
			return false;
		}

		try {
			stmt.execute(
					"CREATE TABLE users( id INT AUTO_INCREMENT, username varchar(128), password varchar(500), email varchar(128), " +
							"primary key (id));");
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	private boolean dropTable(Connection connection, String name) {

		PreparedStatement statement;
		try {
			statement = connection.prepareStatement("DROP TABLE " + name);
			statement.execute();
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
}

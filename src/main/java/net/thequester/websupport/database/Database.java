package net.thequester.websupport.database;

import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;
import net.thequester.websupport.model.QuestType;
import net.thequester.websupport.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tdubravcevic
 */
public class Database {

	private final Connection connection;

	public Database(Connection connection) {

		this.connection = connection;
	}


	public List<QuestDetails> getNearbyQuests(Filter filter) throws DatabaseException {

        List<QuestDetails> quests = new ArrayList<QuestDetails>();

        PreparedStatement statement = getPreparedStatement(
                "SELECT * FROM quests WHERE " +
                        "acos(sin(?) * sin(latitude*pi()/180) +" +
                        " cos(?) * cos(latitude*pi()/180) * cos(longitude*pi()/180 - ?))*6366000 <= ?");

        try {
            statement.setDouble(1, filter.getLatitude()*Math.PI/180);
            statement.setDouble(2, filter.getLatitude()*Math.PI/180);
            statement.setDouble(3, filter.getLongitude()*Math.PI/180);
            statement.setDouble(4, filter.getRadius());

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
            quests.add(new QuestDetails(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(4),
                        resultSet.getDouble(5),
                        QuestType.valueOf(resultSet.getString(6)),
                        resultSet.getString(7),
						resultSet.getString(8)));
            }
            return quests;

        } catch (SQLException e) {
            throw new DatabaseException("Failed to query quests by location " + e.getMessage());
        }

    }

    public void insertQuestDetails(QuestDetails details) throws DatabaseException {

		PreparedStatement statement = getPreparedStatement(
                "INSERT INTO quests(id, questName, description, latitude, longitude, questType, url, owner) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

		try {

			statement.setInt(1, details.getId());
			statement.setString(2, details.getQuestName());
			statement.setString(3, details.getDescription());
			statement.setDouble(4, details.getLatitude());
			statement.setDouble(5, details.getLongitude());
			statement.setString(6, details.getQuestType().toString());
            statement.setString(7, details.getUrl());
			statement.setString(8, details.getOwner());
			statement.executeUpdate();

			statement.close();

		} catch (SQLException e) {
			throw new DatabaseException("Failed to insert quest details " + e.getMessage());
		}

	}


	public void insertUser(User user) throws DatabaseException {

		PreparedStatement statement = getPreparedStatement(
				"INSERT INTO users(username, password, email) VALUES (?, ?, ?)");

		try {

			statement.setString(1, user.getUsername());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getEmail());
			statement.executeUpdate();

			statement.close();

		} catch (SQLException e) {
			throw new DatabaseException("Failed to insert user " + e.getMessage());
		}
	}


	private PreparedStatement getPreparedStatement(String query) throws DatabaseException {

		try {
			return connection.prepareStatement(query);
		} catch (SQLException e) {
			throw new DatabaseException("Failed to create statement " + e.getMessage());
		}
	}
}

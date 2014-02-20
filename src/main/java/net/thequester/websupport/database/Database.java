package net.thequester.websupport.database;

import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author tdubravcevic
 */
public class Database {

	private final Connection connection;

	public Database(Connection connection) {

		this.connection = connection;
	}

	public ResultSet getNearbyQuests(Filter filter) throws DatabaseException {

        PreparedStatement statement = getPreparedStatement(
                "SELECT * FROM quests WHERE " +
                        "acos(sin(?) * sin(latitude) + cos(?) * cos(latitude) * cos(longitude - ?))*(180/pi())*60*1.1515*1609.344 <= ?");

        try {
            statement.setDouble(1, filter.getLatitude());
            statement.setDouble(2, filter.getLatitude());
            statement.setDouble(3, filter.getLongitude());
            statement.setDouble(4, filter.getRadius());

            return statement.executeQuery();

        } catch (SQLException e) {
            throw new DatabaseException("Failed to query quests by location " + e.getMessage());
        }

    }
	public void insertQuestDetails(QuestDetails details) throws DatabaseException {

		PreparedStatement statement = getPreparedStatement(
                "INSERT INTO quests(id, questName, description, latitude, longitude, questType, url) VALUES (?, ?, ?, ?, ?, ?, ?)");

		try {

			statement.setInt(1, details.getId());
			statement.setString(2, details.getQuestName());
			statement.setString(3, details.getDescription());
			statement.setDouble(4, details.getLatitude());
			statement.setDouble(5, details.getLongitude());
			statement.setString(6, details.getQuestType().toString());
            statement.setString(7, details.getUrl());
			statement.executeUpdate();

			statement.close();

		} catch (SQLException e) {
			throw new DatabaseException("Failed to insert quest details " + e.getMessage());
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

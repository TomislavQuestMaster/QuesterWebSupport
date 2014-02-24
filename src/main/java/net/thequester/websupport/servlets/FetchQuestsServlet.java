package net.thequester.websupport.servlets;

import net.thequester.websupport.database.Database;
import net.thequester.websupport.database.DatabaseException;
import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;
import net.thequester.websupport.model.Response;
import net.thequester.websupport.serializator.JsonSerializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

/**
 * @author tdubravcevic
 */
public class FetchQuestsServlet extends HttpServlet {


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) { /*report an error*/ }

		String json = jb.toString();

		JsonSerializer serializer = new JsonSerializer();
		Filter filter = (Filter) serializer.deserialize(json, Filter.class);

		Database database = new Database(getLocalConnection());
		List<QuestDetails> quests;
		try {
			quests = database.getNearbyQuests(filter);
		} catch (DatabaseException e) {
			response.getWriter().print(e.getMessage());
			return;
		}

		response.getWriter().print(quests.size());

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	public static Connection getLocalConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			return null;
		}

		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/questerdb", "root", "root");

		} catch (Exception e) {
			// TODO throw exception
			return null;
		}
	}
}

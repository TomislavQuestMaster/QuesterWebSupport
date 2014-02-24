package net.thequester.websupport.servlets;

import net.thequester.websupport.database.Database;
import net.thequester.websupport.database.DatabaseException;
import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;
import net.thequester.websupport.serializator.JsonSerializer;
import net.thequester.websupport.utility.Utilities;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author tdubravcevic
 */
public class FetchQuestsServlet extends HttpServlet {

	private final JsonSerializer serializer = new JsonSerializer();
	private final Database database = new Database(Utilities.getLocalConnection());

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String body = Utilities.getBody(request);
		Filter filter = (Filter) serializer.deserialize(body, Filter.class);

		List<QuestDetails> quests;
		try {
			quests = database.getNearbyQuests(filter);
		} catch (DatabaseException e) {
			response.getWriter().print(e.getMessage());
			return;
		}

		response.getWriter().print(serializer.serialize(quests));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

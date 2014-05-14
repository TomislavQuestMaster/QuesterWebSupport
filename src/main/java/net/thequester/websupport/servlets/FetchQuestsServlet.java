package net.thequester.websupport.servlets;

import net.thequester.websupport.database.repositories.QuestService;
import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;
import net.thequester.websupport.serializator.JsonSerializer;
import net.thequester.websupport.utility.Utilities;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tdubravcevic
 */
public class FetchQuestsServlet extends HttpServlet {

    //TODO transfer to MVC
	private final JsonSerializer serializer = new JsonSerializer();

	private QuestService service;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String body = Utilities.getBody(request);
		Filter filter = (Filter) serializer.deserialize(body, Filter.class);

		Iterable<QuestDetails> quests = service.filterQuests(filter);

		response.getWriter().print(serializer.serialize(quests));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

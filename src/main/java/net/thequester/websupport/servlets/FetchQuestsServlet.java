package net.thequester.websupport.servlets;

import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.Response;
import net.thequester.websupport.serializator.JsonSerializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

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

		response.getWriter().print(filter.getRadius());

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}

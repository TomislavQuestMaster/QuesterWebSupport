package net.thequester.websupport.servlets;

import net.thequester.websupport.FileManager;
import net.thequester.websupport.model.QuestDetails;
import net.thequester.websupport.serializator.JsonSerializer;
import net.thequester.websupport.utility.Utilities;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tomo.
 */
public class DownloadServlet extends HttpServlet {

	private final JsonSerializer serializer = new JsonSerializer();
	private final FileManager manager = new FileManager();

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String body = Utilities.getBody(request);
        QuestDetails quest = (QuestDetails) serializer.deserialize(body, QuestDetails.class);

		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment;filename=quest.zip");

		InputStream inputStream = getServletContext().getResourceAsStream(manager.getQuestLocation(quest));
        Utilities.writeToResponse(inputStream, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}

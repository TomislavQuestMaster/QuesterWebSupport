package net.thequester.websupport.servlets;

import net.thequester.websupport.database.Database;
import net.thequester.websupport.model.Filter;
import net.thequester.websupport.serializator.JsonSerializer;
import net.thequester.websupport.utility.Utilites;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Tomo.
 */
public class DownloadServlet extends HttpServlet {

	private final JsonSerializer serializer = new JsonSerializer();
	private final Database database = new Database(Utilites.getLocalConnection());

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String body = Utilites.getBody(request);

		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "attachment;filename=quest.txt");
		ServletContext ctx = getServletContext();
		InputStream is = ctx.getResourceAsStream("/quests/tomo/1_" + body + ".txt");

		int read;
		byte[] bytes = new byte[1024];
		OutputStream os = response.getOutputStream();

		while ((read = is.read(bytes)) != -1) {
			os.write(bytes, 0, read);
		}
		os.flush();
		os.close();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
}

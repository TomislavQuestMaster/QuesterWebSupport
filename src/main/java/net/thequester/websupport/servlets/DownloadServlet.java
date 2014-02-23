package net.thequester.websupport.servlets;

import net.thequester.websupport.model.Response;
import net.thequester.websupport.serializator.JsonSerializer;

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


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/plain");
        response.setHeader("Content-Disposition",
                "attachment;filename=downloadname.txt");
        ServletContext ctx = getServletContext();
        InputStream is = ctx.getResourceAsStream("/file.txt");

        int read=0;
        byte[] bytes = new byte[1024];
        OutputStream os = response.getOutputStream();

        while((read = is.read(bytes))!= -1){
            os.write(bytes, 0, read);
        }
        os.flush();
        os.close();
    }
}

package net.thequester.websupport.servlets;

import net.thequester.websupport.model.Response;
import net.thequester.websupport.serializator.JsonSerializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author tdubravcevic
 */
public class SupportServlet extends HttpServlet {


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JsonSerializer serializator = new JsonSerializer();
        response.getWriter().print(serializator.serialize(new Response(1,"Hey")));

    }
}

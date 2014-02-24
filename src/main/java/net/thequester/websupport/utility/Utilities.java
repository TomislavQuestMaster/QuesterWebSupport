package net.thequester.websupport.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author tdubravcevic
 */
public class Utilities {

	public static String getBody(HttpServletRequest request){

		StringBuilder stringBuilder = new StringBuilder();
		String line;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				stringBuilder.append(line);
		} catch (Exception e) {
			return null;
		}

		return stringBuilder.toString();
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

    public static void writeToResponse(InputStream is, HttpServletResponse response) throws IOException {

        OutputStream os = response.getOutputStream();

        int read;
        byte[] bytes = new byte[1024];
        while ((read = is.read(bytes)) != -1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        os.close();
    }
}

package utility;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author tdubravcevic
 */
public class TestUtils {

	public static Connection getLocalConnection(PrintWriter out) {

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception e) {
			out.println("Exception: " + e.getMessage());
			return null;
		}

		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/questerdb", "root", "root");

		} catch (Exception e) {
			// TODO throw exception
			out.println("Exception: " + e.getMessage());
			return null;
		}
	}
}

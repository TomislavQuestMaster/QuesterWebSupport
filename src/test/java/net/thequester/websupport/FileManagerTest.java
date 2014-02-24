package net.thequester.websupport;

import net.thequester.websupport.model.User;
import org.junit.Test;

/**
 * @author tdubravcevic
 */
public class FileManagerTest {

	FileManager manager = new FileManager();

	@Test
	public void createQuestsLocationTest(){

		User user = new User("tomo", "", "");
		manager.createNewUser(user);
	}

}

package net.thequester.websupport;

import net.thequester.websupport.model.User;

import java.io.File;

/**
 * @author tdubravcevic
 */
public class FileManager {

	public boolean createNewUser(User user){

		File dir = new File("web/quests/" + user.getUsername());
		return dir.mkdir();

	}
}

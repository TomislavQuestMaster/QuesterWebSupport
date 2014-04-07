package net.thequester.websupport;

import net.thequester.websupport.model.QuestDetails;
import net.thequester.websupport.model.User;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

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

    @Test
    public void getLocationTest(){

        QuestDetails details = new QuestDetails();
        details.setId(1L);
        details.setQuestName("Cvjetno");
        details.setOwner("tomo");

        String location = manager.getQuestLocation(details);

        assertEquals("/quests/tomo/1_Cvjetno.zip", location);
    }

}

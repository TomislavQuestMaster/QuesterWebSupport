package net.thequester.websupport;

import net.thequester.archiver.ArchiverException;
import net.thequester.model.Quest;
import net.thequester.websupport.model.QuestDetails;
import net.thequester.websupport.model.User;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * @author tdubravcevic
 */
public class FileManager {

	//TODO make better extract interface

	public boolean createNewUser(User user){

		File dir = new File("web/quests/" + user.getUsername());
		return dir.mkdir();

	}

    public String getQuestLocation(QuestDetails quest){

        return "/quests/" + quest.getOwner() + "/" + quest.getId() + "_" + quest.getQuestName() + ".zip";
    }

    public void save(Quest quest) throws ArchiverException {
        File file = new File("C:\\Users\\Tomo\\IdeaProjects\\QuesterWebSupport\\src\\" + quest.getId() + ".xml");

        try {
            JAXBContext jaxbContextMessage = JAXBContext.newInstance(Quest.class);
            Marshaller marshaller = jaxbContextMessage.createMarshaller();
            marshaller.marshal(quest, file);
        } catch (JAXBException e) {
            throw new ArchiverException("Failed to save quest progress: " +  e.getMessage());
        }
    }

    public Quest load(String name) throws ArchiverException {
        File file = new File("C:\\Users\\Tomo\\IdeaProjects\\QuesterWebSupport\\src\\" + name + ".xml");

        try {
            JAXBContext context = JAXBContext.newInstance(Quest.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Quest) unmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            throw new ArchiverException("Failed to load quest: " + e.getMessage());
        }
    }

}

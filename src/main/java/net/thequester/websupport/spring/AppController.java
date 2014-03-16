package net.thequester.websupport.spring;

import net.thequester.model.Connection;
import net.thequester.model.Node;
import net.thequester.model.Quest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.nio.file.Paths;

/**
 * Created by Tomo.
 */
@Controller
public class AppController {

    public static Quest quest = new Quest();

    @RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView addNodePage() {
        ModelAndView modelAndView = new ModelAndView("addNode");

        modelAndView.addObject("node", new Node());
        return modelAndView;
    }

    @RequestMapping(value="/connect", method=RequestMethod.GET)
    public ModelAndView connectPage() {
        ModelAndView modelAndView = new ModelAndView("addConnection");

        modelAndView.addObject("link", new Link());
        return modelAndView;
    }

    @RequestMapping(value="/add", method=RequestMethod.POST)
    public ModelAndView addingNode(@ModelAttribute Node node) throws JAXBException {

        ModelAndView modelAndView = new ModelAndView("hello");
        quest.getNodes().add(node);

        marshall();

        String message = "Node was successfully added.";
        modelAndView.addObject("name", message);

        return modelAndView;
    }

    @RequestMapping(value="/connect", method=RequestMethod.POST)
    public ModelAndView addingConnection(@ModelAttribute Link link) throws JAXBException {

        ModelAndView modelAndView = new ModelAndView("hello");

        Connection connection = quest.getConnections().get(link.getParent());
        if(connection==null){
            connection = new Connection();
        }
        connection.getChildren().add(link.getChild());
        quest.getConnections().put(link.getParent(),connection);

        connection = quest.getConnections().get(link.getChild());
        if(connection==null){
            connection = new Connection();
        }
        connection.getParents().add(link.getParent());
        quest.getConnections().put(link.getChild(),connection);

        marshall();

        String message = "Connection was successfully added.";
        modelAndView.addObject("name", message);

        return modelAndView;
    }

    private void marshall() throws JAXBException {
        JAXBContext jaxbContextMessage = JAXBContext.newInstance(Quest.class);
        Marshaller marshaller = jaxbContextMessage.createMarshaller();
        File file = new File(Paths.get("C://Users/Tomo/IdeaProjects/QuesterWebSupport/src/quest.xml").toUri());
        marshaller.marshal(quest,file);
    }

}


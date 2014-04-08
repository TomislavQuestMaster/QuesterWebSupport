package net.thequester.websupport.spring;

import net.thequester.model.*;
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

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView goToCreatePage(){
        ModelAndView mv = new ModelAndView("create_quest");
        mv.addObject("questLocation",new QuestLocation(45.81497, 15.97851));
        return mv;
    }

    @RequestMapping(value="/event", method=RequestMethod.GET)
    public ModelAndView addEventPage() {
        ModelAndView modelAndView = new ModelAndView("addEvent");

        //modelAndView.addObject("event", new Event());
        return modelAndView;
    }

    @RequestMapping(value="/add", method=RequestMethod.GET)
    public ModelAndView addNodePage() {
        ModelAndView modelAndView = new ModelAndView("addNode");
        modelAndView.addObject("nodes", quest.getNodes());
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

        quest.getNodes().add(node);
        marshall();

        return addNodePage();
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


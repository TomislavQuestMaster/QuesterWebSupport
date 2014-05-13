package net.thequester.websupport.servlets;

import net.thequester.archiver.ArchiverException;
import net.thequester.model.Quest;
import net.thequester.model.QuestLocation;
import net.thequester.processor.impl.EventProcessor;
import net.thequester.processor.impl.GameEngine;
import net.thequester.processor.impl.QuestProcessor;
import net.thequester.websupport.FileManager;
import net.thequester.websupport.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Tomo.
 */
@Controller
public class AngularAppController {

    @RequestMapping(value = "/app/save", method = RequestMethod.POST)
    public @ResponseBody
    Response save(@RequestBody Quest quest) {

        FileManager manager = new FileManager();
        try {
            manager.save(quest);
        } catch (ArchiverException e) {
            return new Response(0,e.getMessage());
        }

        return new Response(1,"Success");
    }

    @RequestMapping(value = "/app/test", method = RequestMethod.POST)
    public @ResponseBody
    List<Integer> runTest(@RequestBody List<QuestLocation> path) throws ArchiverException {

        Quest quest = new FileManager().load("123");
        GameEngine engine = new GameEngine(new QuestProcessor(quest), new EventProcessor(quest.getEvents()));

        engine.startQuest(quest);
        for(QuestLocation location : path){
            engine.processLocation(location);
        }

        return engine.getGamePath();
    }

    @RequestMapping(value = "/app/load", method = RequestMethod.GET)
    public @ResponseBody
    Quest load() throws ArchiverException {

        return new FileManager().load("123");
    }

}

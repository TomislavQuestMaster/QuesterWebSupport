package net.thequester.websupport.servlets;

import net.thequester.archiver.ArchiverException;
import net.thequester.model.Node;
import net.thequester.model.Quest;
import net.thequester.model.QuestLocation;
import net.thequester.processor.impl.QuestProcessor;
import net.thequester.websupport.FileManager;
import net.thequester.websupport.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by Tomo.
 */
@Controller
public class AngularAppController {

    @RequestMapping(value = "/app/hook", method = RequestMethod.POST)
    public @ResponseBody
    Response test(@RequestBody Quest quest) {

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
    Response run(@RequestBody List<QuestLocation> path) throws ArchiverException {

        Quest quest = new FileManager().load("123");
        QuestProcessor processor = new QuestProcessor(quest);

        int lastId = 0;
        for(QuestLocation location : path){
            Node node = processor.processLocation(lastId, location);
            if(node != null){
                lastId = node.getId();
            }
        }


        FileManager manager = new FileManager();
        try {
            manager.save(quest);
        } catch (ArchiverException e) {
            return new Response(0,e.getMessage());
        }

        return new Response(1,"Success");
    }

}
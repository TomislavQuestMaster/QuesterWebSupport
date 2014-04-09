package net.thequester.websupport.servlets;

import net.thequester.websupport.model.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Tomo.
 */
@Controller
public class AngularAppController {

    @RequestMapping(value = "/app/hook", method = RequestMethod.POST)
    public @ResponseBody
    Response test(@RequestBody Response response) {

        System.out.println(response.getMessage());

        return new Response(1,"Hello back");
    }

}

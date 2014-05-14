package net.thequester.websupport.servlets;

import com.google.common.collect.Lists;
import net.thequester.websupport.database.repositories.QuestService;
import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import java.util.List;

/**
 * @author tdubravcevic
 */
@Controller
public class FetchQuestsServlet extends HttpServlet {

	@Autowired
	private QuestService service;

	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	public @ResponseBody List<QuestDetails> save(@RequestBody Filter filter) {

		return Lists.newArrayList(service.filterQuests(filter));
	}

	@RequestMapping(value = "/saveDetails", method = RequestMethod.POST)
	public void save(@RequestBody QuestDetails quest) {

		service.save(quest);
	}

}

package net.thequester.websupport.servlets;

import com.google.common.collect.Lists;
import net.thequester.websupport.database.repositories.QuestService;
import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * @author tdubravcevic
 */
@Controller
public class FetchQuestsServlet extends HttpServlet {

	@Autowired
	private QuestService service;

	@RequestMapping(value = "/filter", method = RequestMethod.POST)
	public
	@ResponseBody
	List<QuestDetails> save(@RequestBody Filter filter) {

		return Lists.newArrayList(service.filterQuests(filter));
	}

	@RequestMapping(value = "/saveDetails", method = RequestMethod.POST)
	public void save(@RequestBody QuestDetails quest) {

		service.save(quest);
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public
	@ResponseBody
	String uploadFileHandler(@RequestParam("file") MultipartFile file) {

		String name = file.getOriginalFilename();

		if (!file.isEmpty()) {

			try {
				byte[] bytes = file.getBytes();

				File dir = new File("C:\\Users\\tdubravcevic\\Downloads");

				//TODO trasfer to some file manager
				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				return "You successfully uploaded file=" + name;
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}

		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}

	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	public void getFile(HttpServletResponse response) {

		//TODo file manager should serve the file
		File file = new File("C:\\Users\\tdubravcevic\\Downloads\\bla.jpg");

		try {
			InputStream is = new FileInputStream(file);
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException ex) {
			throw new RuntimeException("IOError writing file to output stream");
		}

	}
}

package net.thequester.websupport;

import net.thequester.websupport.database.repositories.QuestRepository;
import net.thequester.websupport.database.repositories.QuestService;
import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static java.lang.Math.*;

/**
 * @author tdubravcevic
 */
@ContextConfiguration(classes = SystemTestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseTest {

	@Autowired
	QuestRepository repository;

	private QuestService service;

	@Before
	public void setUp(){

		service = new QuestService(repository);
	}


	@Test
	public void test(){

		service.create(new QuestDetails.Builder().at(0.0,0.0).build());
		service.create(new QuestDetails.Builder().at(1.0,1.0).build());

		Iterable<QuestDetails> filtered = service.getNearbyQuests(new Filter(0.0,0.0,8122744));

		System.out.println( acos(sin(1.0)*sin(0.0)+cos(1.0)*cos(0.0)*cos(1.0))*6373000);

		for(QuestDetails details : filtered){
			System.out.println(details.getLatitude());
			System.out.println(details.getLongitude());
		}
	}


}

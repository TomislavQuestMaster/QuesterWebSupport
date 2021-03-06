package net.thequester.websupport.database.providers;

import net.thequester.websupport.SystemTestConfiguration;
import net.thequester.websupport.database.repositories.QuestRepository;
import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * @author tdubravcevic
 */
@ContextConfiguration(classes = SystemTestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QuestProviderTest {

	@Autowired
	QuestRepository repository;

	private QuestProvider service;

	@Before
	public void setUp() {

		service = new QuestProvider(repository);
	}

	@Test
	public void happyPath() {

		service.save(new QuestDetails.Builder().named("A").at(0.0, 0.0).build());
		service.save(new QuestDetails.Builder().named("B").at(1.0, 1.0).build());

		Iterable<QuestDetails> filtered = service.filterQuests(new Filter(0.0, 0.0, 8122744));

		for (QuestDetails details : filtered) {
			assertEquals("A", details.getQuestName());
		}
	}

}

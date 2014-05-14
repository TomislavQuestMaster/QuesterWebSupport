package net.thequester.websupport;

import net.thequester.websupport.database.repositories.FilterExpressionCreator;
import net.thequester.websupport.model.Filter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author tdubravcevic
 */
public class FilterExpressionCreatorTest {

	@Test
	public void distanceExpressionCreated() {

		assertEquals("acos(sin(questDetails.latitude) * 0.0 + cos(questDetails.latitude) * 1.0 * cos(questDetails.longitude - 0.0)) <= 1" +
							 ".0", FilterExpressionCreator.createExpression(new Filter(0.0, 0.0, 6373000)).toString());
	}

	@Test
	public void ownerExpressionCreated() {

		Filter filter = new Filter();
		filter.setOwner("test");
		assertEquals("questDetails.owner like test", FilterExpressionCreator.createExpression(filter).toString());
	}

	@Test
	public void nameExpressionCreated() {

		Filter filter = new Filter();
		filter.setName("test");
		assertEquals("questDetails.questName like test", FilterExpressionCreator.createExpression(filter).toString());
	}

	@Test
	public void descriptionExpressionCreated() {

		Filter filter = new Filter();
		filter.setDescription("test");
		assertEquals("questDetails.description like test", FilterExpressionCreator.createExpression(filter).toString());
	}

	@Test
	public void combinedExpressionCreated() {

		Filter filter = new Filter();
		filter.setName("quest");
		filter.setOwner("test");
		assertEquals("questDetails.questName like quest && questDetails.owner like test",
					 FilterExpressionCreator.createExpression(filter).toString());
	}
}

package net.thequester.websupport.database.repositories;

import com.mysema.query.types.expr.BooleanExpression;
import net.thequester.websupport.model.Filter;

import static com.mysema.query.types.expr.MathExpressions.*;
import static java.lang.Math.*;
import static net.thequester.websupport.model.QQuestDetails.*;

/**
 * @author tdubravcevic
 */
public class FilterExpressionCreator {

	public static BooleanExpression createExpression(Filter filter) {

		BooleanExpression expression = null;

		if (distanceParametersSet(filter)) {
			expression = addExpression(expression, getDistanceExpression(filter));
		}

		if (filter.getName() != null) {
			expression = addExpression(expression, questDetails.questName.like(filter.getName()));
		}

		if (filter.getOwner() != null) {
			expression = addExpression(expression, questDetails.owner.like(filter.getOwner()));
		}

		if (filter.getDescription() != null) {
			expression = addExpression(expression, questDetails.description.like(filter.getDescription()));
		}

		return expression;
	}

	private static boolean distanceParametersSet(Filter filter) {

		return filter.getLatitude() != null && filter.getLongitude() != null && filter.getRadius() != null;
	}

	private static BooleanExpression getDistanceExpression(Filter filter) {

		return acos(sin(questDetails.latitude).multiply(sin(filter.getLatitude()))
											  .add(cos(questDetails.latitude).multiply(cos(filter.getLatitude()))
																			 .multiply(cos(questDetails.longitude.subtract(filter.getLongitude())))))
				.loe(filter.getRadius() / 6373000);
	}

	private static BooleanExpression addExpression(BooleanExpression expression, BooleanExpression newExpression) {

		if (expression == null) {
			return newExpression;
		}

		return expression.and(newExpression);
	}
}

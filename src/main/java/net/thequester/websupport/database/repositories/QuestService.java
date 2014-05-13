package net.thequester.websupport.database.repositories;

import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;

import static com.mysema.query.types.expr.MathExpressions.*;
import static net.thequester.websupport.model.QQuestDetails.*;

/**
 * Created by Tomo.
 */
public class QuestService {

	private QuestRepository repository;

	public QuestService(QuestRepository repository) {

		this.repository = repository;
	}

	public QuestDetails create(QuestDetails quest) {

		return repository.save(quest);
	}

	public Iterable<QuestDetails> getQuests() {

		return repository.findAll();
	}

	public Iterable<QuestDetails> getNearbyQuests(Filter filter) {

		return repository.findAll(acos(sin(questDetails.latitude).multiply(Math.sin(filter.getLatitude()))
																 .add(cos(questDetails.latitude).multiply(Math.cos(filter.getLatitude()))
																								.multiply(cos(questDetails.longitude.subtract(
																										filter.getLongitude()))))).loe(filter.getRadius()/6373000));
	}

}

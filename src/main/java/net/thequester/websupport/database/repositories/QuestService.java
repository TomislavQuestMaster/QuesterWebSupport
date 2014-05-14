package net.thequester.websupport.database.repositories;

import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;

/**
 * Created by Tomo.
 */
public class QuestService {

	private QuestRepository repository;

	public QuestService(QuestRepository repository) {

		this.repository = repository;
	}

	public QuestDetails save(QuestDetails quest) {

		return repository.save(quest);
	}

	public Iterable<QuestDetails> filterQuests(Filter filter) {

		return repository.findAll(FilterExpressionCreator.createExpression(filter));
	}


}

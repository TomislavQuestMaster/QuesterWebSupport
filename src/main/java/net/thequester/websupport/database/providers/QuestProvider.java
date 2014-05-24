package net.thequester.websupport.database.providers;

import net.thequester.websupport.database.repositories.FilterExpressionCreator;
import net.thequester.websupport.database.repositories.QuestRepository;
import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;

/**
 * Created by Tomo.
 */
public class QuestProvider {

	private QuestRepository repository;

	public QuestProvider(QuestRepository repository) {

		this.repository = repository;
	}

	public QuestDetails save(QuestDetails quest) {

		return repository.save(quest);
	}

	public Iterable<QuestDetails> filterQuests(Filter filter) {

		return repository.findAll(FilterExpressionCreator.createExpression(filter));
	}

}

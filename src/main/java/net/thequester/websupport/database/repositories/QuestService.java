package net.thequester.websupport.database.repositories;

import net.thequester.websupport.model.Filter;
import net.thequester.websupport.model.QuestDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static com.mysema.query.types.expr.MathExpressions.*;
import static net.thequester.websupport.model.QQuestDetails.*;

/**
 * Created by Tomo.
 */
@Service
public class QuestService {

	@Resource
	private QuestRepository repository;

	@Transactional
	public QuestDetails create(QuestDetails quest) {

		return repository.save(quest);
	}

	@Transactional
	public Iterable<QuestDetails> getNearbyQuests(Filter filter) {

		return repository.findAll(acos(sin(questDetails.latitude).multiply(Math.sin(filter.getLatitude()))
																 .add(cos(questDetails.latitude).multiply(Math.cos(filter.getLatitude()))
																								.multiply(questDetails.longitude.subtract(
																										filter.getLongitude())))).loe(filter.getRadius()/6366000));
	}

}

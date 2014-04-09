package net.thequester.websupport.database.repositories;

import net.thequester.websupport.model.QuestDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by Tomo.
 */
public interface QuestRepository extends JpaRepository<QuestDetails, Long>, QueryDslPredicateExecutor<QuestDetails> {
}

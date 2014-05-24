package net.thequester.websupport.database.repositories;

import net.thequester.websupport.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

/**
 * Created by Tomo.
 */
public interface ReviewRepository extends JpaRepository<Review, Long>, QueryDslPredicateExecutor<Review> {
}

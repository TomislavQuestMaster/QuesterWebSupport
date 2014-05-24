package net.thequester.websupport.database.providers;

import com.google.common.collect.Lists;
import net.thequester.websupport.database.repositories.ReviewRepository;
import net.thequester.websupport.model.QuestDetails;
import net.thequester.websupport.model.Review;
import net.thequester.websupport.model.User;

import java.util.List;

import static net.thequester.websupport.model.QReview.review;

/**
 * Created by Tomo.
 */
public class ReviewProvider {

    private ReviewRepository reviewRepository;

    public ReviewProvider(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void addReview(Review newReview) {

        Review fromDb = reviewRepository.findOne(review.questId.eq(newReview.getQuestId())
                .and(review.userId.eq(newReview.getUserId())));

        if (fromDb != null) {
            return;
        }

        reviewRepository.save(newReview);
    }

    public void editReview(Review newReview) {

        Review fromDb = reviewRepository.findOne(review.questId.eq(newReview.getQuestId())
                .and(review.userId.eq(newReview.getUserId())));

        if (fromDb == null) {
            return;
        }

        fromDb.setLikes(newReview.getLikes());
        fromDb.setText(newReview.getText());

        reviewRepository.save(fromDb);
    }

    public void deleteReview(Review oldReview) {

        Review fromDb = reviewRepository.findOne(review.questId.eq(oldReview.getQuestId())
                .and(review.userId.eq(oldReview.getUserId())));

        reviewRepository.delete(fromDb);
    }

    public List<Review> getReviewsForQuest(QuestDetails quest) {

        return getReviewsForQuest(quest.getId());
    }

    private List<Review> getReviewsForQuest(long questId) {

        return  Lists.newArrayList(reviewRepository.findAll(review.questId.eq(questId)));
    }

    public Iterable<Review> getReviewsFromUser(User user) {

        return reviewRepository.findAll(review.userId.eq(user.getId()));
    }

    public Integer totalQuestLikes(QuestDetails details) {

        Integer count = 0;

        for (Review review : getReviewsForQuest(details)) {
            count += review.getLikes();
        }

        return count;
    }
}

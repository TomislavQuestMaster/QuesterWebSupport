package net.thequester.websupport.database.providers;

import net.thequester.websupport.SystemTestConfiguration;
import net.thequester.websupport.database.repositories.ReviewRepository;
import net.thequester.websupport.model.QuestDetails;
import net.thequester.websupport.model.Review;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Tomo.
 */
@ContextConfiguration(classes = SystemTestConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ReviewProviderTest {

    @Autowired
    private ReviewRepository repository;

    private ReviewProvider provider;

    @Before
    public void setUp() {

        provider = new ReviewProvider(repository);
    }

    @Test
    public void likesAreCorrectlyCalculated() {

        provider.addReview(new Review(1, 1, "bla", 1));
        provider.addReview(new Review(1, 2, "bla", 1));

        QuestDetails questDetails = new QuestDetails();
        questDetails.setId(1L);

        Integer likes = provider.totalQuestLikes(questDetails);
        assertEquals(Integer.valueOf(2),likes);
    }

    @Test
    public void sameUserCannotAddReviewForSameQuest() {

        provider.addReview(new Review(1, 1, "bla", 1));
        provider.addReview(new Review(1, 1, "bla", 1));

        QuestDetails questDetails = new QuestDetails();
        questDetails.setId(1L);

        List<Review> reviews = provider.getReviewsForQuest(questDetails);
        assertEquals(1, reviews.size());
    }

    @Test
    public void reviewIsEdited(){

        provider.addReview(new Review(1,1,"",0));
        provider.editReview(new Review(1,1,"test",1));

        QuestDetails questDetails = new QuestDetails();
        questDetails.setId(1L);

        List<Review> reviews = provider.getReviewsForQuest(questDetails);
        Review review = reviews.get(0);

        assertEquals("test", review.getText());
        assertEquals(Integer.valueOf(1), review.getLikes());
        assertEquals(1, reviews.size());
    }

    @After
    public void cleanUp(){
        repository.deleteAll();
    }

}

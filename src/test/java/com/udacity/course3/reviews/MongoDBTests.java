package com.udacity.course3.reviews;

import com.udacity.course3.reviews.MongoDBRepository.MongoDBReviewRepository;
import com.udacity.course3.reviews.document.MongoDBComment;
import com.udacity.course3.reviews.document.MongoDBReview;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class MongoDBTests {

    @Autowired
    private MongoDBReviewRepository mongoDBReviewRepository;

    @Test
    public void CreateMongoDBReviewTest() {
        MongoDBReview mongoDBReview = new MongoDBReview();
        mongoDBReview.setReviewedProductId(1);
        mongoDBReview.setReviewText("A very nice product");

        List<MongoDBComment> myCommentList = new ArrayList<>();
        myCommentList.add(new MongoDBComment(1001, "I agree"));
        myCommentList.add(new MongoDBComment(1002, "That's not true"));
        mongoDBReview.setCommentList(myCommentList);
        mongoDBReviewRepository.save(mongoDBReview);

        Optional<MongoDBReview> optionalMongoDBReview = mongoDBReviewRepository.findById(mongoDBReview.get_id());

        Assert.assertTrue(optionalMongoDBReview.isPresent());
        assertThat(optionalMongoDBReview.get().getReviewText()).isEqualTo("A very nice product");
        List<MongoDBComment> savedCommentList = optionalMongoDBReview.get().getCommentList();
        assertThat(savedCommentList.get(0).getCommentText()).isEqualTo("I agree");
        assertThat(savedCommentList.get(1).getCommentText()).isEqualTo("That's not true");
    }

    @Test
    public void FindReviewsByProductIdTest() {

        MongoDBReview mongoDBReview = new MongoDBReview();
        mongoDBReview.setReviewedProductId(500);
        mongoDBReview.setReviewText("A super nice product");
        List<MongoDBComment> myCommentList = new ArrayList<>();
        myCommentList.add(new MongoDBComment(1003, "It really is super nice"));
        myCommentList.add(new MongoDBComment(1004, "Well, that's overhyped"));
        mongoDBReview.setCommentList(myCommentList);
        mongoDBReviewRepository.save(mongoDBReview);


        List<MongoDBReview> reviewList = mongoDBReviewRepository.findByReviewedProductId(500);
        Assert.assertEquals(reviewList.size(), 1);
        Assert.assertEquals(reviewList.get(0).getCommentList().size(), 2);
        Assert.assertEquals(reviewList.get(0).getCommentList().get(0).getCommentText(), "It really is super nice");
    }

}

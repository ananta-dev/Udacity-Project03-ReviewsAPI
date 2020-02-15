package com.udacity.course3.reviews.document;

import com.udacity.course3.reviews.entity.Review;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document("reviews")
public class MongoDBReview {

    @Id
    private String _id;
    private Integer reviewId;
    private String reviewText;
    private Integer reviewedProductId;
    private List<MongoDBComment> commentList = new ArrayList<>();

    public MongoDBReview() {
    }

    public MongoDBReview(Review review) {
        this.reviewId = review.getReviewId();
        this.reviewText = review.getReviewText();
        this.reviewedProductId = review.getProduct().getProductId();
        // this.commentList = review.getComments(); THIS DOES NOT WORK - it is a different class that we need
        this.commentList = new ArrayList<>();
    }


    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public Integer getReviewedProductId() {
        return reviewedProductId;
    }

    public void setReviewedProductId(Integer reviewedProductId) {
        this.reviewedProductId = reviewedProductId;
    }

    public List<MongoDBComment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<MongoDBComment> commentList) {
        this.commentList = commentList;
    }


    public void addMongoDBComment(MongoDBComment mongoDBComment) {
        this.commentList.add(mongoDBComment);
    }

}

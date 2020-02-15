package com.udacity.course3.reviews.document;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Document("reviews")
public class MongoDBReview {

    @Id
    private Integer reviewId;
    private String reviewText;
    private Integer reviewedProductId;
    private List<MongoDBComment> commentList = new ArrayList<>();

    public MongoDBReview() {
    }

    public MongoDBReview(Integer reviewId, String reviewText, Integer reviewedProductId, List<MongoDBComment> commentList) {
        this.reviewId = reviewId;
        this.reviewText = reviewText;
        this.reviewedProductId = reviewedProductId;
        this.commentList = commentList;
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
}

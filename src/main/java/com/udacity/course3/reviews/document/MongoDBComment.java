package com.udacity.course3.reviews.document;

public class MongoDBComment {

    private Integer commentId;
    private String commentText;

    public MongoDBComment() {
    }

    public MongoDBComment(Integer commentId, String commentText) {
        this.commentId = commentId;
        this.commentText = commentText;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
}

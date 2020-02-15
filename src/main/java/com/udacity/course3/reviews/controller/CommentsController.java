package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.MongoDBRepository.MongoDBReviewRepository;
import com.udacity.course3.reviews.document.MongoDBComment;
import com.udacity.course3.reviews.document.MongoDBReview;
import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    // TODO: Wire needed JPA repositories here
    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoDBReviewRepository mongoDBReviewRepository;
    
    /**
     * Creates a comment for a review.
     *
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody Comment theComment) {
        // throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
        
        Optional<Review> myOptionalReview = reviewRepository.findById(reviewId);
        if (myOptionalReview.isPresent()) {
            Review theReview = myOptionalReview.get();
            theComment.setReview(theReview);
            commentRepository.save(theComment);

            // Save to MongoDB review
            MongoDBComment theMongoDBComment = new MongoDBComment(theComment.getCommentId(),theComment.getCommentText());
            Optional<MongoDBReview> optionalMongoDBReview = mongoDBReviewRepository.findByReviewId(reviewId);
            if (optionalMongoDBReview.isPresent()) {
                MongoDBReview theMongoDBReview = optionalMongoDBReview.get();
                theMongoDBReview.addMongoDBComment(theMongoDBComment);
                mongoDBReviewRepository.save(theMongoDBReview);
            }
            return ResponseEntity.accepted().body(theMongoDBComment);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * List comments for a review.
     *
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND. >>> Throw Exception???
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public List<Comment> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        // throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
        Optional<Review> myOptionalReview = reviewRepository.findById(reviewId);
        if (myOptionalReview.isPresent()) {
            List<Comment> theComments = myOptionalReview.get().getComments();
            return theComments;
        }
        else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }
}

package com.udacity.course3.reviews.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

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
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @RequestBody Comment oneComment) {
        // throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
        
        Optional<Review> myOptionalReview = reviewRepository.findById(reviewId);
        if (myOptionalReview.isPresent()) {
            Review theReview = myOptionalReview.get();
            oneComment.setReview(theReview);
            commentRepository.save(oneComment);
            
            return new ResponseEntity<>("Comment added to Review.", HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public List<?> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        // throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);
        Optional<Review> myOptionalReview = reviewRepository.findById(reviewId);
        if (myOptionalReview.isPresent()) {
            List<Comment> theComments = optionalReview.get().getComments();
            return theComments;
        }
        else {
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }
    }
}

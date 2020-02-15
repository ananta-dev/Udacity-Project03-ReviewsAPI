package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.MongoDBRepository.MongoDBReviewRepository;
import com.udacity.course3.reviews.document.MongoDBReview;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    // TODO: Wire JPA repositories here

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoDBReviewRepository mongoDBReviewRepository;

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> createReviewForProduct(@PathVariable("productId") Integer productId, @RequestBody Review theReview) {
        // throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);

        Optional<Product> myOptionalProduct = productRepository.findById(productId);

        if (myOptionalProduct.isPresent()) {
            Product theProduct = myOptionalProduct.get();
            theReview.setProduct(theProduct);
            reviewRepository.save(theReview);

            MongoDBReview mongoDBReview = new MongoDBReview(theReview);
            mongoDBReviewRepository.save(mongoDBReview);

            return new ResponseEntity<>(theReview, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        // throw new HttpServerErrorException(HttpStatus.NOT_IMPLEMENTED);

        Optional<Product> myOptionalProduct = productRepository.findById(productId);

        List<MongoDBReview> theMongoReviewList = new ArrayList<>();
        if (myOptionalProduct.isPresent()) {
            List<Review> theMySQLReviewList = reviewRepository.findReviewsByProduct(myOptionalProduct.get());
            for (Review thisMySQLReview : theMySQLReviewList) {

                /**
                Optional<MongoDBReview> optionalMongoDBReview = mongoDBReviewRepository.findByReviewId(thisMySQLReview.getReviewId());
                if (optionalMongoDBReview.isPresent()) {
                    theMongoReviewList.add(optionalMongoDBReview.get());
                } */

                // the MongoDBReview should always be present if the mySQL review is present, so no need to check isPresent()
                theMongoReviewList.add(mongoDBReviewRepository.findByReviewId(thisMySQLReview.getReviewId()).get());
            }
            return ResponseEntity.accepted().body(theMongoReviewList);
        }
        else
            return ResponseEntity.notFound().build();
    }
}

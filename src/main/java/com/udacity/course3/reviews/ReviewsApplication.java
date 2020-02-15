package com.udacity.course3.reviews;

import com.udacity.course3.reviews.MongoDBRepository.MongoDBReviewRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackageClasses = MongoDBReviewRepository.class)
// @EnableJpaRepositories(basePackageClasses = {ProductRepository.class,
// 											 ReviewRepository.class,
// 											 CommentRepository.class})
public class ReviewsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsApplication.class, args);
	}

}
package com.udacity.course3.reviews;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.transaction.BeforeTransaction;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ReviewsApplicationTests {

	@Autowired
	private DataSource dataSource;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private CommentRepository commentRepository;

	@Test
	public void contextLoads() {
	}

	@BeforeTransaction
	public void setup() {
		Product product = new Product("White Audi A8", "A cool car.");
		productRepository.save(product);

		Review review = new Review("A Renault Clio LIMITED 1.4 is a better buy.");
		review.setProduct(product);
		reviewRepository.save(review);

		Comment comment = new Comment("I totally agree!");
		comment.setReview(review);
		commentRepository.save(comment);

		comment = new Comment("Me too!");
		comment.setReview(review);
		commentRepository.save(comment);

		comment = new Comment("Well. but it should be red.");
		comment.setReview(review);
		commentRepository.save(comment);

		review = new Review("I enjoyed test driving it, but it's too expensive.");
		review.setProduct(product);
		reviewRepository.save(review);

		review = new Review("I enjoyed test driving it, but it's too expensive.");
		review.setProduct(product);
		reviewRepository.save(review);

		review = new Review("I do not like this car. Too noisy");
		review.setProduct(product);
		reviewRepository.save(review);

		product = new Product("Renault Clio", "A much cooler car.");
		productRepository.save(product);
	}


	@Test
	public void injectedComponentsAreNotNull(){
		assertThat(dataSource).isNotNull();
		assertThat(entityManager).isNotNull();
		assertThat(productRepository).isNotNull();
		assertThat(reviewRepository).isNotNull();
		assertThat(commentRepository).isNotNull();
	}

	@Test
	public void testFindByProductId() {
		Product product = new Product();

		product.setProductName("Computer Monitor");
		product.setProductDesc("A very large screen on a stand");

		entityManager.persist(product);
		entityManager.flush();
		Integer theProductId = product.getProductId();

		Optional<Product> optionalProduct = productRepository.findById(theProductId);

		assertThat(product).isEqualTo(optionalProduct.get());
	}

	@Test
	public void testListComments() {
		Review review = reviewRepository.findById(1).get();
		List<Comment> commentList = (List<Comment>)commentRepository.findAllByReview(review);
		assertThat(commentList.size()).isEqualTo(3);
	}

	@Test
	public void testListReviews() {
		Product product = productRepository.findById(1).get();
		List<Review> reviewList = (List<Review>)reviewRepository.findReviewsByProduct(product);
		assertThat(reviewList.size()).isEqualTo(4);
	}

	@Test
	public void testListProducts() {
		List<Product> productList = (List<Product>)productRepository.findAll();
		assertThat(productList.size()).isGreaterThan(1);
	}

	@Test
	public void testFindProductById() {
		// test for the existence of the product created during setup()
		Optional<Product> optionalProduct = productRepository.findById(1);
		assertThat(optionalProduct.get().getProductDesc()).isEqualTo("A cool car.");
	}

	@Test
	public void testCreateProduct() {
		Product product = new Product("Tesla 1", "Sports Electrical Car.");
		productRepository.save(product);
		Integer productId = product.getProductId();
		Product savedProduct = productRepository.findById(productId).get();
		assertThat(savedProduct).isEqualTo(product);
	}

	@Test
	public void testCreateReview(){
		Product firstProduct = productRepository.findById(1).get();
		Review review = new Review("I would never buy this car");
		review.setProduct(firstProduct);
		reviewRepository.save(review);
		Integer reviewId = review.getReviewId();
		Review savedReview = reviewRepository.findById(reviewId).get();
		assertThat(savedReview).isEqualTo(review);
	}

	@Test
	public void testCreateComment(){
		Review firstReview = reviewRepository.findById(1).get();
		Comment comment = new Comment("I do not agree nor disagree.");
		comment.setReview(firstReview);
		commentRepository.save(comment);
		Integer commentId = comment.getCommentId();
		Comment savedComment = commentRepository.findById(commentId).get();
		assertThat(savedComment).isEqualTo(comment);
	}
}
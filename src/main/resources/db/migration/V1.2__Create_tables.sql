CREATE TABLE products (
    product_id INT AUTO_INCREMENT,
    product_title VARCHAR(200) NOT NULL,
    product_desc VARCHAR(1000),
    PRIMARY KEY (product_id)
);

CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT,
    review_text VARCHAR(1000) NOT NULL,
    product_id INT NOT NULL,
    CONSTRAINT prod_review_fk FOREIGN KEY (product_id) REFERENCES products (product_id),
    PRIMARY KEY (review_id)
);

CREATE TABLE comments (
    comment_id INT AUTO_INCREMENT,
    comment_text VARCHAR(1000) NOT NULL,
    review_id INT NOT NULL,
    CONSTRAINT review_comment_fk FOREIGN KEY (review_id) REFERENCES reviews (review_id),
    PRIMARY KEY (comment_id)
);
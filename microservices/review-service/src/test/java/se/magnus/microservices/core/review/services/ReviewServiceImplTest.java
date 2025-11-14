package se.magnus.microservices.core.review.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static reactor.core.publisher.Mono.just;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import se.magnus.api.core.review.Review;
import se.magnus.microservices.core.review.persistence.DBTestBase;
import se.magnus.microservices.core.review.persistence.ReviewRepository;

//@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ReviewServiceImplTest {

    @Autowired
    private ReviewRepository repository;

    // @BeforeEach
    void setupDb() {
        repository.deleteAll();
    }

}
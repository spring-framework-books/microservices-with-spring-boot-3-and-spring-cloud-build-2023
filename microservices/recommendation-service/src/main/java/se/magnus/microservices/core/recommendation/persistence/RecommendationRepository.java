package se.magnus.microservices.core.recommendation.persistence;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;

public interface RecommendationRepository extends MongoRepository<RecommendationEntity, String> {
  List<RecommendationEntity> findByProductId(int productId);
}

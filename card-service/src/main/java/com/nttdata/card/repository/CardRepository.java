package com.nttdata.card.repository;

import com.nttdata.card.model.DebitCard;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Card Repository.
 */
@Repository
public interface CardRepository extends ReactiveMongoRepository<DebitCard, String> {
}

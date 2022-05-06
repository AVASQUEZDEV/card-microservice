package com.nttdata.card.repository;

import com.nttdata.card.model.CardType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface defines the repository for cards
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Repository
public interface ICardTypeRepository extends ReactiveMongoRepository<CardType, String> {
}

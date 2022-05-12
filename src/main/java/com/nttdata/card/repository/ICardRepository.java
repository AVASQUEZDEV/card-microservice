package com.nttdata.card.repository;

import com.nttdata.card.model.Card;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * This interface defines the repository for bank account charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Repository
public interface ICardRepository extends ReactiveMongoRepository<Card, String> {

    Mono<Card> findByCci(String name);

}

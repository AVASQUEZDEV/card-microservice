package com.nttdata.card.service;

import com.nttdata.card.dto.request.CardTypeRequest;
import com.nttdata.card.model.CardType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This interface defines the service of cards
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
public interface ICardTypeService {

    Flux<CardType> findAll();

    Mono<CardType> findById(String id);

    Mono<CardType> create(CardTypeRequest request);

    Mono<CardType> update(String id, CardTypeRequest request);

    Mono<Void> deleteById(String id);

}

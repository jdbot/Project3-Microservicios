package com.nttdata.card.service;

import com.nttdata.card.model.DebitCard;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Card service interface.
 */
public interface CardService {

    public Flux<DebitCard> findAll();

    public Mono<DebitCard> register(DebitCard debitCard);

    public Mono<DebitCard> update(DebitCard debitCard);

    public Mono<Void> delete(String id);

    public Mono<DebitCard> findById(String id);
}

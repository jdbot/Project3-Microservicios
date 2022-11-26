package com.nttdata.card.service;

import com.nttdata.card.model.BankAccount;
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

    public Mono<DebitCard> associatePrimaryAccount(String idAccount);

    public Mono<Float> getPrimaryAccountAmount(String debitCardId);

    public Flux<BankAccount> payWithDebitCard(String debitCardId, Float amountToPay);
}

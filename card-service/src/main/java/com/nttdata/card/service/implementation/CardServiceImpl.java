package com.nttdata.card.service.implementation;

import com.nttdata.card.model.BankAccount;
import com.nttdata.card.model.DebitCard;
import com.nttdata.card.repository.CardRepository;
import com.nttdata.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Card service implementation.
 */
@Service
public class CardServiceImpl implements CardService {

    @Autowired
    private WebClient.Builder webClient;

    @Autowired
    CardRepository cardRepository;

    @Override
    public Flux<DebitCard> findAll() {
        return cardRepository.findAll();
    }

    @Override
    public Mono<DebitCard> register(DebitCard debitCard) {
        return cardRepository.save(debitCard);
    }

    @Override
    public Mono<DebitCard> update(DebitCard debitCard) {
        return cardRepository.save(debitCard);
    }

    @Override
    public Mono<Void> delete(String id) {
        return cardRepository.deleteById(id);
    }

    @Override
    public Mono<DebitCard> findById(String id) {
        return cardRepository.findById(id);
    }

    @Override
    public Mono<DebitCard> associatePrimaryAccount(String idAccount) {
        return this.webClient.build().get().uri("/bankAccount/{bankAccountId}", idAccount).retrieve().bodyToMono(BankAccount.class)
                .flatMap( x -> this.findById(x.getDebitCardId()))
                .filter(debitcard -> debitcard.getPrimaryAccountId()==null)
                .flatMap(x -> {
                    x.setPrimaryAccountId(idAccount);
                    this.webClient.build().put().uri("/bankAccount/primaryAccount/{bankAccountId}", idAccount).retrieve().bodyToMono(BankAccount.class).subscribe();
                    return update(x);
                });
    }

    @Override
    public Mono<Float> getPrimaryAccountAmount(String debitCardId) {
        return findById(debitCardId).flatMap(card -> {
            return this.webClient.build().get().uri("/bankAccount/{bankAccountId}", card.getPrimaryAccountId()).retrieve().bodyToMono(BankAccount.class);
        }).map(BankAccount::getAmount);
    }
}

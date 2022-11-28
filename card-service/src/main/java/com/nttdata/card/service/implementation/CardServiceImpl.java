package com.nttdata.card.service.implementation;

import com.google.common.util.concurrent.AtomicDouble;
import com.nttdata.card.model.BankAccount;
import com.nttdata.card.model.DebitCard;
import com.nttdata.card.model.Transaction;
import com.nttdata.card.repository.CardRepository;
import com.nttdata.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Override
    public Flux<BankAccount> payWithDebitCard(String debitCardId, Float amountToPay) {
        AtomicDouble sum = new AtomicDouble();
        AtomicDouble sum2 = new AtomicDouble(amountToPay);
        Flux<BankAccount> accounts = this.webClient.build().get().uri("/bankAccount/findAccounts/{debitCardId}",debitCardId).retrieve().bodyToFlux(BankAccount.class)
                .filter(account -> account.getAmount() > 0)
                .sort(Comparator.comparing(BankAccount::isPrimaryAccount).reversed().thenComparing(x -> LocalDateTime.parse(x.getAssociationDate())))
                .takeUntil(x -> sum.addAndGet(x.getAmount()) >= amountToPay)
                .flatMapSequential(account -> {
                    float transactionAmount = (account.getAmount() - (float)sum2.get()) <= 0 ? account.getAmount() :  (float)sum2.get();
                    float newAmount = account.getAmount() - transactionAmount;
                    account.setAmount(newAmount);
                    sum2.getAndAdd(transactionAmount*-1);
                    Transaction t = new Transaction(null, LocalDate.now().toString(), transactionAmount, "card payment" , account.getCustomerId(), account.getId(), account.getAmount(), debitCardId);
                    return this.webClient.build().post().uri("/transaction/")
                            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                            .body(Mono.just(t), Transaction.class)
                            .retrieve()
                            .bodyToFlux(Transaction.class)
                            .flatMap(x -> this.webClient.build().put().uri("/bankAccount/update")
                                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                                    .body(Mono.just(account), BankAccount.class)
                                    .retrieve()
                                    .bodyToFlux(BankAccount.class)
                                    .next());
                });

        return accounts;
    }


}

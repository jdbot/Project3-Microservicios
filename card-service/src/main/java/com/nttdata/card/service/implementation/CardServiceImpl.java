package com.nttdata.card.service.implementation;

import com.nttdata.card.model.DebitCard;
import com.nttdata.card.repository.CardRepository;
import com.nttdata.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Card service implementation.
 */
@Service
public class CardServiceImpl implements CardService {

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
}

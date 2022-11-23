package com.nttdata.card.controller;

import com.nttdata.card.model.DebitCard;
import com.nttdata.card.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller of Card.
 */
@RestController
@RequestMapping("/card")
public class CardController {

    @Autowired
    private CardService cardService;

    //Method to get all the DebitCards
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<DebitCard> findAll() {
        return cardService.findAll();
    }

    //Method to insert a new DebitCard
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<DebitCard> register(@RequestBody DebitCard debitCard) {
        return  cardService.register(debitCard);
    }

    //Method to update a DebitCard
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Mono<DebitCard> modify(@RequestBody DebitCard debitCard) {
        return  cardService.update(debitCard);
    }

    //Method to get a DebitCard by ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<DebitCard> findById(@PathVariable("id") String id) {
        return cardService.findById(id);
    }

    //Method to delete a DebitCard
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> delete(@PathVariable("id") String id) {
        return cardService.delete(id);
    }
}
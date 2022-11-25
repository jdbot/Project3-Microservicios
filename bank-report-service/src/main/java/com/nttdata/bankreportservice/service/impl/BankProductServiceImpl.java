package com.nttdata.bankreportservice.service.impl;

import com.nttdata.bankreportservice.dto.*;
import com.nttdata.bankreportservice.service.BankProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Bank Product Service Implementation.
 */
@Service
public class BankProductServiceImpl implements BankProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankProductServiceImpl.class);


    @Autowired
    private WebClient.Builder webClient;

    @Override
    public Mono<ClientDTO> getClient(String customerId) {
        return this.webClient.build().get().uri("/client/{id}", customerId)
                .retrieve().bodyToMono(ClientDTO.class);
    }

    @Override
    public Flux<BankAccountSummaryDTO> getBankAccountByCustomer(String customerId) {
        return this.webClient.build().get().uri("/bankAccount/accountByCustomerId/{id}", customerId)
        .retrieve().bodyToFlux(BankAccountSummaryDTO.class);
    }

    @Override
    public Flux<BankCreditSummaryDTO> getBankCreditByCustomer(String customerId) {
        return this.webClient.build().get().uri("/bankCredit/creditByCustomerId/{id}", customerId)
                .retrieve().bodyToFlux(BankCreditSummaryDTO.class);
    }

    @Override
    public Mono<BankProductSummaryDTO> getBankProductByCustomer(String customerId) {
        Mono<ClientDTO> dataClient = getClient(customerId);
        Mono<List<BankAccountSummaryDTO>> account = getBankAccountByCustomer(customerId).collectList();
        Mono<List<BankCreditSummaryDTO>> credit = getBankCreditByCustomer(customerId).collectList();
        BankProductSummaryDTO product = new BankProductSummaryDTO();

        return dataClient.flatMap( c-> {
            product.setClientName(c.getName());
            Mono.just(product);
            return account.flatMap( a-> {
                product.setBankAccount(a);
                return credit.flatMap( d-> {
                    product.setBankCredit(d);
                    return Mono.just(product);
                }).switchIfEmpty(Mono.just(product));
            }).switchIfEmpty(Mono.just(product));
        }).switchIfEmpty(Mono.just(product));

    }

    @Override
    public Flux<BankAccountDTO> getBankAccount(String startDate, String endDate) {
        return this.webClient.build().get().uri("/bankAccount")
                .retrieve().bodyToFlux(BankAccountDTO.class);
    }

    @Override
    public Flux<BankCreditDTO> getBankCredit(String startDate, String endDate) {
        return this.webClient.build().get().uri("/bankCredit")
                .retrieve().bodyToFlux(BankCreditDTO.class);
    }




}

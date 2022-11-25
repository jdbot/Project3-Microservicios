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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Bank Product Service Implementation.
 */
@Service
public class BankProductServiceImpl implements BankProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BankProductServiceImpl.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Autowired
    private WebClient.Builder webClient;

    @Override
    public Mono<ClientDto> getClient(String customerId) {
        return this.webClient.build().get().uri("/client/{id}", customerId)
                .retrieve().bodyToMono(ClientDto.class);
    }

    @Override
    public Flux<BankAccountSummaryDto> getBankAccountByCustomer(String customerId) {
        return this.webClient.build().get().uri("/bankAccount/accountByCustomerId/{id}", customerId)
        .retrieve().bodyToFlux(BankAccountSummaryDto.class);
    }

    @Override
    public Flux<BankCreditSummaryDto> getBankCreditByCustomer(String customerId) {
        return this.webClient.build().get().uri("/bankCredit/creditByCustomerId/{id}", customerId)
                .retrieve().bodyToFlux(BankCreditSummaryDto.class);
    }

    @Override
    public Mono<BankProductSummaryDto> getBankProductByCustomer(String customerId) {
        Mono<ClientDto> dataClient = getClient(customerId);
        Mono<List<BankAccountSummaryDto>> account = getBankAccountByCustomer(customerId).collectList();
        Mono<List<BankCreditSummaryDto>> credit = getBankCreditByCustomer(customerId).collectList();
        BankProductSummaryDto product = new BankProductSummaryDto();

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
    public Flux<BankAccountDto> getBankAccount(String startDate, String endDate) {
        return this.webClient.build().get().uri("/bankAccount")
                .retrieve().bodyToFlux(BankAccountDto.class)
                .filter(x-> LocalDate.from(LocalDate.parse(x.getCreationDate(),formatter))
                        .compareTo(LocalDate.from(LocalDate.parse(startDate,formatter))) > 0 &&
                        LocalDate.from(LocalDate.parse(x.getCreationDate(),formatter))
                                .compareTo(LocalDate.from(LocalDate.parse(endDate,formatter))) < 0);
    }

    @Override
    public Flux<BankCreditDto> getBankCredit(String startDate, String endDate) {
        return this.webClient.build().get().uri("/bankCredit")
                .retrieve().bodyToFlux(BankCreditDto.class)
                .filter(x-> LocalDate.from(LocalDate.parse(x.getCreationDate(),formatter))
                        .compareTo(LocalDate.from(LocalDate.parse(startDate,formatter))) > 0 &&
                        LocalDate.from(LocalDate.parse(x.getCreationDate(),formatter))
                                .compareTo(LocalDate.from(LocalDate.parse(endDate,formatter))) < 0);
    }

}

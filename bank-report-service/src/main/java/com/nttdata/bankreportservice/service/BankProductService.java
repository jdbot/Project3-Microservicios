package com.nttdata.bankreportservice.service;

import com.nttdata.bankreportservice.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Bank Product Service Interface.
 */
public interface BankProductService {

    Mono<ClientDTO> getClient(String customerId);

    Flux<BankAccountSummaryDTO> getBankAccountByCustomer(String customerId);

    Flux<BankCreditSummaryDTO> getBankCreditByCustomer(String customerId);

    Mono<BankProductSummaryDTO> getBankProductByCustomer(String customerId);

    Flux<BankAccountDTO> getBankAccount(String startDate, String endDate);

    Flux<BankCreditDTO> getBankCredit(String startDate, String endDate);

}

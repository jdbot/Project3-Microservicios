package com.nttdata.bankreportservice.service;

import com.nttdata.bankreportservice.dto.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Bank Product Service Interface.
 */
public interface BankProductService {

    Mono<ClientDto> getClient(String customerId);

    Flux<BankAccountSummaryDto> getBankAccountByCustomer(String customerId);

    Flux<BankCreditSummaryDto> getBankCreditByCustomer(String customerId);

    Mono<BankProductSummaryDto> getBankProductByCustomer(String customerId);

    Flux<BankAccountDto> getBankAccount(String startDate, String endDate);

    Flux<BankCreditDto> getBankCredit(String startDate, String endDate);

}

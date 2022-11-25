package com.nttdata.bankreportservice.controller;

import com.nttdata.bankreportservice.dto.BankProductSummaryDTO;
import com.nttdata.bankreportservice.service.BankProductService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Controller of Bank Product.
 */
@RestController
@RequestMapping("/bankProduct")
public class BankProductController {

    @Autowired
    private BankProductService bankProductService;

    //Method to get a bank product by clientId
    @GetMapping("/byClient/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BankProductSummaryDTO> getBankProductByCustomer(@PathVariable("id") String customerId) {
        return bankProductService.getBankProductByCustomer(customerId);
    }

    @GetMapping("/byDate/{product}/{startDate}/{endDate}")
    @ResponseStatus(HttpStatus.OK)
    public Flux<?> getProductByDate(@PathVariable("product") @NotNull String product,
                                    @PathVariable("startDate") String startDate,
                                    @PathVariable("endDate") String endDate) {
        if (product.equals("account")) {
            return bankProductService.getBankAccount(startDate,endDate);
        } else {
            return bankProductService.getBankCredit(startDate,endDate);
        }

    }
}

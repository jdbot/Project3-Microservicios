package com.nttdata.bankreportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankCreditSummaryDTO {

    //number credit of the bank credit
    private String numberCredit;
    //amount of the bank credit
    private Float amount;
    //credit
    private Float credit;

}

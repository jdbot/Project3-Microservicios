package com.nttdata.bankreportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class BankProductSummaryDto {

    //Full name of the client
    private String clientName;
    //data of the bank account
    private List<BankAccountSummaryDto> bankAccount;
    //data of the bank credit
    private List<BankCreditSummaryDto> BankCredit;
}

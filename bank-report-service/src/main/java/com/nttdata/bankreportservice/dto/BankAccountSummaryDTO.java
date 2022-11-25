package com.nttdata.bankreportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class BankAccountSummaryDTO {

        //number account of the bank account
        private String numberAccount;
        //amount of the bank account
        private Float amount;

}

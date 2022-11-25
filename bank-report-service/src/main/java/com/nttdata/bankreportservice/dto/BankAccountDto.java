package com.nttdata.bankreportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
public class BankAccountDto {

        private String id;
        //number account of the bank account
        private String numberAccount;
        //amount of the bank account
        private Float amount;
        //end date of the bank account
        private String endDate;
        //id of the client
        private String customerId;
        //id of the bank type
        private String type;
        //creation date of the bank account
        private String creationDate;

}

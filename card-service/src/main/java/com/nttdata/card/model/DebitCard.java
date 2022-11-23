package com.nttdata.card.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * DebitCard document.
 */
@Getter
@AllArgsConstructor
@Setter
@NoArgsConstructor
@Document(collection = "transactions")
public class DebitCard {
    @Id
    public String id;

    private String cardType;
    private String cardNumber;
    private String creationDate;
    private String expirationDate;
    private String securityCode;
    private String primaryAccountId;
    private String clientId;
}

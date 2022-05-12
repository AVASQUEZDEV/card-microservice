package com.nttdata.card.dto.response.proxy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * This class defines the response of bank account
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankAccountResponse {

    @JsonProperty(value = "id")
    private String id;

    @JsonProperty(value = "clientId")
    private String clientId;

    @JsonProperty(value = "productId")
    private String productId;

    @JsonProperty(value = "cardId")
    private String cardId;

    @JsonProperty(value = "balance")
    private Float balance;

    @JsonProperty(value = "createdAt")
    private Date createdAt;

    @JsonProperty(value = "updatedAt")
    private Date updatedAt;

}
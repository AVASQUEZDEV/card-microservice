package com.nttdata.card.dto.request;

import lombok.Data;

import java.util.Date;

/**
 * This class defines the request of bank account charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Data
public class CardRequest {

    private String bankAccountId;

    private String cardNumber;

    private Long securityCode;

    private Date expirationDate;

    private String cci;

    private Float balance;

    private String bankName;

}

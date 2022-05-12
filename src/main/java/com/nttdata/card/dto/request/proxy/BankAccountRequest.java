package com.nttdata.card.dto.request.proxy;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * This class defines the request of bank account
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class BankAccountRequest {

    private String cardId;

}

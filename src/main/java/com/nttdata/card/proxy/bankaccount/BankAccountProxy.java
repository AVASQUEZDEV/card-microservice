package com.nttdata.card.proxy.bankaccount;

import com.nttdata.card.dto.request.proxy.BankAccountRequest;
import com.nttdata.card.dto.response.proxy.BankAccountResponse;
import com.nttdata.card.exceptions.CustomException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * This class get queries external
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class BankAccountProxy {

    private final static Logger LOGGER = LoggerFactory.getLogger(BankAccountProxy.class);

    @Value("${api-gateway.routes.ms-bank-account.account}")
    private String accountURL;

    private final WebClient webClient;

    public Mono<BankAccountResponse> getBankAccountById(String id) {
        LOGGER.info("[REQUEST][URL][getBankAccountById]:" + accountURL + "/" + id);
        return webClient.get()
                .uri(accountURL + "/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(BankAccountResponse.class)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][getBankAccountById]" + e);
                    return Mono.error(CustomException.badRequest("The request to proxy bank account is invalid"));
                });
    }

    public Mono<BankAccountResponse> bankAccountUpdate(String id, BankAccountRequest request) {
        LOGGER.info("[REQUEST][URL][bankAccountUpdate]" + accountURL + "/" + id);
        LOGGER.info("[REQUEST][BODY][bankAccountUpdate]" + request.toString());
        return webClient.put()
                .uri(accountURL + "/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), BankAccountRequest.class)
                .retrieve()
                .bodyToMono(BankAccountResponse.class)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][bankAccountUpdate]" + e);
                    return Mono.error(CustomException.badRequest("The request to proxy bank account is invalid"));
                });
    }

}

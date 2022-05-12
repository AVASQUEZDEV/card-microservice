package com.nttdata.card.service.impl;

import com.nttdata.card.dto.mapper.CardMapper;
import com.nttdata.card.dto.request.CardRequest;
import com.nttdata.card.dto.request.proxy.BankAccountRequest;
import com.nttdata.card.exceptions.CustomException;
import com.nttdata.card.model.Card;
import com.nttdata.card.proxy.bankaccount.BankAccountProxy;
import com.nttdata.card.repository.ICardRepository;
import com.nttdata.card.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * This class defines the service of bank accounts charges
 *
 * @author Alcibar Vasquez
 * @version 1.0
 */
@RequiredArgsConstructor
@Service
public class CardServiceImpl implements ICardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CardServiceImpl.class);

    private final ICardRepository cardRepository;

    private final CardMapper cardMapper;

    private final BankAccountProxy bankAccountProxy;

    /**
     * This method returns a list of bank accounts charges
     *
     * @return ank account charges list
     */
    @Override
    public Flux<Card> findAll() {
        return cardRepository.findAll().onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][findAll]" + e);
            return Mono.error(CustomException.internalServerError("Internal Server Error:" + e));
        });
    }

    /**
     * This method return one bank account charge
     *
     * @param id request
     * @return bank account charge
     */
    @Override
    public Mono<Card> findById(String id) {
        return cardRepository.findById(id)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findById]" + e.getMessage());
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account charge not found")));
    }

    /**
     * @param cci request
     * @return card
     */
    @Override
    public Mono<Card> findByCci(String cci) {
        return cardRepository.findByCci(cci)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][findById]" + e.getMessage());
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account charge not found")));
    }

    /**
     * This method creates a bank account charges
     *
     * @param request request to create new bank account charges
     * @return bank account charges created
     */
    @Override
    public Mono<Card> create(CardRequest request) {
        return cardMapper.toPostModel(request)
                .flatMap(cardRepository::save)
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][create]" + e);
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Bank account charge not created")));
    }

    /**
     * This method updates a bank account charges
     *
     * @param id      bank account charge id to update
     * @param request request to update bank account charge
     * @return bank account charge updated
     */
    @Override
    public Mono<Card> update(String id, CardRequest request) {
        return findById(id)
                .flatMap(c -> cardMapper.toPutModel(c, request)
                        .flatMap(req -> {
                            if (checkIfExistField(request, "bankAccountId")) {
                                return bankAccountProxy.getBankAccountById(request.getBankAccountId())
                                        .flatMap(ba -> bankAccountProxy.bankAccountUpdate(ba.getId(),
                                                        new BankAccountRequest(c.getId()))
                                                .flatMap(res -> cardRepository.save(req))
                                        );
                            }
                            System.out.println("test:" + checkIfExistField(request, "bankAccountId"));
                            return cardRepository.save(req);
                        }))
                .onErrorResume(e -> {
                    LOGGER.error("[" + getClass().getName() + "][update]" + e);
                    return Mono.error(CustomException.badRequest("The request is invalid:" + e));
                }).switchIfEmpty(Mono.error(CustomException.notFound("Card not found"))
                );
    }

    /**
     * This method delete a bank account charge
     *
     * @param id bank account charge id to delete
     * @return void
     */
    @Override
    public Mono<Void> deleteById(String id) {
        return cardRepository.deleteById(id).onErrorResume(e -> {
            LOGGER.error("[" + getClass().getName() + "][delete]" + e);
            return Mono.error(CustomException.badRequest("The request is invalid:" + e));
        });
    }

    private boolean checkIfExistField(CardRequest request, String fieldName) {
        try {
            Field f = request.getClass().getField(fieldName);
            return true;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

}

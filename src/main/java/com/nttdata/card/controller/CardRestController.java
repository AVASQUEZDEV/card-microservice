package com.nttdata.card.controller;

import com.nttdata.card.dto.mapper.CardMapper;
import com.nttdata.card.dto.request.CardRequest;
import com.nttdata.card.dto.response.CardResponse;
import com.nttdata.card.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This controller class defines the endpoints to cards
 *
 * @author Alcibar Vasquesz
 * @version 1.0
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cards")
public class CardRestController {

    private final ICardService cardService;

    private final CardMapper cardMapper;

    /**
     * @return list of cards
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<CardResponse> getAll() {
        return cardMapper.toFluxResponse(cardService.findAll());
    }

    /**
     * @return card
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CardResponse> getById(@PathVariable(name = "id") String id) {
        return cardMapper.toMonoResponse(cardService.findById(id));
    }

    /**
     * @return card
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/cci/{cci}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CardResponse> getByCci(@PathVariable(name = "cci") String cci) {
        return cardMapper.toMonoResponse(cardService.findByCci(cci));
    }

    /**
     * @param request request to create card
     * @return card created
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CardResponse> create(@RequestBody CardRequest request) {
        return cardMapper.toMonoResponse(cardService.create(request));
    }

    /**
     * @param id      card id to update
     * @param request request for update card
     * @return card updated
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<CardResponse> update(@PathVariable(name = "id") String id,
                                     @RequestBody CardRequest request) {
        return cardMapper.toMonoResponse(cardService.update(id, request));
    }

    /**
     * @param id card id to delete
     * @return void
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable(name = "id") String id) {
        return cardService.deleteById(id);
    }

}

package com.pokemon.pokemoncatchalljava.pokemon.adapter.command.controller;

import com.pokemon.pokemoncatchalljava.pokemon.application.ports.in.GetRandomPokemonInputPort;
import com.pokemon.pokemoncatchalljava.utils.ResponseBuilder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public final class PokemonController {

    private final GetRandomPokemonInputPort getRandomPokemon;
    private final ResponseBuilder responseBuilder;

    @GetMapping("/v1/pokemon")
    public ResponseEntity getRandomPokemon() {
        return responseBuilder.buildResponseFrom(getRandomPokemon::execute);
    }


}

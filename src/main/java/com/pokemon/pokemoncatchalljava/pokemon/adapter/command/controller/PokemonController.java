package com.pokemon.pokemoncatchalljava.pokemon.adapter.command.controller;

import com.pokemon.pokemoncatchalljava.pokemon.application.ports.in.GetRandomPokemonInputPort;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class PokemonController {

    private final GetRandomPokemonInputPort getRandomPokemon;

    @GetMapping("/v1/random-pokemon")
    public ResponseEntity getRandomPokemon() {

        return getRandomPokemon.execute()
                .map(
                        (domain) -> new ResponseEntity(domain, HttpStatusCode.valueOf(HttpStatus.OK.value()))
                ).getOrElseGet(
                        (e) -> new ResponseEntity(e.getCause().toString(), HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()))
                );
    }

}

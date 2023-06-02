package com.pokemon.pokemoncatchalljava.pokemon.adapter.repository.rest;

import com.pokemon.pokemoncatchalljava.pokemon.adapter.repository.rest.model.PokemonResponse;
import com.pokemon.pokemoncatchalljava.pokemon.application.ports.out.PokemonOutputPort;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.PokemonNotFound;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository("pokeApi")
@AllArgsConstructor
@Slf4j
public class PokeApi implements PokemonOutputPort {

    private final RestTemplate pokeApi;

    @Override
    public Either<Exception, PokemonDomain> getPokemon(int id) {

        return Try.of(() -> pokeApi.getForObject("pokemon/" + id, PokemonResponse.class))
                .toEither()
                .bimap(
                        (e) -> {
                            log.error("getPokemon error getting pokemon with id: {}", id);
                            return new PokemonNotFound(id, e);
                        },
                        PokemonResponse::toDomain
                );
    }
}

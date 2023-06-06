package com.pokemon.pokemoncatchalljava.pokemon.adapter.repository.rest;

import com.pokemon.pokemoncatchalljava.config.http.RestClient;
import com.pokemon.pokemoncatchalljava.pokemon.adapter.repository.rest.model.PokemonResponse;
import com.pokemon.pokemoncatchalljava.pokemon.application.ports.out.PokemonOutputPort;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.ApplicationError;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.CommunicationError;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.PokemonNotFound;
import io.vavr.control.Either;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpStatusCodeException;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Repository("pokeApi")
@AllArgsConstructor
@Slf4j
public class PokeApi implements PokemonOutputPort {

    private final RestClient pokeApi;

    @Override
    public Either<ApplicationError, PokemonDomain> getPokemon(int id) {

        return pokeApi.get("pokemon/" + id, PokemonResponse.class)
                .toEither()
                .bimap(
                        (e) -> toError(id, e),
                        PokemonResponse::toDomain
                );
    }

    private ApplicationError toError(int id, Throwable e) {
        return Match(((HttpStatusCodeException) e).getStatusCode()).of(
                Case(
                        $(instanceOf(HttpStatus.NOT_FOUND.getClass())),
                        o -> new PokemonNotFound(id, e)
                ),
                Case($(), o -> new CommunicationError("POKE_API", e))
        );
    }
}

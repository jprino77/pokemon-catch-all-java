package com.pokemon.pokemoncatchalljava.pokemon.application.usecase;

import com.pokemon.pokemoncatchalljava.config.annotation.UseCase;
import com.pokemon.pokemoncatchalljava.pokemon.application.ports.in.GetRandomPokemonInputPort;
import com.pokemon.pokemoncatchalljava.pokemon.application.ports.out.PokemonOutputPort;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.ApplicationError;
import com.pokemon.pokemoncatchalljava.utils.RandomComponent;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@UseCase
@Slf4j
@AllArgsConstructor
public final class GetRandomPokemon implements GetRandomPokemonInputPort {

    private final RandomComponent random;
    private final PokemonOutputPort pokemonOutputPort;

    @Override
    public Either<ApplicationError, PokemonDomain> execute() {

        return Try.of(random::randomizeInt)
                .andThen((id) -> log.info("searching by id {}", id))
                .map(pokemonOutputPort::getPokemon)
                .get()
                .peek((pokemonDomain) -> log.info("Pokemon Got {}", pokemonDomain))
                .peekLeft((e) -> log.error("error getting pokemon {} ", e.getMessage()));

    }


}

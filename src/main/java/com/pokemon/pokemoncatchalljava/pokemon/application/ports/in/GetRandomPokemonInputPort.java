package com.pokemon.pokemoncatchalljava.pokemon.application.ports.in;

import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.ApplicationError;
import io.vavr.control.Either;

@FunctionalInterface
public interface GetRandomPokemonInputPort {

    Either<ApplicationError, PokemonDomain> execute();
}

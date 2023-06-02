package com.pokemon.pokemoncatchalljava.pokemon.application.ports.in;

import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;
import io.vavr.control.Either;

@FunctionalInterface
public interface GetRandomPokemonInputPort {

    Either<Exception, PokemonDomain> execute();
}

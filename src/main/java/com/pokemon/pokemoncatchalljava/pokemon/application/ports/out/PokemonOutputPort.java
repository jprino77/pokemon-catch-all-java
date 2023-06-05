package com.pokemon.pokemoncatchalljava.pokemon.application.ports.out;

import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.ApplicationError;
import io.vavr.control.Either;

public interface PokemonOutputPort {

    Either<ApplicationError, PokemonDomain> getPokemon(int id);
}

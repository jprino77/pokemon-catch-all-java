package com.pokemon.pokemoncatchalljava.pokemon.application.ports.out;

import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;
import io.vavr.control.Either;

public interface PokemonOutputPort {

    Either<Exception, PokemonDomain> getPokemon(int id);
}

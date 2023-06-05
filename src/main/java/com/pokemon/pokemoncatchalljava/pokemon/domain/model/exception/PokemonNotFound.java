package com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class PokemonNotFound extends ResourceNotFound {

    public PokemonNotFound(@NonNull Integer id, @NonNull Throwable cause) {

        super("Pokemon with id " + id + " Not Found", "POKEMON_NOT_FOUND", cause);
    }

}

package com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception;

import lombok.Getter;
import lombok.NonNull;


public class PokemonNotFound extends Exception {

    @Getter
    private final String code = "POKEMON_NOT_FOUND";

    public PokemonNotFound(@NonNull Integer id, @NonNull Throwable cause) {

        super("Pokemon with id " + id + "Not Found", cause);
    }

}

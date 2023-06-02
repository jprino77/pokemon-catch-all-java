package com.pokemon.pokemoncatchalljava.pokemon.adapter.repository.rest.model;

import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;

public record PokemonResponse(
        int id,
        String name
) {

    public PokemonDomain toDomain(){
        return new PokemonDomain(
                id(),
                name()
        );
    }
}

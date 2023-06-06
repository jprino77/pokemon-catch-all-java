package com.pokemon.pokemoncatchalljava.adapter.repository.rest;

import com.pokemon.pokemoncatchalljava.config.http.RestClient;
import com.pokemon.pokemoncatchalljava.pokemon.adapter.repository.rest.PokeApi;
import com.pokemon.pokemoncatchalljava.pokemon.adapter.repository.rest.model.PokemonResponse;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.ApplicationError;
import io.vavr.control.Either;
import io.vavr.control.Try;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PokeApiTest {

    private PokeApi pokeApi;
    private RestClient restClient;

    @BeforeEach
    void setUp() {
        restClient = Mockito.mock(RestClient.class);
        pokeApi = new PokeApi(restClient);
    }

    @Test
    @DisplayName("Success get pokemon by id")
    void getPokemon() {

        PokemonDomain expectedDomain = new PokemonDomain(4, "Charmander");
        Either<ApplicationError, PokemonDomain> expectedResult = Either.right(expectedDomain);

        Mockito.when(restClient.get("pokemon/4", PokemonResponse.class))
                .thenReturn(Try.of(() -> new PokemonResponse(4, "Charmander")));

        Either<ApplicationError, PokemonDomain> result = pokeApi.getPokemon(4);


        assertEquals(expectedResult, result);
    }
}
package com.pokemon.pokemoncatchalljava.adapter.repository.rest;

import com.pokemon.pokemoncatchalljava.pokemon.adapter.repository.rest.PokeApi;
import com.pokemon.pokemoncatchalljava.pokemon.adapter.repository.rest.model.PokemonResponse;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;


class PokeApiTest {

    private PokeApi pokeApi;
    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        restTemplate = Mockito.mock(RestTemplate.class);
        pokeApi = new PokeApi(restTemplate);
    }

    @Test
    @DisplayName("Success get pokemon by id")
    void getPokemon() {

        PokemonDomain expectedDomain = new PokemonDomain(4, "Charmander");
        Either<Exception, PokemonDomain> expectedResult = Either.right(expectedDomain);

        Mockito.when(restTemplate.getForObject("pokemon/", PokemonResponse.class))
                .thenReturn(new PokemonResponse(4, "Charmander"));

        Either<Exception, PokemonDomain> result = pokeApi.getPokemon(4);



        assertEquals(expectedResult, result);
    }
}
package com.pokemon.pokemoncatchalljava.pokemon.adapter.command.controller;

import com.pokemon.pokemoncatchalljava.pokemon.application.ports.in.GetRandomPokemonInputPort;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.ApplicationError;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.PokemonNotFound;
import com.pokemon.pokemoncatchalljava.utils.ResponseBuilder;
import io.vavr.control.Either;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


class PokemonControllerTest {

    private MockMvc mockMvc;
    private GetRandomPokemonInputPort getRandomPokemon;

    @BeforeEach
    void setUp() {
        getRandomPokemon = Mockito.mock(GetRandomPokemonInputPort.class);
        ResponseBuilder responseBuilder = new ResponseBuilder();
        PokemonController controller = new PokemonController(getRandomPokemon, responseBuilder);

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();
    }

    @Test
    @DisplayName("Success get random pokemon")
    void successGetRandomPokemon() throws Exception {

        PokemonDomain expectedDomain = new PokemonDomain(4, "Charmander");
        Either<ApplicationError, PokemonDomain> expectedResult = Either.right(expectedDomain);

        Mockito.when(getRandomPokemon.execute())
                .thenReturn(expectedResult);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(
                        "/v1/pokemon"
                ).contentType(MediaType.APPLICATION_JSON)
        );


        result
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(4)));

    }

    @Test
    @DisplayName("Success get random pokemon")
    void failGetRandomPokemon() throws Exception {

        PokemonNotFound expectedError = new PokemonNotFound(1, new Exception("test"));
        Either<ApplicationError, PokemonDomain> expectedResult = Either.left(expectedError);

        Mockito.when(getRandomPokemon.execute())
                .thenReturn(expectedResult);

        ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get(
                        "/v1/pokemon"
                ).contentType(MediaType.APPLICATION_JSON)
        );


        result
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.equalTo("POKEMON_NOT_FOUND")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.equalTo("Pokemon with id 1 Not Found")));

    }


}
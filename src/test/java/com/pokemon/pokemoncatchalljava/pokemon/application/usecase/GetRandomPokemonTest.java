package com.pokemon.pokemoncatchalljava.pokemon.application.usecase;

import com.pokemon.pokemoncatchalljava.pokemon.application.ports.out.PokemonOutputPort;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.PokemonDomain;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.ApplicationError;
import com.pokemon.pokemoncatchalljava.utils.RandomComponent;
import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetRandomPokemonTest {

    private GetRandomPokemon useCase;
    private RandomComponent random;
    private PokemonOutputPort pokemonOutputPort;

    @BeforeEach
    void setUp() {
        random = Mockito.mock(RandomComponent.class);
        pokemonOutputPort = Mockito.mock(PokemonOutputPort.class);

        useCase = new GetRandomPokemon(random, pokemonOutputPort);

    }

    @Test
    @DisplayName("Success getRandom Pokemon")
    void execute() {
        PokemonDomain expectedDomain = new PokemonDomain(4, "Charmander");
        Either<ApplicationError, PokemonDomain> expectedResult = Either.right(expectedDomain);

        Mockito.when(random.randomizeInt()).thenReturn(4);

        Mockito.when(pokemonOutputPort.getPokemon(4))
                .thenReturn(expectedResult);

        Either<ApplicationError, PokemonDomain> result = useCase.execute();

        assertEquals(expectedResult, result);
    }
}
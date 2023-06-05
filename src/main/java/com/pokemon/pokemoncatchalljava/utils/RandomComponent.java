package com.pokemon.pokemoncatchalljava.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public final class RandomComponent {
    private final int min;

    private final int max;

    public RandomComponent(@Value("${pokemon-range.min}") int min, @Value("${pokemon-range.max}") int max) {
        this.min = min;
        this.max = max;
    }

    public int randomizeInt() {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .orElse(1);
    }
}


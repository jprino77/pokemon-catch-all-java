package com.pokemon.pokemoncatchalljava.config.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Component
@Target({TYPE})
@Retention(RUNTIME)
@Documented
public @interface UseCase {
}

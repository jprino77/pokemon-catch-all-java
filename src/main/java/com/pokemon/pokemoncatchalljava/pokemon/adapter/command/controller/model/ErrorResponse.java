package com.pokemon.pokemoncatchalljava.pokemon.adapter.command.controller.model;

import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.ApplicationError;
import lombok.NonNull;

public record ErrorResponse(
        @NonNull String code,
        @NonNull String description
) {

    public static ErrorResponse fromDomain(ApplicationError error) {
        return new ErrorResponse(
                error.getCode(),
                error.getMessage()
        );

    }
}

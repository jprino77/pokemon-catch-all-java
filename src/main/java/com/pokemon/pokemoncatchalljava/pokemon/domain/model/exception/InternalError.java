package com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception;

import lombok.NonNull;

public abstract non-sealed class InternalError extends ApplicationError {
    protected InternalError(@NonNull String code, @NonNull String message, @NonNull Throwable cause) {
        super(code, message, cause);
    }
}

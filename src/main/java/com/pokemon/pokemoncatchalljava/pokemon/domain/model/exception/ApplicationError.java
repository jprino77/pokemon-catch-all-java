package com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception;

import lombok.Getter;
import lombok.NonNull;

public abstract sealed class ApplicationError extends Exception permits BusinessError, InternalError, ResourceNotFound, ValidationError {

    @Getter
    protected final String code;

    protected ApplicationError(@NonNull String code, @NonNull String message, @NonNull Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}

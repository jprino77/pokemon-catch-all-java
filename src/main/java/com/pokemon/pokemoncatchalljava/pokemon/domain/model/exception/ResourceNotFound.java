package com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception;

import lombok.NonNull;

public abstract class ResourceNotFound extends ApplicationError {
    protected ResourceNotFound(@NonNull String message, @NonNull String code, @NonNull Throwable cause) {
        super(code, message, cause);
    }
}

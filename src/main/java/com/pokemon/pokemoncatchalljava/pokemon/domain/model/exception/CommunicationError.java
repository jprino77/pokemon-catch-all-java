package com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
public class CommunicationError extends InternalError {

    public CommunicationError(@NonNull String server, @NonNull Throwable cause) {

        super("Communication error with " + server, "COMMUNICATION_ERROR", cause);
    }

}

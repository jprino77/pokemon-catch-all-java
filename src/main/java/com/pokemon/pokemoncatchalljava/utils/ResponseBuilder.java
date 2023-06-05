package com.pokemon.pokemoncatchalljava.utils;

import com.pokemon.pokemoncatchalljava.pokemon.adapter.command.controller.model.ErrorResponse;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.InternalError;
import com.pokemon.pokemoncatchalljava.pokemon.domain.model.exception.*;
import io.vavr.Function0;
import io.vavr.Function2;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import static io.vavr.API.*;
import static io.vavr.Predicates.instanceOf;

@Component
final public class ResponseBuilder {


    public <T> ResponseEntity buildResponseFrom(Function0<Either<ApplicationError, T>> function) {
        return function
                .apply()
                .map(
                        this::buildSuccessResponse
                ).getOrElseGet(
                        this::buildErrorResponse
                );
    }

    private <T> ResponseEntity buildSuccessResponse(T domain) {
        return new ResponseEntity(domain, HttpStatusCode.valueOf(HttpStatus.OK.value()));
    }

    private ResponseEntity buildErrorResponse(ApplicationError e) {

        final Function2<ApplicationError, HttpStatus, ResponseEntity> buildResponse =
                (applicationError, httpStatus) ->
                        new ResponseEntity(ErrorResponse.fromDomain(applicationError), httpStatus);

        return Match(e).of(
                Case($(instanceOf(ResourceNotFound.class)),
                        o -> buildResponse.apply(e, HttpStatus.NOT_FOUND)
                ),
                Case($(instanceOf(ValidationError.class)),
                        o -> buildResponse.apply(e, HttpStatus.BAD_REQUEST)
                ),
                Case($(instanceOf(BusinessError.class)),
                        o -> buildResponse.apply(e, HttpStatus.UNPROCESSABLE_ENTITY)
                ),
                Case($(instanceOf(InternalError.class)),
                        o -> buildResponse.apply(e, HttpStatus.INTERNAL_SERVER_ERROR)
                ),
                Case($(),
                        o -> buildResponse.apply(e, HttpStatus.INTERNAL_SERVER_ERROR)
                )
        );
    }
}

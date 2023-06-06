package com.pokemon.pokemoncatchalljava.config.http;

import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.NeverRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.web.client.HttpServerErrorException;

public final class InternalServerExceptionClassifierRetryPolicy extends ExceptionClassifierRetryPolicy {


    public InternalServerExceptionClassifierRetryPolicy(int maxAttempts) {

        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(maxAttempts);

        this.setExceptionClassifier(classifiable -> {
                    if (classifiable instanceof HttpServerErrorException) {
                        if (((HttpServerErrorException) classifiable).getStatusCode().is5xxServerError()) {
                            return simpleRetryPolicy;
                        } else return new NeverRetryPolicy();
                    } else {
                        return new NeverRetryPolicy();
                    }
                }
        );
    }
}

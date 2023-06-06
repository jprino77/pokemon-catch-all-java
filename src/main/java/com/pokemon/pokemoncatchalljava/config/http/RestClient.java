package com.pokemon.pokemoncatchalljava.config.http;

import io.vavr.Function0;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@Slf4j
public final class RestClient {

    private final RestTemplate restTemplate;
    private final RetryTemplate retryTemplate;

    public <R> Try<R> get(final String url, Class<R> clazz) {
        return executeCall(
                () -> {
                    log.info("get for url: " + url);
                    return restTemplate.getForObject(url, clazz);
                }
        );

    }

    private <R> Try<R> executeCall(Function0<R> callBack) {

        return Try.of(() -> retryTemplate.execute(
                context -> callBack.apply()
        ));
    }


}

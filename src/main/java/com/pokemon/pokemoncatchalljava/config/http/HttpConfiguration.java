package com.pokemon.pokemoncatchalljava.config.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.retry.backoff.BackOffPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

@Configuration
public class HttpConfiguration {

    @Primary
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }


    @Bean("pokeApiRestTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder, @Value("${poke-api.timeout}") Long timeout, @Value("${poke-api.uri}") String uri) {

        return builder
                .setConnectTimeout(Duration.ofMillis(timeout))
                .setReadTimeout(Duration.ofMillis(timeout))
                .uriTemplateHandler(new DefaultUriBuilderFactory(uri))
                .build();
    }

    @Bean
    public RetryTemplate retryTemplate(
            @Value("${rest-client.retry.max-attempts}") Integer maxAttempts,
            BackOffPolicy backOffPolicy
    ) {
        RetryTemplate template = new RetryTemplate();

        template.setRetryPolicy(new InternalServerExceptionClassifierRetryPolicy(maxAttempts));
        template.setBackOffPolicy(backOffPolicy);

        return template;
    }

    @Bean
    public ExponentialBackOffPolicy backOffPolicy(
            @Value("${rest-client.retry.backoff-initial-interval}") Long backoffInitialInterval,
            @Value("${rest-client.retry.backoff-max-interval}") Long backoffMaxInterval,
            @Value("${rest-client.retry.backoff-multiplier}") Double backoffMultiplier
    ) {
        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(backoffInitialInterval);
        exponentialBackOffPolicy.setMaxInterval(backoffMaxInterval);
        exponentialBackOffPolicy.setMultiplier(backoffMultiplier);

        return exponentialBackOffPolicy;
    }

    @Bean("pokeApiRestClient")
    public RestClient pokeApiRestClient(
            @Qualifier("pokeApiRestTemplate") RestTemplate restTemplate,
            RetryTemplate retryTemplate
    ) {
        return new RestClient(restTemplate, retryTemplate);
    }
}

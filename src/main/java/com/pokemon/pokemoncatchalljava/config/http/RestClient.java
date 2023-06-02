package com.pokemon.pokemoncatchalljava.config.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

@Configuration
public class RestClient {

    @Primary
    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder(){
        return new Jackson2ObjectMapperBuilder()
                .propertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }


    @Bean("PokeApiRestTemplate")
    public RestTemplate restTemplate(RestTemplateBuilder builder, @Value("${poke-api.timeout}") Long timeout, @Value("${poke-api.uri}") String uri) {

        return builder
                .setConnectTimeout(Duration.ofMillis(timeout))
                .setReadTimeout(Duration.ofMillis(timeout))
                .uriTemplateHandler(new DefaultUriBuilderFactory(uri))
                .build();
    }
}

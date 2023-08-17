package com.weather.service;

import com.weather.model.CityDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
@Slf4j
public class GoWeatherService {
    private static final String GO_WEATHER_ENDPOINT = "https://goweather.herokuapp.com/weather/";
    private final WebClient webClient;


    public Mono<CityDetails> getWeather(String cityName) {
        return webClient.get().uri(GO_WEATHER_ENDPOINT + cityName).retrieve().bodyToMono(CityDetails.class)
                .map(cityDetails -> {
                    cityDetails.setName(cityName);
                    return cityDetails;
                })
                .onErrorResume(throwable -> handleError(throwable, cityName));
    }

    private Mono<CityDetails> handleError(Throwable t, String cityName) {
        log.error("Error: " +  t.getMessage());
        CityDetails cityDetails = new CityDetails();
        cityDetails.setName(cityName);
        return Mono.just(cityDetails);
    }
}

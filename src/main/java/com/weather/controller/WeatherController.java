package com.weather.controller;

import com.weather.model.CityDetails;
import com.weather.service.WeatherProcessingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherProcessingService weatherProcessingService;

    @GetMapping
    public Flux<CityDetails> getWeather(@RequestParam("city") String cityNames) {
        return weatherProcessingService.fetchAllData(cityNames);
    }
}

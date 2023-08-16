package com.weather.service;

import com.weather.model.CityDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.CharSequenceEncoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeatherProcessingService {
    private static final String HEADER = "Name,temperature,wind\n";

    private final GoWeatherService goWeatherService;

    public Flux<CityDetails> fetchAllData(String cityNames) {
        List<String> cities = Arrays.asList(cityNames.split("\\s*,\\s*"));

        Flux<CityDetails> cityDetailsFlux = Flux.fromIterable(cities)
                .flatMap(goWeatherService::getWeather)
                .sort();

        writeInCsvFile(cityDetailsFlux);

        return cityDetailsFlux;
    }

    private void writeInCsvFile(Flux<CityDetails> cityDetailsFlux) {
        DefaultDataBufferFactory bufferFactory = new DefaultDataBufferFactory();
        CharSequenceEncoder encoder = CharSequenceEncoder.textPlainOnly();

        Flux<DataBuffer> headerDataFlux = Flux.just(encoder.encodeValue(HEADER, bufferFactory, ResolvableType.NONE, null, null));

        Flux<DataBuffer> dataBufferFlux = cityDetailsFlux
                .map(CityDetails::generateCSVEntry)
                .map(s -> s + "\n")
                .map(line ->
                        encoder.encodeValue(line, bufferFactory, ResolvableType.NONE, null, null)
                );

        DataBufferUtils.write(
                Flux.concat(headerDataFlux, dataBufferFlux),
                Path.of("output.csv")
        ).subscribe();
    }
}

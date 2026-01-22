package com.laboratorio.springboot64.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
public class MeteoTools {
    private static final String METEO_SERVICE_URL = "https://www.meteosource.com/api/v1/free";
    private static final String APIKEY = "[TU_API_KEY]";

    private final WebClient webClient;

    public MeteoTools(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl(METEO_SERVICE_URL)
                .build();
    }

    @Tool(description = """
           Obtiene el listado de las ciudades con información climática disponible.
           Recibe como parámetro una cadena de caracteres con el nombre de la ciudad para la cual se quiere obtener los datos del clima.
           Devuelve los datos de las ciudades existente con el nombre dado por parámetro, incluyendo el place_id que identifica de manera única una ciudad. 
            """)
    public String getAvaibleCities(String name) {
        log.info("**************** Se está usando la herramienta: getAvaibleCities()");
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/find_places")
                        .queryParam("text", name)
                        .queryParam("key", APIKEY)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Tool(description = """
            Permite obtener la información del clima en tiempo real para una ciudad a partir de su identificador (place_id).
            Recibe como parámetro la cadena de caracteres con el identificador único de la ciudad.
            """)
    public String getCityMeteo(String placeId) {
        log.info("**************** Se está usando la herramienta: getCityMeteo()");
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/point")
                        .queryParam("place_id", placeId)
                        .queryParam("units", "metric")
                        .queryParam("key", APIKEY)
                        .build()
                )
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Tool(description = """
            Permite registrar en base de datos la temperatura de una ciudad.
            Recibe como parámetros, el nombre de la ciudad en una variable de tipo cadena y la temperatura en una
            variable de tipo double.
            """)
    public void registerTemperature(String placeName, double temperature) {
        log.info("************************ La temperatura en {} es: {}", placeName, temperature);
        log.info("************************ La temperatura en {} es: {}", placeName, temperature);
    }
}
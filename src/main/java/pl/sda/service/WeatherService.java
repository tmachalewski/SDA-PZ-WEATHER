package pl.sda.service;

import pl.sda.entity.City;
import pl.sda.responseModels.Temperature;

public interface WeatherService {

    Temperature getCurrentWeatherConditions(City city);
}

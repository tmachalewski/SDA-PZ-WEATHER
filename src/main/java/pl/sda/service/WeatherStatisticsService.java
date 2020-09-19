package pl.sda.service;

import pl.sda.entity.City;
import pl.sda.responseModels.Imperial;
import pl.sda.responseModels.Metric;
import pl.sda.responseModels.Temperature;

public class WeatherStatisticsService {

    AccuWeatherService aws;
    OpenWeatherService ows;

    //you could consider passing vararg of WeatherServices, rather than specific services;
    public WeatherStatisticsService(AccuWeatherService aws, OpenWeatherService ows){
        this.aws=aws;
        this.ows=ows;
    }

    //here, if you had an array or a collection of services you could stream through them and get averaged characteristics easily
    public Temperature getAveragedWeatherConditions(City city){
        Temperature tempAws = aws.getCurrentWeatherConditions(city);
        Temperature tempOws = ows.getCurrentWeatherConditions(city);

        Imperial imperialOutput = new Imperial();
        imperialOutput.setValue(
                (tempAws.getImperial().getValue()
                +tempOws.getImperial().getValue())
                /2
        );

        Metric metricOutput = new Metric();
        metricOutput.setValue(
                (tempAws.getMetric().getValue()
                +tempOws.getMetric().getValue())
                /2
        );

        Temperature tempOutput = new Temperature();
        tempOutput.setImperial(imperialOutput);
        tempOutput.setMetric(metricOutput);

        return tempOutput;
    }
}

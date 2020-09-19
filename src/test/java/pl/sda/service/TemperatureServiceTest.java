package pl.sda.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.sda.entity.City;
import pl.sda.responseModels.Imperial;
import pl.sda.responseModels.Metric;
import pl.sda.responseModels.Temperature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TemperatureServiceTest {

    @Mock
    AccuWeatherService aws;

    @Mock
    OpenWeatherService ows;

    @Mock
    City city;

    static Temperature temperatureAws;
    static Temperature temperatureOws;

    @BeforeAll
    public static void setObjectThatAreReturnedByMockedMethods(){
        temperatureAws = getTemperatureAws();
        temperatureOws = getTemperatureOws();
    }

    private static Temperature getTemperatureAws(){
        Metric m = new Metric();
        m.setValue(100.0f);

        Imperial i = new Imperial();
        i.setValue(150.0f);
        Temperature t = new Temperature();
        t.setMetric(m);
        t.setImperial(i);

        return t;
    }

    private static Temperature getTemperatureOws(){
        Metric m = new Metric();
        m.setValue(200.0f);

        Imperial i = new Imperial();
        i.setValue(300.0f);
        Temperature t = new Temperature();
        t.setMetric(m);
        t.setImperial(i);

        return t;
    }

    @Test
    public void weatherStatisticsServiceShouldAverageCorrectly()
    {
        //Assuming
        WeatherStatisticsService ts = new WeatherStatisticsService(aws, ows);
        when(aws.getCurrentWeatherConditions(any())).thenReturn(temperatureAws);
        when(ows.getCurrentWeatherConditions(any())).thenReturn(temperatureOws);

        //Act
        Temperature averaged = ts.getAveragedWeatherConditions(city);

        //Assert
        assertEquals(150.0f, averaged.getMetric().getValue());
        assertEquals(225.0f, averaged.getImperial().getValue());
    }

}

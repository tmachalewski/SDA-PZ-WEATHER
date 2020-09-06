package pl.sda.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.sda.entity.AdminArea;
import pl.sda.entity.City;
import pl.sda.responseModels.AccuWeatherCurrentConditionsNoDetails;
import pl.sda.responseModels.Temperature;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.List;
import java.util.Properties;

public class AccuWeatherService {

    private String getApplicationKey(){
        Properties prop = System.getProperties();
        String key = (String)prop.get("accuWeatherAppKey");
        if(key==null){
            throw new RuntimeException("Key for accuWeather is not set: add -DaccuWeatherAppKey=value to VM options");
        }
        return key;
    }

    public List<AdminArea> getAvailableLocations() {
        String key = getApplicationKey();

        Request request = new Request.Builder()
                .url("http://dataservice.accuweather.com/currentconditions/v1/274945?apikey=m5v2C5Tejc88GUriqavjJqofwnNEcqZu&language=en-us&details=false")
                .build();

        try (Response response = new OkHttpClient().newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            System.out.println(response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }


        //String applicationKey = Properties.get
        return null;
    }

    public Temperature getCurrentWeatherConditions(City city){
        //String responseString = "[{\"LocalObservationDateTime\":\"2020-09-06T01:00:00+02:00\",\"EpochTime\":1599346800,\"WeatherText\":\"Cloudy\",\"WeatherIcon\":7,\"HasPrecipitation\":false,\"PrecipitationType\":null,\"IsDayTime\":false,\"Temperature\":{\"Metric\":{\"Value\":16.3,\"Unit\":\"C\",\"UnitType\":17},\"Imperial\":{\"Value\":61.0,\"Unit\":\"F\",\"UnitType\":18}},\"MobileLink\":\"http://m.accuweather.com/en/pl/opole/274945/current-weather/274945?lang=en-us\",\"Link\":\"http://www.accuweather.com/en/pl/opole/274945/current-weather/274945?lang=en-us\"}]";

        Request request = new Request.Builder()
                .url("http://dataservice.accuweather.com/currentconditions/v1/" +
                        city.getLocationKey() +
                        "?" +
                        "apikey=" +
                        getApplicationKey() +
                        "&language=en-us&details=false")
                .build();

        String responseString = null;
        try (Response response = new OkHttpClient().newCall(request).execute()) {

            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Get response body
            responseString = response.body().string();

        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        AccuWeatherCurrentConditionsNoDetails[] currents = new AccuWeatherCurrentConditionsNoDetails[1];
        try {
            currents = objectMapper.readValue(responseString, AccuWeatherCurrentConditionsNoDetails[].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return currents[0].getTemperature();
    }
}

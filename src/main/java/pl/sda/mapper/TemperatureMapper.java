package pl.sda.mapper;

import pl.sda.entity.TemperatureMeasurement;
import pl.sda.responseModels.Temperature;

public class TemperatureMapper {

    public TemperatureMeasurement map(Temperature t){
        TemperatureMeasurement tm = new TemperatureMeasurement();
        tm.setValue(t.getMetric().getValue());
        return tm;
    }
}

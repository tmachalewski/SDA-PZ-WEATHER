package pl.sda.responseModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Metric {

    @JsonProperty("Value")
    private float value;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}

package app.virtual_guardian.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Prediction {
    @JsonProperty("prediction")
    private String prediction;

    public Prediction() {
    }

    public Prediction(String prediction) {
        this.prediction = prediction;
    }


    public String getPrediction() {
        return prediction;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }
}

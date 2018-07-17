package com.zxdev.app.weatherapp2.model;

import android.util.Log;

public class Forecast {

    private String condition;
    private double temp;

    public Forecast() {
    }

    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {
        switch (condition) {
            case "Thunderstorm":
            case "Drizzle":
            case "Rain":
            case "Snow":
                condition = "Small-Rainy";
                break;
            case "Atmosphere":
            case "Clouds":
                condition = "Small-Cloudy";
                break;
            case "Clear":
                condition = "Small-Sunny";
                break;
            default:
                Log.e("Error", "Cant get weather conditon");
                break;
        }
        this.condition = condition;
    }
    public double getTemp() {
        return temp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
}

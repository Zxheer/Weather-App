package com.zxdev.app.weatherapp2;

import android.util.Log;

public class Weather {

    private String condition;
    private String city;
    private double temp;
    private double minTemp;
    private double maxTemp;

    public Weather() {

    };

    public String getCondition() {
        return condition;
    }
    public void setCondition(String condition) {

        switch (condition) {
            case "Thunderstorm":
            case "Drizzle":
            case "Rain":
            case "Snow":
                condition = "Rainy";
                break;
            case "Atmosphere":
            case "Clouds":
                condition = "Cloudy";
                break;
            case "Clear":
                condition = "Sunny";
                break;
            default:
                Log.e("Error", "Cant get weather conditon");
                break;
        }
        this.condition = condition;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public double getTemp() {
        return temp;
    }
    public void setTemp(double temp) {
        this.temp = temp;
    }
    public double getMinTemp() {
        return minTemp;
    }
    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }
    public double getMaxTemp() {
        return maxTemp;
    }
    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

}

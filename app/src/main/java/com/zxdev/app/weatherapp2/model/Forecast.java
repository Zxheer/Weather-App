package com.zxdev.app.weatherapp2.model;

import android.util.Log;

/**
 * @author Zaheer Ebrahim
 * SOLAR
 * 2018-07-19
 * Version 1.1.0
 */
public class Forecast {

    private String condition;
    private double temp;
    private double min;  //Min and max are used as the 5 day forcast gives a better estimate on the
    private double max;  //current days max and min temp and thus this is checked in HomeActivity

    /**
     * Default constructor for the 5 day forecast information
     */
    public Forecast() {
    }

    public String getCondition() {
        return condition;
    }
    /**
     * /**
     * Mutator to match the condition in the API to the correct condition for the image using the imageHelper
     * @param condition Current weather condition
     */
    public void setCondition(String condition)
    {
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
    public double getMin() {
        return min;
    }
    public void setMin(double min) {
        this.min = min;
    }
    public double getMax() {
        return max;
    }
    public void setMax(double max) {
        this.max = max;
    }
}

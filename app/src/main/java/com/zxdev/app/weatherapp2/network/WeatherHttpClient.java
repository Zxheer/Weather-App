package com.zxdev.app.weatherapp2.network;


import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Zaheer Ebrahim
 * SOLAR
 * 2018-07-19
 * Version 1.1.0
 */
public class WeatherHttpClient {

    private static final String CURRENT_URL = "http://api.openweathermap.org/data/2.5/weather?lat=";
    private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?lat=";
    private static final String API_KEY = "f48f1ba733d2f6120c39c146d3b15d8b";

    /**
     * Function to set the URL's and get and return the data
     * @param lat Users current Latitiude
     * @param lon Users current Longitude
     * @param lang Users default Language
     * @return String[] data consisiting of the weather and forecast JSON Objects
     */
    public String[] getAllData(String lat, String lon, String lang) {

        String[] data = new String[2];
        String currentUrl = CURRENT_URL + lat + "&lon=" + lon + "&lang=" + lang + "&units=metric" + "&APPID=" + API_KEY;
        String forecastUrl = FORECAST_URL + lat + "&lon=" + lon + "&lang=" + lang +"&units=metric" + "&APPID=" + API_KEY;

        data[0] = getData(currentUrl);  //Use the same getData function get get both weather + forecast data
        data[1] = getData(forecastUrl);

        return data;
    }

    /**
     * Function to connect to API and get JSON object
     * @param url Built API URL
     * @return String of the JSON data recieved from the API
     */
    private String getData(String url) {

        HttpURLConnection connection = null;
        InputStream is = null;

        try {
            connection = (HttpURLConnection) (new URL(url)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            StringBuffer sTemp = new StringBuffer();
            is = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null)
                sTemp.append(line + "\r\n");

            is.close();
            connection.disconnect();
            return sTemp.toString();

        } catch (Throwable t) {
            t.printStackTrace();

        } finally {
            try {
                assert is != null;  //Assertions for extra error checking
                is.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                assert connection != null;
                connection.disconnect();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return null;
    }
}

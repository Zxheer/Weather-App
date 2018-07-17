package com.zxdev.app.weatherapp2.network;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherHttpClient {

    private static final String CURRENT_URL = "http://api.openweathermap.org/data/2.5/weather?lat=";
    private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?lat=";
    private static final String API_KEY = "f48f1ba733d2f6120c39c146d3b15d8b";

    public String[] getWeatherData(String lat, String lon, String lang) {

        String[] data = new String[2];
        String currentUrl = CURRENT_URL + lat + "&lon=" + lon + "&lang=" + lang + "&units=metric" + "&APPID=" + API_KEY;
        String forecastUrl = FORECAST_URL + lat + "&lon=" + lon + "&lang=" + lang +"&units=metric" + "&APPID=" + API_KEY + "&cnt=5";

        data[0] = getData(currentUrl);
        data[1] = getData(forecastUrl);

        return data;
    }

    private String getData(String url) {

        HttpURLConnection con = null;
        InputStream is = null;

        try {
            con = (HttpURLConnection) (new URL(url)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null)
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            return buffer.toString();

        } catch (Throwable t) {
            t.printStackTrace();

        } finally {
            try {
                assert is != null;
                is.close();
            } catch (Throwable t) {
                t.printStackTrace();
            }
            try {
                assert con != null;
                con.disconnect();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return null;
    }
}

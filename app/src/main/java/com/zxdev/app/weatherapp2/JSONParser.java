package com.zxdev.app.weatherapp2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONParser {

    public static Weather getWeather(String data) throws JSONException {

        Weather weather = new Weather();

        JSONObject rawObj = new JSONObject(data);

        JSONArray tempArr = rawObj.getJSONArray("weather");
        JSONObject weatherCond = tempArr.getJSONObject(0);
        JSONObject tempObj = getObject(rawObj);

        weather.setCondition(weatherCond.getString("main"));

        weather.setMaxTemp(tempObj.getDouble("temp_max"));
        weather.setMinTemp(tempObj.getDouble("temp_min"));
        weather.setTemp(tempObj.getDouble("temp"));

        weather.setCity(rawObj.getString("name"));

        return weather;
    }

    public static Forecast[] getForecast(String data) throws JSONException {

        Forecast[] days = new Forecast[5];
        days[0] = new Forecast();
        days[1] = new Forecast();
        days[2] = new Forecast();
        days[3] = new Forecast();
        days[4] = new Forecast();

        JSONObject rawObj = new JSONObject(data);
        JSONArray tempArrr = rawObj.getJSONArray("list");

        for (int i = 0; days.length > i; i++) {

            JSONObject rawArr = tempArrr.getJSONObject(i);
            JSONObject mainObj = rawArr.getJSONObject("main");
            days[i].setTemp(mainObj.getDouble("temp"));

            JSONArray weatherArr = rawArr.getJSONArray("weather");
            JSONObject weatherObj = weatherArr.getJSONObject(0);

            days[i].setCondition(weatherObj.getString("main"));
        }
        return days;
    }

    private static JSONObject getObject(JSONObject jObj) throws JSONException {

        JSONObject tempObj;
        tempObj = jObj.getJSONObject("main");
        return tempObj;
    }
}

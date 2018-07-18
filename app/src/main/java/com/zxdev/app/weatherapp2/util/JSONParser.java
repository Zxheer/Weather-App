package com.zxdev.app.weatherapp2.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.zxdev.app.weatherapp2.model.Weather;
import com.zxdev.app.weatherapp2.model.Forecast;

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

        Double highTemp = 0.0;
        Integer Highest = 0;
        Integer z = 0;


        for (int i = 0; 40 > i; i++)
        {
            JSONObject rawArr = tempArrr.getJSONObject(i);
            JSONObject mainObj = rawArr.getJSONObject("main");

            if (i == 0)
            {
                highTemp = mainObj.getDouble("temp");
                Highest = 0 ;
            }

            if (highTemp < mainObj.getDouble("temp"))
            {
                highTemp = mainObj.getDouble("temp");
                Highest = i;
            }


            if (i % 8 == 0)
            {
                JSONObject rawArr2 = tempArrr.getJSONObject(Highest);
                JSONObject mainObj2 = rawArr2.getJSONObject("main");
                days[z].setTemp(mainObj2.getDouble("temp"));
                Highest = 0;
                highTemp = 0.0;
                z++;

            }
        }

        Integer k = 0;

        for (int i = 0; 40 > i; i+= 8) {

            JSONObject rawArr = tempArrr.getJSONObject(i);
            //JSONObject mainObj = rawArr.getJSONObject("main");
            //days[i].setTemp(mainObj.getDouble("temp"));

            JSONArray weatherArr = rawArr.getJSONArray("weather");
            JSONObject weatherObj = weatherArr.getJSONObject(0);
            if(!(i == 0))
            {
                k = (Integer) Math.round(i/8);
                days[k].setCondition(weatherObj.getString("main"));
            }
            else
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

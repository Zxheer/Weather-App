package com.zxdev.app.weatherapp2.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zxdev.app.weatherapp2.HomeActivity;
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
        Double lowTemp = 0.0;
        Integer Highest = 0;
        Integer z = 0;
        Integer x = 0;
        Integer carryOver = 0;
        Integer q = 0;
        boolean b = false;
        Integer i = 0;
        Integer Lowest = 0;
        Double temp = 0.0;

        while (!b) {
            JSONObject rawArr = tempArrr.getJSONObject(i);
            JSONObject mainObj = rawArr.getJSONObject("main");

            String time = rawArr.getString("dt_txt");

            temp = mainObj.getDouble("temp");


            if (i == 0) {
                highTemp = temp;//mainObj.getDouble("temp");
                lowTemp = temp;//mainObj.getDouble("temp");
                Highest = 0;
                Lowest = 0;
            }

            if (highTemp < mainObj.getDouble("temp")) {
                highTemp = mainObj.getDouble("temp");
                Highest = i;
            }

            if (lowTemp > mainObj.getDouble("temp")) {
                lowTemp = mainObj.getDouble("temp");
                Lowest = i;
            }

            if ((time.charAt(11) == '0') && (time.charAt(12) == '0')) {
                JSONObject rawArr2 = tempArrr.getJSONObject(Highest);
                JSONObject mainObj2 = rawArr2.getJSONObject("main");
                days[0].setMax(mainObj2.getDouble("temp"));

                JSONObject rawArr23 = tempArrr.getJSONObject(Lowest);
                JSONObject mainObj23 = rawArr23.getJSONObject("main");
                days[0].setMin(mainObj23.getDouble("temp"));



                carryOver = x;
                b = true;

            }

            i++;
            x++;
        }


        z = 0;
        Integer l = 1;

        for (int p = carryOver; p < 32 + carryOver; p++) {

            JSONObject rawArr3 = tempArrr.getJSONObject(p);
            JSONObject mainObj = rawArr3.getJSONObject("main");

            if (p == carryOver) {
                highTemp = mainObj.getDouble("temp");
                Highest = 0;
            }

            if (highTemp < mainObj.getDouble("temp")) {
                highTemp = mainObj.getDouble("temp");
                Highest = p;
            }

            if (l % 8 == 0) {
                JSONObject rawArr2 = tempArrr.getJSONObject(Highest);
                JSONObject mainObj2 = rawArr2.getJSONObject("main");

                days[z].setTemp(mainObj2.getDouble("temp"));
                Highest = 0;
                highTemp = 0.0;
                z++;

            }
            l++;
        }

        for (int g = 0; g < 8-carryOver; g++)
        {
            JSONObject rawArr3 = tempArrr.getJSONObject(g+carryOver+32);
            JSONObject mainObj = rawArr3.getJSONObject("main");

            if (g == carryOver) {
                highTemp = mainObj.getDouble("temp");
                Highest = 0;
            }

            if (highTemp < mainObj.getDouble("temp")) {
                highTemp = mainObj.getDouble("temp");
                Highest = g+carryOver+32;
                days[4].setTemp(mainObj.getDouble("temp"));
            }

        }

        Integer k = 0;

        JSONObject rawObj2 = new JSONObject(data);
        JSONArray tempArrr2 = rawObj2.getJSONArray("list");

        for (int v = 0; 40 > v; v += 8) {

            JSONObject rawArr = tempArrr2.getJSONObject(v);

            JSONArray weatherArr = rawArr.getJSONArray("weather");
            JSONObject weatherObj = weatherArr.getJSONObject(0);


            if (!(v == 0)) {
                k = (Integer) Math.round(v / 8);
                days[k].setCondition(weatherObj.getString("main"));
            } else
                days[v].setCondition(weatherObj.getString("main"));


        }

        return days;
    }

    private static JSONObject getObject(JSONObject jObj) throws JSONException {

        JSONObject tempObj;
        tempObj = jObj.getJSONObject("main");
        return tempObj;
    }
}

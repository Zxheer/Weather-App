package com.zxdev.app.weatherapp2.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zxdev.app.weatherapp2.HomeActivity;
import com.zxdev.app.weatherapp2.model.Weather;
import com.zxdev.app.weatherapp2.model.Forecast;

/**
 * @author Zaheer Ebrahim
 * SOLAR
 * 2018-07-19
 * Version 1.1.0
 */
public class JSONParser {

    /**
     * Fucntion to parse the JSON for the weather API's data
     * @param data Weather data
     * @return returns populated weather object
     * @throws JSONException
     */
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

    /**
     * Fucntion to parse the JSON for the forecast API's data
     * @param data Forecast data
     * @return returns populated forecast object
     * @throws JSONException
     */
    public static Forecast[] getForecast(String data) throws JSONException {

        Forecast[] days = new Forecast[5];
        days[0] = new Forecast();
        days[1] = new Forecast();
        days[2] = new Forecast();
        days[3] = new Forecast();
        days[4] = new Forecast();

        JSONObject rawObj = new JSONObject(data);
        JSONArray tempArrr = rawObj.getJSONArray("list");  //Full array for 40 records of weather data for 5 days and today's

        Double highTemp = 0.0; //Value of highest temp
        Double lowTemp = 0.0;  //Value of highest temp
        Integer Highest = 0;   //Index of highest temp
        Integer Lowest = 0;    //Index of highest temp

        Integer z = 0;         //Will track the 5 indicies of the array
        Integer x = 0;
        Integer i = 0;

        Integer carryOver = 0;

        boolean bFoundDay1 = false;

        Double temp = 0.0;

        while (!bFoundDay1)                                    //Since the 5 day forcast API returns info on whats left of the current day,
        {                                                      //the while loop gets the index of where this data stops and assigns the carryOver value,
            JSONObject rawArr = tempArrr.getJSONObject(i);     //the carry overValue tracts what would be left of the last day since if there was 5 times
            JSONObject mainObj = rawArr.getJSONObject("main"); //data's for the current day the last day will have 5 less time based 3 hour forecast data

            String time = rawArr.getString("dt_txt");

            temp = mainObj.getDouble("temp");

            if (i == 0)            //First run therefore highest = first value
            {
                highTemp = temp;
                lowTemp = temp;
                Highest = 0;
                Lowest = 0;
            }

            if (highTemp < temp)   //Check if current value higher than max
            {
                highTemp = temp;
                Highest = i;
            }

            if (lowTemp > temp)    //Check if current value lower than min
            {
                lowTemp = temp;
                Lowest = i;
            }

            if ((time.charAt(11) == '0') && (time.charAt(12) == '0'))  //Indication of the next days data so stop while and get current days max and min
            {
                JSONObject rawArrMax = tempArrr.getJSONObject(Highest);
                JSONObject mainObjMax = rawArrMax.getJSONObject("main");
                days[0].setMax(mainObjMax.getDouble("temp"));

                JSONObject rawArrMin = tempArrr.getJSONObject(Lowest);
                JSONObject mainObjMin = rawArrMin.getJSONObject("main");
                days[0].setMin(mainObjMin.getDouble("temp"));

                carryOver = x;
                bFoundDay1 = true;
            }

            i++;
            x++;
        }


        z = 0;
        Integer l = 1;

        for (int p = carryOver; p < 32 + carryOver; p++)         //Since there will be 8 records of garunteed full 3 hour data for the next 4 days (32 records)
        {                                                        //this for loop runs from where the current days 3 hour forcast ends to the end of the 4th day
            JSONObject rawArr = tempArrr.getJSONObject(p);      // since the 5th days has a variable amount of 3 hour data
            JSONObject mainObj = rawArr.getJSONObject("main");

            temp = mainObj.getDouble("temp");

            if (p == carryOver)    //First run therefore highest = first value
            {
                highTemp = temp;
                Highest = 0;
            }

            if (highTemp < temp)    //Check if current value higher than max
            {
                highTemp = temp;
                Highest = p;
            }

            if (l % 8 == 0)               //Since we have 32 records, every 8th record will be the end of a day so the max temp is calculated
            {                             //before moving onto the next day
                JSONObject rawArrMax = tempArrr.getJSONObject(Highest);
                JSONObject mainObjMax = rawArrMax.getJSONObject("main");

                days[z].setTemp(mainObjMax.getDouble("temp"));
                Highest = 0;
                highTemp = 0.0;
                z++;                   //z will reach [3] and then the loop will end
            }
            l++;
        }

        for (int g = 0; g < 8-carryOver; g++)       //Now that the current days and next 4 days 3 hour data is saved to get the last day we start at
                                                    // record[32 + carry over] where by carry over is the amount of current day 3 hour data provided
                                                    //which is variable since if its 15:30 there will 2 more data records left (18:00 and 21:00)

                                                    //Using that calculation we know the last days data starts at record[32 + carryover]
                                                    //and is of length 8 - carryOver since there are 8 times 3 hour forecasts foe the days
        {
            JSONObject rawArr = tempArrr.getJSONObject(g+carryOver+32);
            JSONObject mainObj = rawArr.getJSONObject("main");

            temp = mainObj.getDouble("temp");

            if (g == carryOver)   //First run therefore highest = first value
            {
                highTemp = temp;
            }

            if (highTemp < temp)   //Check if current value higher than max
            {
                highTemp = temp;
                days[4].setTemp(temp);
            }

        }

        Integer k = 0;

        JSONObject rawObj2 = new JSONObject(data);
        JSONArray tempArrr2 = rawObj2.getJSONArray("list");

        for (int v = 0; 40 > v; v += 8)             //Find the start of each day and get the condition for the day
        {
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

    /**
     * Return JSON object's main
     * @param jObj
     * @return
     * @throws JSONException
     */
    private static JSONObject getObject(JSONObject jObj) throws JSONException {

        JSONObject tempObj;
        tempObj = jObj.getJSONObject("main");
        return tempObj;
    }
}

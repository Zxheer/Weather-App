package com.zxdev.app.weatherapp2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(Double.toString(-46.00),Double.toString(48), "en");

    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {

            String[] data = new String[2];
            Weather[] days = new Weather[0];
            Forecast[] days2 = new Forecast[0];
            days[0] = new Weather();
            days2[1] = new Forecast();

            Weather weather = new Weather();
            data = ((new WeatherHttpClient()).getWeatherData(params[0], params[1], params[2]));

            Log.e("TEST", data[0]+" "+data[1] );
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {

            super.onPostExecute(weather);

            String[] dayNames = new String[5];
            dayNames = getForecastDays(dayNames);
        }

        private String[] getForecastDays(String[] Days)
        {
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);

            int currentDay = cal.get(Calendar.DAY_OF_WEEK);
            int i = 0;
            int k = currentDay;

            while (k < (5 + currentDay)) {

                k++;
                Calendar nextDays = Calendar.getInstance();
                nextDays.add(Calendar.DATE, i + 1);
                Days[i] = nextDays.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                i++;
            }

            return Days;
        }

    }

}

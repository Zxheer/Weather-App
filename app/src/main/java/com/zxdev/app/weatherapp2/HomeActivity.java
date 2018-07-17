package com.zxdev.app.weatherapp2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.zxdev.app.weatherapp2.model.Weather;


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
            Weather[] days = new com.zxdev.app.weatherapp2.model.Weather[0];
            Forecast[] days2 = new Forecast[0];
            days[0] = new Weather();
            days2[1] = new Forecast();

            Weather weather = new Weather();
            data = ((new WeatherHttpClient()).getWeatherData(params[0], params[1], params[2]));

            Log.e("TEST", data[0]+" "+data[1] );
            return weather;
        }

    }

}

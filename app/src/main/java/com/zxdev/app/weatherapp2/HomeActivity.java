package com.zxdev.app.weatherapp2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.constraint.ConstraintLayout;
import android.annotation.SuppressLint;
import android.graphics.Color;


public class HomeActivity extends AppCompatActivity {

    private TextView txtCurrentTemp, txtCurrentCondition, txtCity, txtCurrentMax, txtCurrentMin, txtCurrentT, txtTemp1, txtTemp2, txtTemp3, txtTemp4, txtTemp5, txtDay1, txtDay2, txtDay3, txtDay4, txtDay5;
    private ImageView imgMain, imgDay1, imgDay2, imgDay3, imgDay4, imgDay5;
    private Forecast days[] = new Forecast[5];
    private ConstraintLayout main;

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

        @SuppressLint("SetTextI18n")
        private void forecastGUI(Weather weather, String[] dayNames) {

            ImageHelper.getImageByDescription(weather.getCondition(), imgMain);
            ImageHelper.getImageByDescription(days[0].getCondition(), imgDay1);
            ImageHelper.getImageByDescription(days[1].getCondition(), imgDay2);
            ImageHelper.getImageByDescription(days[2].getCondition(), imgDay3);
            ImageHelper.getImageByDescription(days[3].getCondition(), imgDay4);
            ImageHelper.getImageByDescription(days[4].getCondition(), imgDay5);

            if (weather.getCondition() == "Sunny")
                main.setBackgroundColor(Color.parseColor("#47AB2F"));
            else if (weather.getCondition() == "Cloudy")
                main.setBackgroundColor(Color.parseColor("#54717A"));
            else
                main.setBackgroundColor(Color.parseColor("#57575D"));


            txtCurrentTemp.setText((Math.round(weather.getTemp())) + "°");
            txtCurrentT.setText((Math.round(weather.getTemp())) + "°");
            txtCurrentMax.setText("  " + (Math.round(weather.getMaxTemp())) + "°");
            txtCurrentMin.setText(" " + (Math.round(weather.getMinTemp())) + "°");
            txtCurrentCondition.setText(weather.getCondition().toUpperCase());
            txtCity.setText(weather.getCity());
            txtTemp1.setText(" " + (Math.round(days[0].getTemp())) + "°");
            txtTemp2.setText((Math.round(days[1].getTemp())) + "°");
            txtTemp3.setText((Math.round(days[2].getTemp())) + "°");
            txtTemp4.setText((Math.round(days[3].getTemp())) + "°");
            txtTemp5.setText((Math.round(days[4].getTemp())) + "°");

            txtDay1.setText(dayNames[0]);
            txtDay2.setText(dayNames[1]);
            txtDay3.setText(dayNames[2]);
            txtDay4.setText(dayNames[3]);
            txtDay5.setText(dayNames[4]);
        }


    }

}

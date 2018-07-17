package com.zxdev.app.weatherapp2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.zxdev.app.weatherapp2.model.Weather;
import com.zxdev.app.weatherapp2.model.Forecast;
import com.zxdev.app.weatherapp2.util.*;
import com.zxdev.app.weatherapp2.network.*;


public class HomeActivity extends AppCompatActivity {

    private String lang = Locale.getDefault().toString();
    private TextView txtCurrentTemp, txtCurrentCondition, txtCity, txtCurrentMax, txtCurrentMin, txtCurrentT, txtTemp1, txtTemp2, txtTemp3, txtTemp4, txtTemp5, txtDay1, txtDay2, txtDay3, txtDay4, txtDay5;
    private ImageView imgMain, imgDay1, imgDay2, imgDay3, imgDay4, imgDay5;
    private Forecast days[] = new Forecast[5];
    private ConstraintLayout main;

    static final int MY_PERMISSIONS_ACCESS_FINE_LOCATION = 0;
    double latitude, longitude;
    boolean isConnected;
    int permissionResult;

    ConnectivityManager connManager;
    NetworkInfo netInfo;
    LocationManager locManager;
    LocationManager locManager2;
    Location location;
    Calendar cal;
    AlertDialog.Builder dialogBuilder;
    AlertDialog.Builder dialogBuilder2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        checkPermissionsAndConnectivity(permissionResult,locManager,isConnected);

    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {

            String[] data = new String[2];
            days[0] = new Forecast();
            days[1] = new Forecast();

            Weather weather = new Weather();
            data = ((new WeatherHttpClient()).getWeatherData(params[0], params[1], params[2]));

            try {
                weather = JSONParser.getWeather(data[0]);
                days = JSONParser.getForecast(data[1]);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }

        @Override
        protected void onPostExecute(Weather weather) {

            super.onPostExecute(weather);

            String[] dayNames = new String[5];
            dayNames = getForecastDays(dayNames);

            forecastGUI(weather,dayNames);
        }

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

    private void checkPermissionsAndConnectivity(int permissionResult, LocationManager locManager, boolean internetConnectivity) {
        if (permissionResult == 0)
        {
            if (locManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            {
                if(internetConnectivity)
                {
                    stimulateSomeWork();
                }
                else
                {
                    dialogBuilder.setMessage(R.string.net).setCancelable(false)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    finish();
                                }
                            });
                    AlertDialog dialog = dialogBuilder.create();
                    dialog.show();
                }
            }
            else
            {
                dialogBuilder.setMessage(R.string.gps).setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                finish();
                            }
                        });
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        } else
        {
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    init();
                }
                else
                {
                    dialogBuilder.setMessage(R.string.loc).setCancelable(false)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    finish();
                                }
                            });
                    AlertDialog dialog = dialogBuilder.create();
                    dialog.show();
                }

            }
        }
    }
    private void stimulateSomeWork()
    {
        new Handler().postDelayed(new Runnable()
        {
            public void run()
            {
                start();
            }
        }, 1000);
    }

    private void start() {

        locManager2 = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        dialogBuilder2 = new AlertDialog.Builder(HomeActivity.this);
        cal = Calendar.getInstance();

        LocationListener locationListener = new LocationListener()
        {
            @Override
            public void onLocationChanged(Location location)
            {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras)
            {

            }

            @Override
            public void onProviderEnabled(String provider)
            {

            }

            @Override
            public void onProviderDisabled(String provider)
            {
                dialogBuilder2.setMessage(R.string.net).setCancelable(false)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                finish();
                            }
                        });
                AlertDialog dialog = dialogBuilder2.create();
                dialog.show();
            }
        };

        int permissionCheck = ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck == 0)
        {
            location = locManager2.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            locManager2.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        }
        if(location != null )
        {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        }

        JSONWeatherTask task = new JSONWeatherTask();
        task.execute(Double.toString(latitude),Double.toString(longitude), lang);

    }

    private void init() {

        connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        netInfo = connManager.getActiveNetworkInfo();
        isConnected = netInfo != null && netInfo.isConnectedOrConnecting();
        locManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        permissionResult = this.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION");
        dialogBuilder = new AlertDialog.Builder(HomeActivity.this);

        imgMain = findViewById(R.id.imgMain);
        imgDay1 = findViewById(R.id.imgDay1);
        imgDay2 = findViewById(R.id.imgDay2);
        imgDay3 = findViewById(R.id.imgDay3);
        imgDay4 = findViewById(R.id.imgDay4);
        imgDay5 = findViewById(R.id.imgDay5);
        main = findViewById(R.id.main);
        txtCurrentTemp = findViewById(R.id.txtCurrentTemp);
        txtCity = findViewById(R.id.txtCity);
        txtCurrentCondition = findViewById(R.id.txtCurrentCondition);
        txtCurrentMax = findViewById(R.id.txtCurrentMax);
        txtCurrentMin = findViewById(R.id.txtCurrentMin);
        txtCurrentT = findViewById(R.id.txtCurrentT);
        txtTemp1 = findViewById(R.id.txtTemp1);
        txtTemp2 = findViewById(R.id.txtTemp2);
        txtTemp3 = findViewById(R.id.txtTemp3);
        txtTemp4 = findViewById(R.id.txtTemp4);
        txtTemp5 = findViewById(R.id.txtTemp5);
        txtDay1 = findViewById(R.id.txtDay1);
        txtDay2 = findViewById(R.id.txtDay2);
        txtDay3 = findViewById(R.id.txtDay3);
        txtDay4 = findViewById(R.id.txtDay4);
        txtDay5 = findViewById(R.id.txtDay5);
    }


}

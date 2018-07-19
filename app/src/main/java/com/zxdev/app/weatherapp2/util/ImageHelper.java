package com.zxdev.app.weatherapp2.util;

import android.support.annotation.DrawableRes;
import android.util.Log;
import android.widget.ImageView;
import com.zxdev.app.weatherapp2.R;

import com.bumptech.glide.Glide;

/**
 * @author Zaheer Ebrahim
 * SOLAR
 * 2018-07-19
 * Version 1.1.0
 */
public class ImageHelper {

    /**
     * Fucntion using the Glide libary to efficently load images
     * @param drawableName The name of the image to load
     * @param imgV The imageview to load the image in
     */
    public static void loadImage(@DrawableRes int drawableName, ImageView imgV) {

        Glide.with(imgV.getContext()).load(drawableName).asBitmap().into(imgV);
    }

    /**
     * Function to start the loading of an image to a specfic textView
     * @param condition Current weather condiditon
     * @param imgV Specific textView
     */
    public static void getImage(String condition, ImageView imgV) {

        switch (condition)
        {
            case "Cloudy":
                loadImage(R.drawable.forest_cloudy, imgV);
                break;
            case "Rainy":
                loadImage(R.drawable.forest_rainy, imgV);
                break;
            case "Sunny":
                loadImage(R.drawable.forest_sunny, imgV);
                break;
            case "Small-Cloudy":
                loadImage(R.drawable.partlysunny2x, imgV);
                break;
            case "Small-Rainy":
                loadImage(R.drawable.rain2x, imgV);
                break;
            case "Small-Sunny":
                loadImage(R.drawable.clear2x, imgV);
                break;
            default:
                Log.e("Error", "Cannot fetch image");
                break;
        }
    }
}

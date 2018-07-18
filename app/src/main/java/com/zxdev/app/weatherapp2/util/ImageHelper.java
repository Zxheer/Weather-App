package com.zxdev.app.weatherapp2.util;

import android.support.annotation.DrawableRes;
import android.util.Log;
import android.widget.ImageView;
import com.zxdev.app.weatherapp2.R;

import com.bumptech.glide.Glide;

public class ImageHelper {

    public static void loadImage(@DrawableRes int drawablId, ImageView imageView) {

        Glide.with(imageView.getContext()).load(drawablId).asBitmap().into(imageView);
    }

    public static void getImageByDescription(String description, ImageView imageView) {

        switch (description) {

            case "Cloudy":
                loadImage(R.drawable.forest_cloudy, imageView);
                break;
            case "Rainy":
                loadImage(R.drawable.forest_rainy, imageView);
                break;
            case "Sunny":
                loadImage(R.drawable.forest_sunny, imageView);
                break;
            case "Small-Cloudy":
                loadImage(R.drawable.partlysunny2x, imageView);
                break;
            case "Small-Rainy":
                loadImage(R.drawable.rain2x, imageView);
                break;
            case "Small-Sunny":
                loadImage(R.drawable.clear2x, imageView);
                break;
            default:
                Log.e("Error", "Cannot fetch image");
                break;
        }
    }
}

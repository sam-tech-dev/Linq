package com.i2e1.linq;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Linq extends Application {

    private static Linq singleton;
    
    @Override
    public void onCreate() {
        super.onCreate();
        singleton=this;

        final ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

    }

    public static Linq getInstance() {
        return singleton;
    }
}

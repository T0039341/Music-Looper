package org.music;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by gagan.utreja on 19-04-2018.
 */

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
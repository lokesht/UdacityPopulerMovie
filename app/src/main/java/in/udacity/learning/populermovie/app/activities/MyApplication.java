package in.udacity.learning.populermovie.app.activities;

import android.app.Application;
import android.content.Context;

/**
 * Created by Lokesh on 06-09-2015.
 */
public class MyApplication extends Application {

    private static MyApplication mInstance = null;

    public MyApplication() {
        mInstance = this;
    }

    public static MyApplication getInstance() {
        if (mInstance == null)
            mInstance = new MyApplication();
        return mInstance;
    }

    public Context getContext() {
        if (mInstance == null)
            mInstance = new MyApplication();
        return mInstance.getApplicationContext();

    }
}

package controllers;

import android.app.Application;
import android.content.Context;

import com.pathguide.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by AKINDE-PETERS on 6/7/2016.
 */
public class AppController extends Application {
    public static final String TAG = AppController.class
            .getSimpleName();

    Context context;
    private static AppController mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Montserrat.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }
}

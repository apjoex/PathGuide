package controllers;

import android.content.Context;
import android.os.Vibrator;

/**
 * Created by AKINDE-PETERS on 6/22/2016.
 */
public class Utilities {

    public static void vibratePhone(Context context, int time) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(time);
    }
}

package com.example.farm.myfarms.Common;

import android.location.Location;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cotam on 18.10.2018.
 */

public class Common {

    public static final String APP_ID = "aace1b31a3ae235456327d6e2b087f9b";

    public static Location current_location = null;

    public static String convertUnixToDate(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd \n EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

    public static String convertUnixToOnlyHour(long dt) {
        Date date = new Date(dt*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formatted = sdf.format(date);
        return formatted;
    }


    public static String convertUnixToHour(long index) {
        Date date = new Date(index*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm EEE MM yyyy");
        String formatted = sdf.format(date);
        return formatted;
    }

}

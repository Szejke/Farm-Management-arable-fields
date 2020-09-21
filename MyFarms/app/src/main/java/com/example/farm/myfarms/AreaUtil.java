package com.example.farm.myfarms;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cotam on 06.11.2018.
 */

public class AreaUtil {
    private static final double EARTH_DIAMETER = 12742000;// meters

    public static double calcArea(final List<LatLng> locations) {
        return calcArea(locations, EARTH_DIAMETER);
    }
    private static double calcArea(final List<LatLng> locations, final double diameter) {
        if (locations.size() < 3) {
            return 0;
        }
        final double circumference = diameter * Math.PI;
        final List<Double> coordListY = new ArrayList<Double>();
        final List<Double> coordListX = new ArrayList<Double>();
        final List<Double> listArea = new ArrayList<Double>();
        final double latiRef = locations.get(0).latitude;
        final double longiRef = locations.get(0).longitude;
        for (int i = 1; i < locations.size(); i++) {
            final double latitude = locations.get(i).latitude;
            final double longitude = locations.get(i).longitude;
            coordListY.add(calcYSegment(latiRef, latitude, circumference));
            coordListX.add(calcXSegment(longiRef, longitude, latitude, circumference));
        }
        for (int i = 1; i < coordListX.size(); i++) {
            final double x1 = coordListX.get(i - 1);
            final double y1 = coordListY.get(i - 1);
            final double x2 = coordListX.get(i);
            final double y2 = coordListY.get(i);
            listArea.add(calculateAreaHectar(x1, x2, y1, y2));
        }

        double areasSum = 0;
        for (final Double area : listArea) {
            areasSum = areasSum + area;
        }


        return Math.abs(areasSum);
    }

    private static Double calculateAreaHectar(final double x1, final double x2, final double y1, final double y2) {
        return ((y1 * x2 - x1 * y2) / 2) / 10000;
    }

    private static double calcYSegment(final double latiRef, final double latitude, final double circumference) {
        return (latitude - latiRef) * circumference / 360.0;
    }

    private static double calcXSegment(final double longiRef, final double longitude, final double latitude,
                                            final double circumference) {
        return (longitude - longiRef) * circumference * Math.cos(Math.toRadians(latitude)) / 360.0;
    }
}
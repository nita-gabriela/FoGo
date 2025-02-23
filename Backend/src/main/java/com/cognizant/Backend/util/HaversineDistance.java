package com.cognizant.Backend.util;

import com.cognizant.Backend.exception.InvalidCoordinatesException;

import static java.lang.Math.*;

public class HaversineDistance {

    public static final double EARTH_RADIUS = 6371;

    public static double calculateDistance(double latitudePoint1, double longitudePoint1,
                                           double latitudePoint2, double longitudePoint2) {

        if (latitudePoint1 < -90 || latitudePoint1 > 90 || latitudePoint2 < -90 || latitudePoint2 > 90) {
            throw new InvalidCoordinatesException("Latitude must be between -90 and 90 degrees.");
        }
        if (longitudePoint1 < -180 || longitudePoint1 > 180 || longitudePoint2 < -180 || longitudePoint2 > 180) {
            throw new InvalidCoordinatesException("Longitude must be between -180 and 180 degrees.");
        }

        double firstLatitudeInRadians = toRadians(latitudePoint1);
        double secondLatitudeInRadians = toRadians(latitudePoint2);
        double firstLongitudeInRadians = toRadians(longitudePoint1);
        double secondLongitudeInRadians = toRadians(longitudePoint2);

        double latitudeDifference = secondLatitudeInRadians - firstLatitudeInRadians;
        double longitudeDifference = secondLongitudeInRadians - firstLongitudeInRadians;

        double halfVersine = pow(sin(latitudeDifference / 2), 2) +
                pow(sin(longitudeDifference / 2), 2) * cos(firstLatitudeInRadians) * cos(secondLatitudeInRadians);

        double angularDistance = 2 * atan2(sqrt(halfVersine), sqrt(1 - halfVersine));

        return EARTH_RADIUS * angularDistance;
    }
}

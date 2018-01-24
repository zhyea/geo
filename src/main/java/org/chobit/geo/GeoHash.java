package org.chobit.geo;

import org.chobit.geo.tools.ArgsChecker;

public final class GeoHash {

    public static final int MAX_GEO_HASH_LENGTH = 12;


    public static String encodeHash(double latitude, double longitude, int length) {
        ArgsChecker.check((length > MAX_GEO_HASH_LENGTH || length < 1), "Length must be between 1 and 12.");
        ArgsChecker.check((latitude < -90D || latitude > 90D), "Latitude must be between -90 and 90.");
        ArgsChecker.check((longitude < -180D || longitude > 180D), "Longitude must be between -180 and 180.");
        return hashToString(toHashLong(latitude, longitude, length), length);
    }


    public static String encodeHash(double latitude, double longitude) {
        return encodeHash(latitude, longitude, MAX_GEO_HASH_LENGTH);
    }


    public static String encodeHash(Coordinate c, int length) {
        ArgsChecker.check(null == c, "Coordinate cannot be null.");
        return encodeHash(c.getLatitude(), c.getLongitude(), length);
    }


    public static String encodeHash(Coordinate c) {
        return encodeHash(c, MAX_GEO_HASH_LENGTH);
    }


    private static String hashToString(long hash, int length) {
        return "";
    }

    private static long toHashLong(double lat, double lng, int len) {
        long hash = 0L;
        int times = len * 5;

        double[] latb = new double[]{-90d, 90d};
        double[] lngb = new double[]{-180d, 180d};

        for (int i = 0; i < times; i++) {
            double latm = (latb[0] + latb[1]) / 2;
            double lngm = (lngb[0] + lngb[1]) / 2;

            if (i % 2 == 0) {
                if (lng <= lngm) {
                    hash = hash << 1L;
                    lngb = new double[]{lngb[0], lngm};
                } else {
                    hash = hash << 1L | 0x1;
                    lngb = new double[]{lngm, lngb[1]};
                }
            } else {
                if (lat <= latm) {
                    hash = hash << 1L;
                    latb = new double[]{latb[0], latm};
                } else {
                    hash = hash << 1L | 0x1;
                    latb = new double[]{latm, latb[1]};
                }
            }
        }
        return hash;
    }

    private GeoHash() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }

}

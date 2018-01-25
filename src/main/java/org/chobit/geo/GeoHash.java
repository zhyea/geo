package org.chobit.geo;

import org.chobit.geo.tools.ArgsChecker;

public final class GeoHash {

    public static final int MAX_GEO_HASH_LENGTH = 12;


    public static final String BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz";


    public static String encodeHash(double latitude, double longitude, int length) {
        ArgsChecker.check((length > MAX_GEO_HASH_LENGTH || length < 1), "Length must be between 1 and 12.");
        ArgsChecker.check((latitude < -90D || latitude > 90D), "Latitude must be between -90 and 90.");
        ArgsChecker.check((longitude < -180D || longitude > 180D), "Longitude must be between -180 and 180.");
        return encodeToString(encodeToLong(latitude, longitude, length), length);
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


    public static Coordinate decodeHash(String source) {
        ArgsChecker.check(null == source, "GeoHash is null.");
        int len = source.trim().length();
        ArgsChecker.check(len < 0 || len > MAX_GEO_HASH_LENGTH, "GeoHash length must be between 1 and 12.");
        long hash = decodeToLong(source.trim().toLowerCase());
        System.out.println(hash);
        return null;
    }


    private static long decodeToLong(String geoHash) {
        double minLat = -90D, maxLat = 90d;
        double minLng = -180D, maxLng = 180D;

        long hash = 0L;
        for (int i = 0; i < geoHash.length(); i++) {
            hash |= BASE32.indexOf(geoHash.charAt(i));
            hash <<= 5L;
        }
        hash >>>= 5L;
        return hash;
    }


    private static String encodeToString(long hash, int len) {
        System.out.println(Long.toBinaryString(hash));
        char[] chars = new char[len];
        while (len-- > 0) {
            chars[len] = BASE32.charAt((int) (hash & 31));
            hash >>>= 5;
        }
        return new String(chars);
    }


    private static long encodeToLong(double lat, double lng, int len) {
        long hash = 0L;
        int times = len * 5;

        double minLat = -90D, maxLat = 90d;
        double minLng = -180D, maxLng = 180D;

        for (int i = 0; i < times; i++) {
            double midLat = (minLat + maxLat) / 2;
            double midLng = (minLng + maxLng) / 2;
            hash <<= 1L;
            if (i % 2 == 0) {
                if (lng <= midLng) {
                    maxLng = midLng;
                } else {
                    hash |= 1L;
                    minLng = midLng;
                }
            } else {
                if (lat <= midLat) {
                    maxLat = midLat;
                } else {
                    hash |= 1L;
                    minLat = midLat;
                }
            }
        }
        return hash;
    }

    private GeoHash() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }

}

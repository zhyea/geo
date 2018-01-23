package org.chobit.geo;

public final class GeoHash {

    public static final int MAX_GEO_HASH_LENGTH = 12;


    public static String encodeHash(double latitude, double longitude, int length) {
        if (length > MAX_GEO_HASH_LENGTH || length < 1)
            throw new IllegalArgumentException("Length must be between 1 and 12.");
        if (latitude < -90D || latitude > 90D)
            throw new IllegalArgumentException("Latitude must be between -90 and 90.");
        if (longitude < -180D || longitude > 180D)
            throw new IllegalArgumentException("Longitude must be between -180 and 180.");


        return null;
    }


    public static String encodeHash(double latitude, double longitude) {
        return encodeHash(latitude, longitude, MAX_GEO_HASH_LENGTH);
    }


    public static String encodeHash(Coordinate c, int length) {
        return encodeHash(c.getLatitude(), c.getLongitude(), length);
    }


    public static String encodeHash(Coordinate c) {
        return encodeHash(c, MAX_GEO_HASH_LENGTH);
    }


    private static String hashToString(long hash, int length) {
        return "";
    }

    private GeoHash() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }

}

package org.chobit.geo;

import org.chobit.geo.tools.Args;

/**
 * @author robin
 */
public final class GeoHash {

    private static final int MAX_GEO_HASH_LENGTH = 12;

    private static final double MIN_LAT = -90D;
    private static final double MAX_LAT = 90D;
    private static final double MIN_LNG = -180D;
    private static final double MAX_LNG = 180D;

    private static final String BASE32 = "0123456789bcdefghjkmnpqrstuvwxyz";

    private static final int[] BITS_5 = {16, 8, 4, 2, 1};


    public static String getNeighbor(Coordinate coordinate, int length, Direction direction) {
        return getNeighbor(coordinate.getLatitude(), coordinate.getLongitude(), length, direction);
    }


    public static String getNeighbor(double latitude, double longitude, int length, Direction direction) {
        double targetLat = latitude;
        double targetLng = longitude;

        switch (direction) {
            case TOP:
                targetLng = longitude - MIN_LNG;
                break;
            case RIGHT:
                targetLat = latitude + MIN_LAT;
                break;
            case BOTTOM:
                targetLng = longitude + MIN_LNG;
                break;
            case LEFT:
                targetLat = latitude - MIN_LAT;
                break;
            default:
                throw new IllegalArgumentException("Illegal Direction:" + direction);
        }

        return encodeHash(targetLat, targetLng, length);
    }


    public static String encodeHash(double latitude, double longitude, int length) {
        Args.check((length > MAX_GEO_HASH_LENGTH || length < 1), "Length must be between 1 and 12.");
        Args.check((latitude < MIN_LAT || latitude > MAX_LAT), "Latitude must be between -90 and 90.");
        Args.check((longitude < MIN_LNG || longitude > MAX_LNG), "Longitude must be between -180 and 180.");
        return encodeToString(encodeToLong(latitude, longitude, length), length);
    }


    public static String encodeHash(double latitude, double longitude) {
        return encodeHash(latitude, longitude, MAX_GEO_HASH_LENGTH);
    }


    public static String encodeHash(Coordinate c, int length) {
        Args.check(null == c, "Coordinate cannot be null.");
        return encodeHash(c.getLatitude(), c.getLongitude(), length);
    }


    public static String encodeHash(Coordinate c) {
        return encodeHash(c, MAX_GEO_HASH_LENGTH);
    }


    public static Coordinate decodeHash(String source) {
        Args.checkNotNull(source, "GeoHash is null.");
        String geoHash = source.trim().toLowerCase();
        int len = geoHash.length();
        Args.check(len <= 0 || len > MAX_GEO_HASH_LENGTH, "GeoHash length must be between 1 and 12.");

        double minLat = MIN_LAT, maxLat = MAX_LAT;
        double minLng = MIN_LNG, maxLng = MAX_LNG;

        double midLat = (minLat + maxLat) / 2;
        double midLng = (minLng + maxLng) / 2;

        boolean isEven = true;
        for (int i = 0; i < len; i++) {
            int tmp = BASE32.indexOf(geoHash.charAt(i));
            for (int k = 0; k < 5; k++) {
                if ((tmp & BITS_5[k]) != 0) {
                    if (isEven) {
                        minLng = midLng;
                    } else {
                        minLat = midLat;
                    }
                } else {
                    if (isEven) {
                        maxLng = midLng;
                    } else {
                        maxLat = midLat;
                    }
                }

                isEven = !isEven;
                midLat = (minLat + maxLat) / 2;
                midLng = (minLng + maxLng) / 2;
            }
        }
        return new Coordinate(midLat, midLng);
    }


    private static String encodeToString(long hash, int len) {
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

        double minLat = MIN_LAT, maxLat = MAX_LAT;
        double minLng = MIN_LNG, maxLng = MAX_LNG;

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

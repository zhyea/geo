package org.chobit.geo;

public final class Coordinate {


    private final double latitude;

    private final double Longitude;

    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.Longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return Longitude;
    }


    @Override
    public String toString() {
        return "Coordinate{" +
                "latitude=" + latitude +
                ", Longitude=" + Longitude +
                '}';
    }

}

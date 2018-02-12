package org.chobit.geo;

import junit.framework.TestCase;

public class GeoHashTest extends TestCase {


    public void testEncodeHash() {
        double lat = 1.2;
        double lng = 1.3;
        int len = 8;
        String geoHash = GeoHash.encodeHash(lat, lng, len);
        assertEquals("s00yvd6w", geoHash);
    }


    public void testDecodeHash() {
        String geoHash = "s00yvd6w";
        Coordinate c = GeoHash.decodeHash(geoHash);
        assertEquals(new Coordinate(1.1999988555908203, 1.2999916076660156), c);
        System.out.println(c);
    }

}

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
        GeoHash.decodeHash(geoHash);
    }

}

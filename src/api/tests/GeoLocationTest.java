package api.tests;
//junit 5.4

import api.GeoLocation;

import static org.junit.jupiter.api.Assertions.*;

class GeoLocationTest {

    @org.junit.jupiter.api.Test
    void distance() {
        GeoLocation g1 = new GeoLocation(1, 1, 1);
        GeoLocation g2 = new GeoLocation(2, 2, 2);
        GeoLocation g3 = new GeoLocation(0, 0, 0);
        GeoLocation g4 = new GeoLocation(0, 0, 0);
        double dis=g1.distance(g2);
        double excpected=1.7320508075688772;
        System.out.println(dis);
        assertEquals(excpected,dis);
    }
}
package cz.java.vse.tymovaprace.vanv08.Logika;

import org.junit.Test;

import static org.junit.Assert.*;

public class UkolTest {
    Ukol ukol = new Ukol("UkolName","Ukol popis","19.01.2021","13.01.2021");



    @Test
    public void getBodyTest(){
        ukol.getPointsUpdate();
        assertEquals(1,ukol.getPoints());
    }

    @Test
    public void getAssignedDate() {
        assertEquals("13.01.2021",ukol.getAssignedDate());
    }
}
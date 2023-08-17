package cz.java.vse.tymovaprace.vanv08.Logika;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SlozkaTest {
    List<Slozka> seznamSlozek = new ArrayList<>();
    User user = new User("username", seznamSlozek);
    Ukol ukol = new Ukol("ukolName", "ukolPopis", "ukolDueDate", "28.11.2021");
    Ukol ukol2 = new Ukol("ukolName2", "ukolPopis2", "ukolDueDate", "28.11.2021");
    List<Ukol> seznamUkolu = new ArrayList<>();
    List<Ukol> seznamUkoluDone = new ArrayList<>();
    String nameslozky = "slozka";
    Slozka slozka = new Slozka(nameslozky, seznamUkolu, seznamUkoluDone);


    @Test
    public void pridatUkol() {
        slozka.pridatUkol(ukol);
        assertEquals(false, slozka.slozkaUndoneEmpty());

    }

    @Test
    public void odeberUkol() {
        slozka.pridatUkol(ukol);
        slozka.pridatUkol(ukol2);
        slozka.odeberUkol(ukol);
        assertEquals(1, slozka.getSize());
    }

    @Test
    public void pridatUkolDone() {
        slozka.pridatUkolDone(ukol);
        assertEquals(false, slozka.slozkaDoneEmpty());
    }

    @Test
    public void odeberUkolDone() {
        slozka.pridatUkolDone(ukol2);
        slozka.odeberUkolDone(ukol2);
        assertEquals(true, slozka.slozkaDoneEmpty());

    }
}
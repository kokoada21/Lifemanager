package cz.java.vse.tymovaprace.vanv08.Logika;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class UserTest {
    List<Slozka> seznamSlozek = new ArrayList<>();
    User user = new User("username", seznamSlozek);
    List<Ukol> seznamUkolu = new ArrayList<>();
    List<Ukol> seznamUkoluDone = new ArrayList<>();
    String nameslozky = "slozka";
    Slozka slozka = new Slozka(nameslozky, seznamUkolu, seznamUkoluDone);
    Slozka slozka2 = new Slozka(nameslozky, seznamUkolu, seznamUkoluDone);
    Ukol ukol = new Ukol("ukolName", "ukolPopis", "ukolDueDate", "28.11.2021");
    Ukol ukol2 = new Ukol("ukolName", "ukolPopis", "ukolDueDate", "28.11.2021");

    @Test
    public void vytvorSlozka() throws IOException {
        user.vytvorSlozka(slozka.getFolderName());
        assertEquals(false,user.listSlozekIsEmpty());
        //
    }

    @Test
    public void smazSlozka() throws IOException {
        user.vytvorSlozka(slozka.getFolderName());
        user.vytvorSlozka(slozka2.getFolderName());
        user.smazSlozka(slozka2);
        assertEquals(1, seznamSlozek.size());
        //
    }

    @Test
    public void dueDateformat() {
        assertEquals(false, user.dueDateformat("20-02-2021"));
        assertEquals(true, user.dueDateformat("20.11.2021"));
    }

}
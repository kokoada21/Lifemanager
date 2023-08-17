package cz.java.vse.tymovaprace.vanv08.Logika;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *  Třída User - obsahuje metody pro manipulaci sle složkami a úkoly
 *
 *  Tato třída je součástí aplikace LifeManager
 *
 *@author     Van Nguyen, Jan Sikora, Adam Ješina
 *@version    1.0.0.
 *@created    leden 2021
 */
public class User{
    private String username;
    private int userPoints;
    private final List<Slozka> seznamSlozek;


    public User(String username, List<Slozka> seznamSlozek) {
        this.seznamSlozek = seznamSlozek;
        this.username = username;
        this.userPoints = userPoints;

    }

    /**
     * metoda pro ukladani dat v .json
     *
     * @param user
     * @throws IOException
     */
    public void ulozit(User user) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileWriter writer = new FileWriter("src/main/resources/ukoly.json");
        gson.toJson(this, writer);
        writer.close();
    }

    public List<Slozka> getSeznamSlozek() {
        return this.seznamSlozek;
    }

    /**
     * metoda pro vyhledavani urcite slozky
     * @param slozka
     * @return hledena slozka
     */
    public Slozka getSlozka(Slozka slozka) {
        Slozka nalezenaSlozka = null;
        for (Slozka slozenka : seznamSlozek) {
            if (slozenka.getFolderName().equals(slozka.getFolderName())) {
                nalezenaSlozka = slozenka;
            }
        }
        return nalezenaSlozka;
    }

    /**
     * Metoda pro vytvoreni slozky
     *
     * @throws IOException
     */
    public void vytvorSlozka(String nameslozky) throws IOException {
        List<Ukol> ukoly = new ArrayList<>();
        List<Ukol> ukolyDone = new ArrayList<>();
        Slozka slozka = new Slozka(nameslozky, ukoly, ukolyDone);
        List<Slozka> listSlozek = this.getSeznamSlozek();
        listSlozek.add(slozka);
        ulozit(this);
    }

    /**
     * metoda pro smazani slozky
     *
     * @throws IOException
     */
    public void smazSlozka(Slozka slozka) throws IOException {
        List<Slozka> listSlozek = this.getSeznamSlozek();
        for (Slozka slozka2 : listSlozek) {
            if (slozka2.getFolderName().equals(slozka.getFolderName())) {
                listSlozek.remove(slozka2);
                break;
            }
        }
        ulozit(this);
    }

    /**
     * metoda pro vytvoreni ukolu v specificke slozce
     *
     * @throws IOException
     */
    public void vytvorUkol(String name, String popis, String dueDate, Slozka nazevSlozky) throws IOException {
        Ukol ukolGeneral = new Ukol();
        Ukol ukol = new Ukol(name, popis, dueDate, ukolGeneral.getActualDate());
        nazevSlozky.pridatUkol(ukol);

        ulozit(this);

    }
    /**
     * metoda pro kontrolu formatu datumu - pouze ve formatu: dd-MM-yyyy HH:mm
     * @return true - pokud je format je v poradku false pokud je spatny format
     */
    public boolean dueDateformat(String dueDate) {
        SimpleDateFormat formatter6 = new SimpleDateFormat("dd.MM.yyyy");
        boolean konec = false;
        while (!konec) {
            try {
                Date date = formatter6.parse(dueDate);
                konec = true;
            } catch (ParseException e) {
                break; // zachrana brzda
            }
        }
        return konec;
    }

    /**
     * metoda pro update bodu ukolu dle datumu
     *
     * @throws IOException
     */
    public void updatePoints() throws IOException {
        List<Slozka> listSlozek = this.getSeznamSlozek();


        for (Slozka S : listSlozek) {
            List<Ukol> listUkolu = S.getSeznamUkolu();
            for (Ukol U : listUkolu) {
                U.getPointsUpdate();
            }
        }
        ulozit(this);
    }

    /**
     * metoda pro smazani ukolu ve specificke slozce
     *
     * @throws IOException
     */
    public void smazUkol(Ukol ukol) throws IOException {
        List<Slozka> listSlozek = this.getSeznamSlozek();

        for (Slozka S : listSlozek) {
            List<Ukol> listUkolu = S.getSeznamUkolu();
            for (Ukol U : listUkolu) {
                if (U.getName().equals(ukol.getName())) {
                    S.odeberUkol(U);
                    System.out.println("Ukol odebran!");
                    break;
                }
            }
            List<Ukol> listUkoluDone = S.getSeznamUkoluDone();
            for (Ukol U : listUkoluDone) {
                if (U.getName().equals(ukol.getName())) {
                    S.odeberUkolDone(U);
                    System.out.println("Ukol odebran!");
                    break;
                }
            }
        }

        ulozit(this);
    }

    /**
     * metoda pro optani jmena uzivatele
     *
     * @throws IOException
     */
    public void askUsername(String username) throws IOException {
        setUsername(username);
        ulozit(this);
    }

    /**
     * metoda pro odskrtnuti ukolu, jako ukol hotovy
     *
     * @throws IOException
     */
    public void ukolDone(Ukol ukol) throws IOException {

        List<Slozka> listSlozek = this.getSeznamSlozek();

        for (Slozka S : listSlozek) {
            List<Ukol> listUkolu = S.getSeznamUkolu();
            for (Ukol U : listUkolu) {
                if (U.getName().equals(ukol.getName())) {
                    S.odeberUkol(U);
                    S.pridatUkolDone(U);
                    setUserPoints(userPoints += U.getPoints());
                    break;
                }
            }
        }
        ulozit(this);
    }

    /**
     * metoda pro navraceni ukolu mezi ukoly nedodelane
     *
     * @throws IOException
     */
    public void ukolUndone(Ukol ukol) throws IOException {

        List<Slozka> listSlozek = this.getSeznamSlozek();

        for (Slozka S : listSlozek) {
            List<Ukol> listDoneUkolu = S.getSeznamUkoluDone();
            for (Ukol U : listDoneUkolu) {
                if (U.getName().equals(ukol.getName())) {
                    S.odeberUkolDone(U);
                    S.pridatUkol(U);
                    setUserPoints(userPoints -= U.getPoints());
                    break;
                }
            }
        }
        ulozit(this);
    }

    /**
     * metoda pro editaci ukolu, jeho jmena, popisu a dueDate
     *
     * @throws IOException
     */
    public void editUkol(String name, String popis, String dueDate, String assignedDate, Slozka slozka, String xName) throws IOException {
        Ukol ukolNew = new Ukol(name, popis, dueDate, assignedDate);
        List<Slozka> listSlozek = this.getSeznamSlozek();
        for (Slozka S : listSlozek) {
            List<Ukol> listUkolu = S.getSeznamUkolu();
            for (Ukol U : listUkolu) {
                if (U.getName().equals(xName)) {
                    S.odeberUkol(U);
                    break;
                }
            }
        }
        for(Slozka AY: listSlozek){
            if(AY.getFolderName().equals(slozka.getFolderName())){
                AY.pridatUkol(ukolNew);
                ulozit(this);
            }
        }
    }

    /**
     * staticky list leaderboard
     * @return leaderboard s pozici uzivatele
     * @throws FileNotFoundException
     */
    public List<String> ukazLeaderboard() throws FileNotFoundException {
        ObservableList<UserDummy> leaderboard = FXCollections.observableArrayList();
        ListView<UserDummy> stringListView = new ListView<>(leaderboard);
        List<String> listClean = new ArrayList<>();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader reader = new FileReader("src/main/resources/ukoly.json");
        JsonReader readerJson = new JsonReader(reader);
        User save = gson.fromJson(readerJson, User.class);

        leaderboard.add(new UserDummy("HulMiHo Ukolen", 5));
        leaderboard.add(new UserDummy("Lucka Kopretinova", 69));
        leaderboard.add(new UserDummy("Jarda Kral", 420));
        leaderboard.add(new UserDummy("Venca Sotonu", 100));
        leaderboard.add(new UserDummy("Vojtech Slavik", 210));
        leaderboard.add(new UserDummy("Ing. Filip Vencovsky",32767));
        leaderboard.add(new UserDummy("Johnny Sins", 122));
        leaderboard.add(new UserDummy("Tonda Nguyen", 99));
        leaderboard.add(new UserDummy("Fidas Peska", 432));
        leaderboard.add(new UserDummy(save.getUsername(),save.getUserPoints()));


        Collections.sort(leaderboard);
        for(UserDummy s: leaderboard){
            listClean.add(s.getName()+ " " + s.getPoints());
        }
        return listClean;
    }
    /**
     * Metoda pro resetovani dat v aplikaci
     * @throws IOException
     */
    public void reset() throws IOException {
        setUsername(null);
        setUserPoints(0);
        List<Slozka> listSlozek = this.getSeznamSlozek();
        listSlozek.clear();
        ulozit(this);
    }

    public boolean  listSlozekIsEmpty(){
        return seznamSlozek.isEmpty();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserPoints() {
        return userPoints;
    }

    public void setUserPoints(int userPoints) {
        this.userPoints = userPoints;
    }

    @Override
    public String toString() {
        return "User{" +
                "username:" + username +
                "seznamSlozek=" + seznamSlozek +
                "seznamSlozekDone=" + seznamSlozek +
                '}';
    }

    }


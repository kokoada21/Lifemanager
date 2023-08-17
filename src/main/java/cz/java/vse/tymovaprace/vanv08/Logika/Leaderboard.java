package cz.java.vse.tymovaprace.vanv08.Logika;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *  Třída LeaderBoard - obsahuje metody pro vytvoření statického leaderboardu
 *
 *  Obsahuje metodu pro střídění leaderbordu sestupně dle počtu bodů
 *
 *  Tato třída je součástí aplikace LifeManager
 *
 *@author     Van Nguyen, Jan Sikora, Adam Ješina
 *@version    1.0.0.
 *@created    leden 2021
 */
public class Leaderboard {
    private List<UserDummy> leaderboard = new ArrayList<>();


    public Leaderboard(List<UserDummy> leaderboard) throws FileNotFoundException {
        this.leaderboard = leaderboard;
    }


    /**
     * metoda pro serazeni leaderboardu dle hodnoty points
     * @throws FileNotFoundException
     */
    public void sortAscendingPoints() throws FileNotFoundException {
        List<UserDummy> leaderboard = new ArrayList<>();

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
            System.out.println(s);
        }
    }

}

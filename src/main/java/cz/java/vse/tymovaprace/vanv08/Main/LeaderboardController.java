package cz.java.vse.tymovaprace.vanv08.Main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import cz.java.vse.tymovaprace.vanv08.Logika.Leaderboard;
import cz.java.vse.tymovaprace.vanv08.Logika.User;
import cz.java.vse.tymovaprace.vanv08.Logika.UserDummy;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;


/**
 *  Třída LedaerboardController
 *
 *  Tato třída je součástí aplikace LifeManager
 *
 *@author     Van Nguyen, Jan Sikora, Adam Ješina
 *@version    1.0.0.
 *@created    leden 2021
 */
public class LeaderboardController {
    @FXML private ListView leaderboardView;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    FileReader reader = new FileReader("src/main/resources/ukoly.json");
    JsonReader readerJson = new JsonReader(reader);
    User save = gson.fromJson(readerJson, User.class);

    public LeaderboardController() throws FileNotFoundException {
    }


    @FXML
    private void initialize() throws FileNotFoundException {
        leaderboardView.getItems().addAll(save.ukazLeaderboard());
    }

}



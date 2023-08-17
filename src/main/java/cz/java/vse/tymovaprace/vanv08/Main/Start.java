package cz.java.vse.tymovaprace.vanv08.Main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import cz.java.vse.tymovaprace.vanv08.Logika.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 *  Třída Start
 *
 *  Obsahuje hlavní metodu pro spuštění aplikace
 *
 *  Tato třída je součástí aplikace LifeManager
 *
 *@author     Van Nguyen, Jan Sikora, Adam Ješina
 *@version    1.0.0.
 *@created    leden 2021
 */

public class Start extends Application {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    FileReader reader = new FileReader("src/main/resources/ukoly.json");
    JsonReader readerJson = new JsonReader(reader);
    User save = gson.fromJson(readerJson, User.class);

    public Start() throws FileNotFoundException {
    }

    public static void main(String [] args) throws IOException {
        launch();
    }



    @Override
    public void start(Stage stage) throws Exception {
        if(save.getUsername() == null){
            Stage askUsername = new Stage();
            GridPane vytvorSlozka = FXMLLoader.load(getClass().getResource("/askusername.fxml"));
            askUsername.setScene(new Scene(vytvorSlozka));
            askUsername.setResizable(false);
            askUsername.showAndWait();
        }
        GridPane home = FXMLLoader.load(getClass().getResource("/home.fxml"));
        stage.setScene(new Scene(home));
        stage.setResizable(true);
        stage.show();
    }

}

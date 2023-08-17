package cz.java.vse.tymovaprace.vanv08.Main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import cz.java.vse.tymovaprace.vanv08.Logika.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *  Třída AskUsernnameController
 *
 *  Controller spojený s fxml souborem askusername
 *
 *  Tato třída je součástí aplikace LifeManager
 *
 *@author     Van Nguyen, Jan Sikora, Adam Ješina
 *@version    1.0.0.
 *@created    leden 2021
 */
public class AskUsernameController {
    @FXML private Button confirmButton;
    @FXML private  TextField inputUsername;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    FileReader reader = new FileReader("src/main/resources/ukoly.json");
    JsonReader readerJson = new JsonReader(reader);
    User save = gson.fromJson(readerJson, User.class);

    public AskUsernameController() throws FileNotFoundException {
    }

    public void zpracujVstup(ActionEvent actionEvent) throws IOException {
        String prikaz = inputUsername.getText();
        save.askUsername(prikaz);
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }
}

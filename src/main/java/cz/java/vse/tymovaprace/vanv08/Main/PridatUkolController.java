package cz.java.vse.tymovaprace.vanv08.Main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import cz.java.vse.tymovaprace.vanv08.Logika.Slozka;
import cz.java.vse.tymovaprace.vanv08.Logika.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *  Třída PridatUkolController
 *
 *  Tato třída je součástí aplikace LifeManager
 *
 *@author     Van Nguyen, Jan Sikora, Adam Ješina
 *@version    1.0.0.
 *@created    leden 2021
 */
public class PridatUkolController {
    @FXML private Button cancelButton;
    @FXML private Button confirmButton;
    @FXML private TextField nazevUkol;
    @FXML private TextArea popisUkol;
    @FXML private TextField dueDateUkol;
    @FXML private ChoiceBox destinationSlozka;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    FileReader reader = new FileReader("src/main/resources/ukoly.json");
    JsonReader readerJson = new JsonReader(reader);
    User save = gson.fromJson(readerJson, User.class);

    public PridatUkolController() throws FileNotFoundException {
    }
    @FXML
    public void initialize(){
        destinationSlozka.getItems().addAll(save.getSeznamSlozek());
        destinationSlozka.getSelectionModel().select(0);
    }
    public void confirmUkol(MouseEvent mouseEvent) throws IOException {
        String name = nazevUkol.getText();
        String popis = popisUkol.getText();
        String dueDate = dueDateUkol.getText();
        if(save.dueDateformat(dueDateUkol.getText())){
            Slozka destination = (Slozka) destinationSlozka.getValue();
            System.out.println(destination);
            save.vytvorUkol(name,popis,dueDate,destination);

            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        }
            else{
            Alert alert = new Alert(Alert.AlertType.WARNING,"SPATNEJ FORMAT. Prosim zadejte datum ve formatu: dd.mm.yyyy");
            alert.showAndWait();
        }
    }
    public void zavritOkno(MouseEvent mouseEvent){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}

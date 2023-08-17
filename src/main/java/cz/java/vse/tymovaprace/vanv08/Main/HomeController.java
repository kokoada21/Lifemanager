package cz.java.vse.tymovaprace.vanv08.Main;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import cz.java.vse.tymovaprace.vanv08.Logika.Slozka;
import cz.java.vse.tymovaprace.vanv08.Logika.Ukol;
import cz.java.vse.tymovaprace.vanv08.Logika.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
/**
 *  Třída HomeController - obsahuje metody pro manipulaci s hlavním grafickým oknem aplikace
 *
 *  Controller
 *
 *  Tato třída je součástí aplikace LifeManager
 *
 *@author     Van Nguyen, Jan Sikora, Adam Ješina
 *@version    1.0.0.
 *@created    leden 2021
 */
public class HomeController extends GridPane{
    @FXML private ScrollPane doneScrollPane;
    @FXML private ScrollPane undoneScrollPane;
    @FXML private Button resetButton;
    @FXML private Button leaderboardButton;
    @FXML private Button buttonDelteSlozka;
    @FXML private Text folderNameField;
    @FXML private Button buttonAddUkol;
    @FXML private VBox listViewTasksDone;
    @FXML private VBox listViewTasks;
    @FXML private Text userBody;
    @FXML private Text userName;
    @FXML private Button confirmButton;
    @FXML private ListView seznamSlozek;
    @FXML private Button buttonAddSlozka;
    @FXML private TextField inputNazevSlozky;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    FileReader reader = new FileReader("src/main/resources/ukoly.json");
    JsonReader readerJson = new JsonReader(reader);
    User save = gson.fromJson(readerJson, User.class);

    public HomeController() throws FileNotFoundException {
    }


    @FXML
    private void initialize() throws IOException {
        save.updatePoints();
        seznamSlozek.getItems().addAll(save.getSeznamSlozek()); // aktualni seznam slozek
        userName.setText(save.getUsername());
        userBody.setText(""+save.getUserPoints());
        seznamSlozek.getSelectionModel().select(0);



    }

    /**
     * Vytváří objekty pro nedokončené a dokončené úkoly
     * @param slozka odkaz na slozku, ve které úkoly vytváříme
     * @throws FileNotFoundException
     */
    public void refreshUkoly(Slozka slozka) throws FileNotFoundException {
            listViewTasks.getChildren().clear();
            listViewTasksDone.getChildren().clear();
            List<Ukol> aktualniUkoly = nactiAktualiUser().getSlozka(slozka).getSeznamUkolu();
            List<Ukol> aktualniUkolyDone = nactiAktualiUser().getSlozka(slozka).getSeznamUkoluDone();
            for (Ukol ukol : aktualniUkoly) {
                String nazev = ukol.getName();
                String popis = ukol.getPopis();
                String dueDate = ukol.getDueDate();
                String points = ""+ukol.getPoints();
                Text textNazev = new Text(nazev);
                Text textPopis = new Text(popis);
                Text otherInfo = new Text("Datum splnění: "+ dueDate + " Body: "+ points);
                textNazev.setId("textNazev");
                VBox information = new VBox();
                information.setId("infoTask");
                Button deleteButton = new Button("delete");
                Button editButton = new Button("edit");
                Button doneButton = new Button("done");
                doneButton.setId("buttonStyly");
                deleteButton.setId("buttonStyly");
                editButton.setId("buttonStyly");
                HBox buttonsBox = new HBox();
                buttonsBox.setId("taskButtonsBox");
                buttonsBox.getChildren().add(doneButton);
                buttonsBox.getChildren().add(editButton);
                buttonsBox.getChildren().add(deleteButton);
                editButton.setOnAction(event -> {
                    VBox editBox = new VBox();
                    editBox.getStylesheets().add(getClass().getResource("/styly.css").toExternalForm());
                    editBox.setId("editBox");
                    Stage stage2 = new Stage();
                    Text nadpis = new Text("Edit úkolu");
                    TextField nazevUkol = new TextField(ukol.getName());
                    String xName = ukol.getName();
                    TextField popisUkol = new TextField(ukol.getPopis());
                    TextField dueDateUkol = new TextField(ukol.getDueDate());
                    TextField assignedDateUkol = new TextField(ukol.getAssignedDate());
                    Button confirm = new Button("Confirm");
                    ChoiceBox destinationSlozka = new ChoiceBox();
                    try {
                        destinationSlozka.getItems().addAll(nactiAktualiUser().getSeznamSlozek());
                        destinationSlozka.getSelectionModel().select(0);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    confirm.setOnAction(ev -> {
                        try {

                            if (save.dueDateformat(dueDateUkol.getText())) {
                                Slozka destination = (Slozka) destinationSlozka.getValue();
                                nactiAktualiUser().editUkol(nazevUkol.getText(),popisUkol.getText(),dueDateUkol.getText(),assignedDateUkol.getText(),destination,xName);
                            }
                            else{
                                Alert alert = new Alert(Alert.AlertType.WARNING,"SPATNEJ FORMAT. Prosim zadejte datum ve formatu: dd.mm.yyyy");
                                alert.show();

                            }
                            update();
                            refreshUkoly(slozka);
                            stage2.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    editBox.getChildren().addAll(nadpis,nazevUkol,popisUkol,dueDateUkol,destinationSlozka,confirm);
                    stage2.setScene(new Scene(editBox));
                    stage2.showAndWait();

                });
                doneButton.setOnAction(event -> {
                    try {
                        nactiAktualiUser().ukolDone(ukol);
                        refreshUkoly(slozka);
                        userBody.setText(""+nactiAktualiUser().getUserPoints());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                deleteButton.setOnAction(e -> {
                    try {
                        nactiAktualiUser().smazUkol(ukol);
                        refreshUkoly(slozka);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                });
                information.getChildren().addAll(textNazev,textPopis,otherInfo, buttonsBox);
                listViewTasks.getChildren().addAll(information);

        }
        for (Ukol ukol : aktualniUkolyDone) {
            String nazev = ukol.getName();
            String popis = ukol.getPopis();
            String dueDate = ukol.getDueDate();
            String points = ""+ukol.getPoints();
            Text textNazev = new Text(nazev);
            Text textPopis = new Text(popis);
            textNazev.setId("textNazev");
            VBox information = new VBox();
            information.setId("infoTasksDone");

            Button deleteButton = new Button("delete");
            Button unDoneButton = new Button("Undone");
            deleteButton.setId("doneButtonStyly");
            unDoneButton.setId("doneButtonStyly");
            HBox buttons = new HBox();
            buttons.setSpacing(5);
            buttons.getChildren().add(unDoneButton);
            buttons.getChildren().add(deleteButton);
            unDoneButton.setOnAction(event -> {
                        try {
                            nactiAktualiUser().ukolUndone(ukol);
                            refreshUkoly(slozka);
                            userBody.setText(""+nactiAktualiUser().getUserPoints());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            deleteButton.setOnAction(e -> {
                try {
                    nactiAktualiUser().smazUkol(ukol);
                    refreshUkoly(slozka);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            });

            information.getChildren().addAll(textNazev,textPopis, buttons);
            listViewTasksDone.getChildren().addAll(information);


        }
    }

    public void vybratSlozka(MouseEvent mouseEvent) throws FileNotFoundException {
        Slozka slozka = (Slozka) seznamSlozek.getSelectionModel().getSelectedItem();
        if(slozka != null){
            refreshUkoly(slozka);
            }

        folderNameField.setText(slozka.getFolderName());

    }


    public void buttonAddSlozka(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        GridPane vytvorSlozka = FXMLLoader.load(getClass().getResource("/vytvorslozka.fxml"));
        stage.setScene(new Scene(vytvorSlozka));
        stage.setResizable(false);
        stage.showAndWait();
        update();

        
    }

    public void update() throws FileNotFoundException {
        seznamSlozek.getItems().clear();
        seznamSlozek.getItems().addAll(nactiAktualiUser().getSeznamSlozek());

    }


    public User nactiAktualiUser() throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        FileReader reader = new FileReader("src/main/resources/ukoly.json");
        JsonReader readerJson = new JsonReader(reader);
        User save = gson.fromJson(readerJson, User.class);
        return save;
    }


    public void showLeaderboard(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        GridPane leaderboard = FXMLLoader.load(getClass().getResource("/leaderboard.fxml"));
        stage.setScene(new Scene(leaderboard));
        stage.setResizable(false);
        stage.showAndWait();
        update();
    }

    public void pridatUkol(MouseEvent mouseEvent) throws IOException {
        if(seznamSlozek.getItems().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Nejprve vytvorte slozku.",ButtonType.OK);
            alert.showAndWait();
        }
        else {
            Stage stage = new Stage();
            GridPane vytvorUkol = FXMLLoader.load(getClass().getResource("/vytvorukol.fxml"));
            stage.setScene(new Scene(vytvorUkol));
            stage.setResizable(false);
            stage.showAndWait();
            update();
        }
    }

    public void buttonDeleteSlozka(ActionEvent actionEvent) throws IOException {
        Slozka slozka = (Slozka) seznamSlozek.getSelectionModel().getSelectedItem();
        if(slozka!=null) {
            nactiAktualiUser().smazSlozka(slozka);
            listViewTasks.getChildren().clear();
            listViewTasksDone.getChildren().clear();
            folderNameField.setText("");
            update();
        }

    }

    public void resetApp(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"YOU SURE BRAH?",ButtonType.YES,ButtonType.CANCEL);
        alert.showAndWait();
        if(alert.getResult()== ButtonType.YES){
            nactiAktualiUser().reset();
            Stage stage = (Stage) resetButton.getScene().getWindow();
            stage.close();
            Stage askUsername = new Stage();
            GridPane vytvorSlozka = FXMLLoader.load(getClass().getResource("/askusername.fxml"));
            askUsername.setScene(new Scene(vytvorSlozka));
            askUsername.setResizable(false);
            askUsername.showAndWait();
            GridPane home = FXMLLoader.load(getClass().getResource("/home.fxml"));
            stage.setScene(new Scene(home));
            stage.setResizable(true);
            stage.show();
        }
    }
}

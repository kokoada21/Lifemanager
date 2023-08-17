package cz.java.vse.tymovaprace.vanv08.Logika;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 *  Třída Ukol - obsahuje základni metody pro práci s atributy složek
 *
 *  Tato třída je součástí aplikace LifeManager
 *
 *@author     Van Nguyen, Jan Sikora, Adam Ješina
 *@version    1.0.0.
 *@created    leden 2021
 */
public class Ukol{

    private String name;
    private String popis;
    private String dueDate;
    private String assignedDate;
    private int body;

    public Ukol(){

    }

    public Ukol(String name,String popis, String dueDate, String assignedDate){
        this.name = name;
        this.popis = popis;
        this.dueDate = dueDate;
        this.assignedDate = assignedDate;
        this.body = 3;
    }




    @Override
    public String toString() {
        return name + " "+ popis +" " + dueDate +" "+assignedDate +" "+ body;

    }

    /**
     * formatovani local casu na pozadovany format
     * @return datum v pozadovanym formatu
     */
    public String getActualDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * metoda pro updatovani za kolik je ukol hodnocen
     */
    public void getPointsUpdate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        LocalDate assignedDateTypeDate = LocalDate.parse(getAssignedDate(),dtf);
        LocalDateTime now = LocalDateTime.now();
        LocalDate actualDateTypeDate = LocalDate.parse(dtf.format(now),dtf);

        LocalDate dueDateTypeDate = LocalDate.parse(getDueDate(),dtf);


        long elapsedDays = ChronoUnit.DAYS.between(assignedDateTypeDate,dueDateTypeDate); // celkovy cas
        long elapsedDaysActual = ChronoUnit.DAYS.between(actualDateTypeDate,dueDateTypeDate); // cas od actual a duedate



        double oneThirdTime = elapsedDays/3; // tretina celkoveho casu

        if(elapsedDaysActual<=oneThirdTime){
            setBody(1);
        }
        else if(elapsedDaysActual<=oneThirdTime*2){
            setBody(2);
        }
        else{
            setBody(3);
        }
    }



    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDueDate() {

        return dueDate;
    }

    public String getPopis() {
        return popis;
    }

    public int getPoints() {
        return body;
    }

    public void setDueDate(String dueDate) {

        this.dueDate = dueDate;
    }

    public void setPopis(String popis) {
        this.popis = popis;
    }

    public void setAssignedDate(String assignedDate) {
        this.assignedDate = assignedDate;
    }

    public String getAssignedDate() {
        return assignedDate;
    }
    public void setBody(int body) {
        this.body = body;
    }


}



package cz.java.vse.tymovaprace.vanv08.Logika;

import java.util.*;

/**
 *  Třída Slozka - obsahuje metody pro manipulaci atributů složek.
 *
 *  Tato třída je součástí aplikace LifeManager
 *
 *@author     Van Nguyen, Jan Sikora, Adam Ješina
 *@version    1.0.0.
 *@created    leden 2021
 */
public class Slozka {
    private String folderName;
    private List<Ukol> seznamUkolu;
    private List<Ukol> seznamUkoluDone;


    public Slozka(String folderName, List<Ukol> seznamUkolu,List<Ukol> seznamUkoluDone){
    this.folderName = folderName;
    this.seznamUkolu = seznamUkolu;
    this.seznamUkoluDone = seznamUkoluDone;
}

    /**
     * metoda pro pridani ukolu do seznamu NEdodelanych ukolu
     * @param ukol
     */
    public void pridatUkol(Ukol ukol){
    seznamUkolu.add(ukol);
}

    /**
     * metoda pro odebrani ukolu z NEdodelanych ukolu
     * @param ukol
     */
    public void odeberUkol(Ukol ukol) {seznamUkolu.remove(ukol); }

    /**
     * metoda pro pridani ukolu do DOdelanych ukoly
     * @param ukol
     */
    public void pridatUkolDone(Ukol ukol) {seznamUkoluDone.add(ukol);}

    /**
     * metoda pro odebrani ukolu z listu DOdelanych ukolu
     * @param ukol
     */
    public void odeberUkolDone(Ukol ukol) {seznamUkoluDone.remove(ukol); }

    public String seznamVeci(){
    String seznam = new String();
    for (Ukol ukol: seznamUkolu){
        seznam+= ukol.getDueDate();
    }
    return seznam;
}
    public boolean slozkaUndoneEmpty() {
        return seznamUkolu.isEmpty();
    }
    public boolean slozkaDoneEmpty(){
        return seznamUkoluDone.isEmpty();
    }

    public String getFolderName() {
    return folderName;
}

    public List<Ukol> getSeznamUkoluDone() {
        return seznamUkoluDone;
    }


    public List<Ukol> getSeznamUkolu(){
    return seznamUkolu;
}

    public int getSize(){
       return seznamUkolu.size();
    }

@Override
public String toString() {
        return getFolderName();
    }

}
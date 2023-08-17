package cz.java.vse.tymovaprace.vanv08.Logika;

/**
 *  Třída UserDummy - obsahuje metody pro manipulaci s atributy objektu UserDummy
 *
 *  UserDummy je virtuální statický uživatel aplikace
 *  UserDummy je používán se zobrazuje v leaderboradu
 *
 *  Tato třída je součástí aplikace LifeManager
 *
 *@author     Van Nguyen, Jan Sikora, Adam Ješina
 *@version    1.0.0.
 *@created    leden 2021
 */
public class UserDummy implements Comparable<UserDummy> {
    private String name;
    private int points;

    public UserDummy(String name, int points) {
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return
                "Name='" + name + '\'' +
                ", points=" + points +
                '}';
    }

    @Override
    public int compareTo(UserDummy o) {
        return o.points-this.points;
    }
}

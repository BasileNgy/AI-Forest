import java.util.ArrayList;

public class Fact {

    public int x;
    public int y;
    public ArrayList<Element> elementList;

    public boolean discoveredRoom;



    //certitudes
    public boolean isEmpty;
    public boolean isWindy;
    public boolean isSmelly;
    public boolean isShiny;
    public boolean isSafe;
    public boolean containsMonster;
    public boolean containsCrevasse;
    public boolean containsExit;
    public boolean rockThrown;

    //dangers
    public int globalDanger;
    public int monsterDanger;
    public int crevasseDanger;

    public Fact(int x, int y)
    {
        this.x = x;
        this.y = y;
        elementList = new ArrayList<>();
        discoveredRoom = false;

        isEmpty = false;
        isShiny = false;
        isSmelly = false;
        isWindy = false;

        isSafe = false;


        containsMonster = false;
        containsCrevasse = false;
        containsExit = false;
        rockThrown = false;

        globalDanger = 0;
        monsterDanger = 0;
        crevasseDanger = 0;
    }

    @Override
    public String toString() {
        return "Fact{" +
                "x=" + x +
                ", y=" + y +
                ", elementList=" + elementList +
                ", discoveredRoom=" + discoveredRoom +
                ", isEmpty=" + isEmpty +
                ", isWindy=" + isWindy +
                ", isSmelly=" + isSmelly +
                ", isShiny=" + isShiny +
                ", isSafe=" + isSafe +
                ", containsMonster=" + containsMonster +
                ", containsCanyon=" + containsCrevasse +
                ", containsExit=" + containsExit +
                ", globalDanger=" + globalDanger +
                '}';
    }

}

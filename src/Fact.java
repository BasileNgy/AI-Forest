import java.util.ArrayList;

public class Fact {

    public int x;
    public int y;
    public ArrayList<Element> elementList;
    public boolean discoveredRoom;

    public boolean isEmpty;
    public boolean isWindy;
    public boolean isSmelly;
    public boolean isShiny;
    public boolean isKnown;
    public boolean mayContainMonster;
    public boolean mayContainCanyon;
    public boolean isSafe;

    public boolean containsMonster;
    public boolean containsCanyon;
    public boolean containsExit;

    public Fact(int x, int y)
    {
        this.x = x;
        this.y = y;
        elementList = new ArrayList<>();
        discoveredRoom = false;
        isKnown = false;
    }
}

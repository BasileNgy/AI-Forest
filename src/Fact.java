import java.util.ArrayList;

public class Fact {

    public int x;
    public int y;
    public ArrayList<Element> elementList;
    public boolean discoveredRoom;

    public Fact(int x, int y)
    {
        this.x = x;
        this.y = y;
        elementList = new ArrayList<>();
        discoveredRoom = false;
    }
}

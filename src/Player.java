import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Player {

    int x;
    int y;

    ArrayList<Room> pathTriedList;

    public Player()
    {
        x = 0;
        y = 0;
        pathTriedList = new ArrayList<>();
    }

}

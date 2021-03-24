import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Player {

    int x;
    int y;

    ArrayList<Point2D> pathTriedList;

    public Player()
    {
        x = 0;
        y = 0;
        pathTriedList = new ArrayList<>();
    }

    public void SetPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}

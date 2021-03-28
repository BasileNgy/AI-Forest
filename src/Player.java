import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Player {

    int x;
    int y;

    ArrayList<Point2D> pathTriedList;

    public Player(int n)
    {
        pathTriedList = new ArrayList<>();
        Random rand = new Random();
        SetPosition(rand.nextInt(n), rand.nextInt(n) );
    }

    public void SetPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Player {

    int x;
    int y;

    Capteur capteur;
    Effecteur effecteur;

    ArrayList<Point2D> pathTriedList;

    public Player(Capteur capteurs, Effecteur effecteur)
    {
        x = 0;
        y = 0;
        capteur = capteurs;
        effecteur = effecteur;
        pathTriedList = new ArrayList<>();
    }

    public void SetPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}

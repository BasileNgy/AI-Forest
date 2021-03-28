import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graphic extends JFrame
{

    private final JFrame f;
    private int size;
    private final ArrayList<JLabel> labelList;

    public Graphic(int n)
    {
        f = new JFrame();
        size = n;
        labelList = new ArrayList<>();
        for (int i = 0; i < size*size; i++) {
            JLabel lab = new JLabel();
            labelList.add(lab);
            f.add(lab);
        }

        f.setSize(500,500);
        f.setDefaultCloseOperation( EXIT_ON_CLOSE );
        f.setLayout(new GridLayout(n,n));
        f.setTitle("Forêt");
        f.setVisible(true);
    }

    public void UpdateGraphic(Room[][] map, Player p, ArrayList<Room> fringe)
    {
        int labelIndex = 0;
        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
            {
                String text = map[i][j].graphicText;
                if(i == p.x && j == p.y)
                    text += " Joueu";

                if(map[i][j].facts.discoveredRoom)
                    labelList.get(labelIndex).setForeground(Color.BLUE);
                if(fringe.contains(map[i][j]))
                    labelList.get(labelIndex).setForeground(Color.ORANGE);
                labelList.get(labelIndex).setText(text);
                labelIndex ++;
            }
    }
    public void UpdateLabel(Room room){
        room.SetGraphicText();
        String text = room.graphicText;
        labelList.get(room.x*size+ room.y).setText(text);
    }
}

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphic extends JFrame
{

    private final JFrame f;
    private final JPanel p;
    private JLabel performLabel;
    private final JButton moveBtn;
    private int size;
    private final ArrayList<JLabel> labelList;

    public Graphic(int n, Agent agent)
    {
        size = n;

        f = new JFrame();
        p = new JPanel();
        moveBtn = new JButton("Action");
        labelList = new ArrayList<>();
        performLabel = new JLabel();

        moveBtn.addActionListener(e -> agent.Resolution());

        for (int i = 0; i < size*size; i++) {
            JLabel lab = new JLabel();
            labelList.add(lab);
            p.add(lab);
        }

        p.setLayout(new GridLayout(n,n));
        f.setSize(900,700);
        f.setDefaultCloseOperation( EXIT_ON_CLOSE );
        f.setTitle("Forêt");
        f.setVisible(true);

        f.setLayout(new BorderLayout());
        f.add(p, BorderLayout.CENTER);
        f.add(moveBtn, BorderLayout.SOUTH);
        f.add(performLabel, BorderLayout.EAST);
    }

    public void SetNewEnvironnement(int n)
    {
        size = n;
        p.removeAll();
        p.revalidate();
        p.repaint();
        labelList.clear();

        for (int i = 0; i < size*size; i++) {
            JLabel lab = new JLabel();
            labelList.add(lab);
            p.add(lab);
        }

        p.setLayout(new GridLayout(n,n));
    }

    public void UpdateGraphic(Room[][] map, Player p, ArrayList<Room> fringe, int performance) {
        for (int j = 0; j < size; j++){
            for (int i = 0; i < size; i++) {
                Room room = map[i][j];
                UpdateLabel(room, p, fringe);
            }
        }
        performLabel.setText("Performance :" + performance);
    }

    public void UpdateLabel(Room room, Player p, ArrayList<Room> fringe){

        int labelIndex = room.y * size + room.x;

        room.SetGraphicText();
        String text = room.graphicText;
        JLabel lab = labelList.get(labelIndex);

        if (room.facts.discoveredRoom)
            lab.setForeground(Color.BLUE);

        else if (fringe.contains(room)){

            lab.setForeground(Color.ORANGE);


            if (room.facts.globalDanger > 0) {
                lab.setForeground(Color.RED);
                if (!room.facts.containsMonster && !room.facts.containsCrevasse) {
                    text = "Dangerous : M(" + room.facts.monsterDanger + ") C(" + room.facts.crevasseDanger + ") G(" + room.facts.globalDanger + ")";
                } else if (room.facts.containsCrevasse && room.facts.containsMonster) {
                    text = "Dangerous ! Monster & Crevasse !";
                } else if (room.facts.containsCrevasse) {
                    text = "Dangerous ! Crevasse ! M(" + room.facts.monsterDanger + ") G(" + room.facts.globalDanger + ")";
                } else if (room.facts.containsMonster) {
                    text = "Dangerous ! Monster ! C(" + room.facts.crevasseDanger + ") G(" + room.facts.globalDanger + ")";
                }
            } else if (room.facts.isSafe) text = "Safe !";


            if(room.facts.rockThrown){
                text = "Cleared";
                lab.setForeground(Color.ORANGE);
            }

        }
        if (room.x == p.x && room.y == p.y) {
            text += " Joueur";
            lab.setForeground(Color.GREEN);
        }

        lab.setText(text);
        lab.setHorizontalAlignment(SwingConstants.CENTER);
        lab.setVerticalAlignment(SwingConstants.CENTER);
    }
}

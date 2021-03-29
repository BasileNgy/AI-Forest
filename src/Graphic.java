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

    public void UpdateGraphic(Room[][] map, Player p, ArrayList<Room> fringe, int performance)
    {
        int labelIndex = 0;
        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
            {
                String text = map[i][j].graphicText;
                if(i == p.x && j == p.y)
                    text += " Joueu";

                JLabel lab = labelList.get(labelIndex);

                if(map[i][j].facts.discoveredRoom)
                    lab.setForeground(Color.BLUE);
                if(fringe.contains(map[i][j]))
                    lab.setForeground(Color.ORANGE);

                lab.setText(text);
                lab.setHorizontalAlignment(SwingConstants.CENTER);
                lab.setVerticalAlignment(SwingConstants.CENTER);

                performLabel.setVerticalAlignment(SwingConstants.CENTER);
                performLabel.setText("Performance :" + performance);

                labelIndex ++;
            }
    }

    public void UpdateLabel(Room room){
        room.SetGraphicText();
        String text = room.graphicText;
        labelList.get(room.x*size+ room.y).setText(text);
    }
}

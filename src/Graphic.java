import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Graphic extends JFrame
{

    private final JFrame f;
    private final JPanel p;
    private final JPanel pLegend;
    private JLabel performLabel;
    private final JButton moveBtn;
    private int size;
    private final ArrayList<JLabel> labelList;

    public Graphic(int n, Agent agent)
    {
        size = n;

        f = new JFrame();
        p = new JPanel();
        pLegend = new JPanel();
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
        pLegend.setLayout(new GridLayout(1,7));
        SetupLegende();

        f.setSize(900,700);
        f.setDefaultCloseOperation( EXIT_ON_CLOSE );
        f.setTitle("Forêt");
        f.setVisible(true);

        f.setLayout(new BorderLayout());
        f.add(p, BorderLayout.CENTER);
        f.add(pLegend, BorderLayout.NORTH);
        f.add(moveBtn, BorderLayout.SOUTH);
        f.add(performLabel, BorderLayout.EAST);
    }

    private void SetupLegende()
    {
        JLabel legend = new JLabel("Légende :");
        JLabel inconnues = new JLabel("Inconnues");
        JLabel connues = new JLabel("Connues");
        JLabel frontiere = new JLabel("Frontières");
        JLabel danger = new JLabel("Danger");
        JLabel sortie = new JLabel("Sortie");
        JLabel joueur = new JLabel("Joueur");

        connues.setForeground(Color.BLUE);
        frontiere.setForeground(Color.ORANGE);
        danger.setForeground(Color.RED);
        sortie.setForeground(Color.GREEN);
        joueur.setForeground(Color.MAGENTA);

        pLegend.add(legend);
        pLegend.add(inconnues);
        pLegend.add(connues);
        pLegend.add(frontiere);
        pLegend.add(danger);
        pLegend.add(sortie);
        pLegend.add(joueur);
    }

    /*
    RAZ du panel et de la list de label, initialisation de n² labels
     */
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

    /*
    Mise à jour du plateau
     */
    public void UpdateGraphic(Room[][] map, Player p, ArrayList<Room> fringe, int performance) {
        for (int j = 0; j < size; j++){
            for (int i = 0; i < size; i++) {
                Room room = map[i][j];
                UpdateLabel(room, p, fringe);
            }
        }
        performLabel.setText("Performance :" + performance);
    }

    /*
    Update des labels selon le contenu de la room + si la room est connue/inconnue/frontière/dangereuse
    Si la room est potentiellement dangereuse, affichage du niveau de danger
     */
    public void UpdateLabel(Room room, Player p, ArrayList<Room> fringe){

        int labelIndex = room.y * size + room.x;

        room.SetGraphicText();
        String text = room.graphicText;
        JLabel lab = labelList.get(labelIndex);

        if (room.elementList.contains(Element.SORTIE)) {
            lab.setForeground(Color.GREEN);
        }

        if (room.facts.discoveredRoom)
            lab.setForeground(Color.BLUE);

        else if (fringe.contains(room)){

            lab.setForeground(Color.ORANGE);


            if (room.facts.globalDanger > 0) {
                lab.setForeground(Color.RED);
                if (!room.facts.containsMonster && !room.facts.containsCrevasse) {
                    text = "Danger : M(" + room.facts.monsterDanger + ") C(" + room.facts.crevasseDanger + ") G(" + room.facts.globalDanger + ")";
                } else if (room.facts.containsCrevasse && room.facts.containsMonster) {
                    text = "/!\\ Monster & Crevasse !";
                } else if (room.facts.containsCrevasse) {
                    text = "/!\\ Crevasse ! M(" + room.facts.monsterDanger + ") G(" + room.facts.globalDanger + ")";
                } else if (room.facts.containsMonster) {
                    text = "/!\\ Monster ! C(" + room.facts.crevasseDanger + ") G(" + room.facts.globalDanger + ")";
                }
            } else if (room.facts.isSafe) text = "Safe";


            if(room.facts.rockThrown){
                text = "Cleared";
                lab.setForeground(Color.ORANGE);
            }


            if (room.elementList.contains(Element.SORTIE))
                text += "(Sortie)";

        }

        if (room.x == p.x && room.y == p.y) {
            text += " Joueur";
            lab.setForeground(Color.MAGENTA);
        }

        lab.setText(text);
        lab.setHorizontalAlignment(SwingConstants.CENTER);
        lab.setVerticalAlignment(SwingConstants.CENTER);
    }
}

import java.util.ArrayList;

public class Agent {

    public ArrayList<Fact> factsList;
    private int size;
    private Capteur capteur;
    private Effecteur effecteur;
    private Player player;

    public Agent(Capteur capteur, Effecteur effecteur, Player p, int size)
    {
        this.capteur = capteur;
        this.effecteur = effecteur;
        this.size = size;
        this.player = p;
        factsList = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++)
                factsList.add(new Fact(i,j));
        }
    }

    public void Resolution()
    {
        //maj des faits avec capteurs
        UpdateFacts();
        //application des règles, choix de l'action
        //action
    }

    private void UpdateFacts()
    {
        ArrayList<Element> elementsDetected = new ArrayList<>();

        if(capteur.isLumiere())
            elementsDetected.add(Element.SORTIE);
        if(capteur.isOdeur())
        {
            elementsDetected.add(Element.ODEUR);
            UpdateFringe(Element.MONSTRE, GetListAdjacentFactUnKnownRooms());
        }
        if(capteur.isVent())
        {
            elementsDetected.add(Element.VENTEUSE);
            UpdateFringe(Element.MONSTRE, GetListAdjacentFactUnKnownRooms());
        }

        if(capteur.isMonstre())
            elementsDetected.add(Element.MONSTRE);
        if(capteur.isCrevasse())
            elementsDetected.add(Element.CREVASSE);

        for (Fact f : factsList)
        {
            if(f.x == player.x && f.y == player.y)
            {
                f.elementList.clear();
                f.elementList.addAll(elementsDetected);
                f.discoveredRoom = true;
            }
        }
    }

    private void UpdateFringe(Element e, ArrayList<Fact> adjFacts)
    {
        for (Fact f : adjFacts)
        {
            f.elementList.add(e);
        }
    }

    /*
    Getter de la list des facts adjacents à une room
     */
    private ArrayList<Fact> GetListAdjacentFactUnKnownRooms()
    {
        ArrayList<Fact> maskedFacts = new ArrayList();

        //Case inconnu Haut (détermine si le joueur est au bord du terrain => pas de case adjacente en haut)
        if (player.y > 0) {
            for (Fact f : factsList)
                if (f.x == player.x && f.y == player.y - 1 && !f.discoveredRoom)
                    maskedFacts.add(f);
        }
        //Case inconnu Droite
        if (player.x < size) {
            for (Fact f : factsList)
                if (f.x == player.x + 1 && f.y == player.y && !f.discoveredRoom)
                    maskedFacts.add(f);
        }
        //Case inconnu Bas
        if (player.y < size) {
            for (Fact f : factsList)
                if (f.x == player.x && f.y == player.y + 1 && !f.discoveredRoom)
                    maskedFacts.add(f);
        }
        //Case inconnu Gauche
        if (player.x > 0) {
            for (Fact f : factsList)
                if (f.x == player.x - 1 && f.y == player.y && !f.discoveredRoom)
                    maskedFacts.add(f);
        }
        return maskedFacts;
    }
}

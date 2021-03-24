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

        if(capteur.isOdeur())
            elementsDetected.add(Element.ODEUR);
        if(capteur.isLumiere())
            elementsDetected.add(Element.SORTIE);
        if(capteur.isVent())
            elementsDetected.add(Element.VENTEUSE);

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
                return;
            }
        }

        Fact fact = new Fact(player.x, player.y);
        fact.elementList.addAll(elementsDetected);
        fact.discoveredRoom = true;
        factsList.add(fact);
    }

    /*
    Getter de la list des rooms frontières
     */
    private ArrayList<Room> GetListAdjacentUnKnownRooms(Room r)
    {
        ArrayList<Room> maskedRooms = new ArrayList();


        if(r.y > 0)
        {
            for (Fact f : factsList)
            {
                if(f.x == r.x && f.y == r.y)
                {

                }
            }
        }
        if(r.x < size)
        {

        }
        if(r.y < size)
        {

        }
        if(r.x > 0)
        {

        }
        return maskedRooms;
    }
}

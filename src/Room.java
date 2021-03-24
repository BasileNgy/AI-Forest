import java.util.ArrayList;

enum Element
{
    CREVASSE, SORTIE, VENTEUSE, ODEUR, MONSTRE
}

public class Room  implements Comparable<Room>
{
    public int x;
    public int y;
    public ArrayList<Element> elementList;
    String graphicText = "";

    //Utilisé par l'agent
    public int nivDanger;
    public boolean discoveredRoom;

    public Room(int x, int y)
    {
        elementList = new ArrayList<>();
        this.x = x;
        this.y = y;
        nivDanger = 0;
        discoveredRoom = false;
    }

    public boolean AddElement(Element e)
    {
        boolean added = true;
        switch (e)
        {
            case ODEUR:
                if(!elementList.contains(Element.ODEUR))
                    elementList.add(e);
                break;

            case SORTIE:
                elementList.add(e);
                break;

            case MONSTRE:
            case CREVASSE:
                if(!elementList.contains(Element.SORTIE))
                {
                    elementList.add(e);
                }else
                    added = false;
                break;

            case VENTEUSE:
                if(!elementList.contains(Element.VENTEUSE))
                    elementList.add(e);
                break;

            default:
                added = false;
                break;
        }
        return added;
    }

    public void SetGraphicText()
    {
        if(elementList.isEmpty())
            graphicText = "Vide";

        for (Element e: elementList)
        {
            switch(e)
            {
                case VENTEUSE -> graphicText += " Vent";
                case CREVASSE -> graphicText += " Crev";
                case MONSTRE -> graphicText += " Mstr";
                case SORTIE -> graphicText += " Sort";
                case ODEUR -> graphicText += " Ode";
            }
        }
    }

    @Override
    public int compareTo(Room r) {
        return this.nivDanger - r.nivDanger;
    }
}

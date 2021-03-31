import java.util.ArrayList;

enum Element
{
    CREVASSE, SORTIE, VENTEUSE, ODEUR, MONSTRE, LUMIERE
}

public class Room implements Comparable<Room>
{
    public int x;
    public int y;
    public ArrayList<Element> elementList;
    public Fact facts;
    public ArrayList<Room> neighbors;
    String graphicText = "";

    public Room(int x, int y)
    {
        elementList = new ArrayList<>();
        this.x = x;
        this.y = y;
        neighbors = new ArrayList<>();
        facts = new Fact(x,y);
    }

    public boolean AddElement(Element e)
    {
        boolean added = true;
        switch (e)
        {
            case ODEUR:
                if(!elementList.contains(Element.ODEUR))
                    elementList.add(e);
                added = true;
                break;

            case SORTIE:
                elementList.add(e);
                added = true;
                break;

            case MONSTRE:
            case CREVASSE:
                if(!elementList.contains(Element.SORTIE))
                {
                    elementList.add(e);
                    added = true;
                }else
                    added = false;
                break;

            case VENTEUSE:
                if(!elementList.contains(Element.VENTEUSE))
                    elementList.add(e);
                added = true;
                break;

            default:
                added = false;
                break;
        }
        return added;
    }

    public void SetGraphicText()
    {
        graphicText = "";
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
    public int compareTo(Room o) {
        return this.facts.globalDanger - o.facts.globalDanger;
    }

    @Override
    public String toString() {
        return "Room{" +
                "x=" + x +
                ", y=" + y +
                ", elementList=" + elementList + '\'' +
                '}';
    }
}

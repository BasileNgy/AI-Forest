import java.util.ArrayList;

enum Element
{
    CREVASSE, SORTIE, VENTEUSE, ODEUR, MONSTRE
}

public class Room
{
    public int x;
    public int y;
    public ArrayList<Element> elementList;
    String graphicText = "";

    public Room(int x, int y)
    {
        elementList = new ArrayList<>();
        this.x = x;
        this.y = y;
    }

    public void AddElement(Element e)
    {
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
                    elementList.add(e);
                break;

            case VENTEUSE:
                if(!elementList.contains(Element.VENTEUSE))
                    elementList.add(e);
                break;

            default:
                break;
        }
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
}

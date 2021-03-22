public class Capteur
{
    Room[][] map;

    public Capteur(Room[][] map)
    {
        this.map = map;
    }

    public boolean isOdeur(int x, int y)
    {
        return map[x][y].elementList.contains(Element.ODEUR);
    }

    public boolean isVent(int x, int y)
    {
        return map[x][y].elementList.contains(Element.VENTEUSE);
    }

    public boolean isLumiere(int x, int y)
    {
        return map[x][y].elementList.contains(Element.SORTIE);
    }
}

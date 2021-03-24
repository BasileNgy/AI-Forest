public class Capteur
{
    Room[][] map;
    Player p;

    public Capteur(Room[][] map, Player p)
    {
        this.map = map;
        this.p = p;
    }

    public boolean isOdeur()
    {
        return map[p.x][p.y].elementList.contains(Element.ODEUR);
    }

    public boolean isVent()
    {
        return map[p.x][p.y].elementList.contains(Element.VENTEUSE);
    }

    public boolean isLumiere()
    {
        return map[p.x][p.y].elementList.contains(Element.SORTIE);
    }


    public boolean isMonstre()
    {
        return map[p.x][p.y].elementList.contains(Element.MONSTRE);
    }

    public boolean isCrevasse()
    {
        return map[p.x][p.y].elementList.contains(Element.CREVASSE);
    }
}

public class Capteur
{
    Room[][] map;

    public Capteur(Room[][] map)
    {
        this.map = map;
    }

    public boolean isOdeur(Player p)
    {
        return map[p.x][p.y].elementList.contains(Element.ODEUR);
    }

    public boolean isVent(Player p)
    {
        return map[p.x][p.y].elementList.contains(Element.VENTEUSE);
    }

    public boolean isLumiere(Player p)
    {
        return map[p.x][p.y].elementList.contains(Element.SORTIE);
    }


    public boolean isMonstre(Player p)
    {
        return map[p.x][p.y].elementList.contains(Element.MONSTRE);
    }

    public boolean isCrevasse(Player p)
    {
        return map[p.x][p.y].elementList.contains(Element.CREVASSE);
    }
}

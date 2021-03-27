public class Capteur
{
    Room[][] map;
    Player p;

    public Capteur(Room[][] map, Player p)
    {
        this.map = map;
        this.p = p;
    }

    public  boolean isThereNothing(){
        return      !map[p.x][p.y].elementList.contains(Element.ODEUR)
                &&  !map[p.x][p.y].elementList.contains(Element.VENTEUSE)
                &&  !map[p.x][p.y].elementList.contains(Element.LUMIERE)
                &&  !map[p.x][p.y].elementList.contains(Element.MONSTRE)
                &&  !map[p.x][p.y].elementList.contains(Element.CREVASSE);
    }

    public boolean isThereSmell()
    {
        return map[p.x][p.y].elementList.contains(Element.ODEUR);
    }

    public boolean isThereWind()
    {
        return map[p.x][p.y].elementList.contains(Element.VENTEUSE);
    }

    public boolean isItShining()
    {
        return map[p.x][p.y].elementList.contains(Element.SORTIE);
    }


    public boolean isThereMonster()
    {
        return map[p.x][p.y].elementList.contains(Element.MONSTRE);
    }

    public boolean isThereRift()
    {
        return map[p.x][p.y].elementList.contains(Element.CREVASSE);
    }
}

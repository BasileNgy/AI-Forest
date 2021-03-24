enum Orientation
{
    HAUT,DROITE,BAS,GAUCHE
}

public class Effecteur
{

    Room[][] map;

    public Effecteur(Room[][] map)
    {
        this.map = map;
    }

    public void Teleportation(int x, int y, Player p)
    {
        p.x = x;
        p.y = y;
    }

    public void Haut(Player p)
    {
        p.y -= 1;
    }

    public void Droite(Player p)
    {
        p.x += 1;
    }

    public void Bas(Player p)
    {
        p.y += 1;
    }

    public void Gauche(Player p)
    {
        p.x -= 1;
    }

    public void Tirer(Player p, Orientation orient)
    {
        int x = p.x;
        int y = p.y;
        switch(orient)
        {
            case HAUT:
                y -= 1;
                break;
            case DROITE:
                x += 1;
                break;
            case BAS:
                y += 1;
                break;
            case GAUCHE:
                x -= 1;
                break;
        }

        if(map[x][y].elementList.contains(Element.MONSTRE))
        {
            map[x][y].elementList.remove(Element.MONSTRE);
            System.out.println("Un monstre a été tué");
        }
    }

    public void Sortir()
    {

    }
}

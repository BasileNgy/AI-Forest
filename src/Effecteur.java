enum Orientation
{
    HAUT,DROITE,BAS,GAUCHE
}

enum Action
{
    HAUT,DROITE,BAS,GAUCHE,TIRER,SORTIR
}

public class Effecteur
{

    Room[][] map;

    public Effecteur(Room[][] map)
    {
        this.map = map;
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

    public void MovePlayer(Player p, Action action)
    {
        switch (action) {
            case HAUT -> p.y -= 1;
            case DROITE -> p.x += 1;
            case BAS -> p.y += 1;
            case GAUCHE -> p.x -= 1;
        }
    }

    public void Teleport(Player p, int x, int y)
    {
        p.x = x;
        p.y = y;
    }

    public void Tirer(Player p, Orientation orient)
    {
        int x = p.x;
        int y = p.y;
        switch (orient) {
            case HAUT -> y -= 1;
            case DROITE -> x += 1;
            case BAS -> y += 1;
            case GAUCHE -> x -= 1;
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

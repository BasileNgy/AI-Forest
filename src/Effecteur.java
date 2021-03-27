public class Effecteur
{

    Room[][] map;

    public Effecteur(Room[][] map)
    {
        this.map = map;
    }

    public void Teleportation(Player p, Room r)
    {
        p.x = r.x;
        p.y = r.y;

        System.out.println("Teleportation sur la case : "+ r.toString());
    }

    public void Tirer(Room r)
    {
        if(r.elementList.contains(Element.MONSTRE))
        {
            r.elementList.remove(Element.MONSTRE);
            System.out.println("Un monstre a été tué");

            for(Room neigh : r.neighbors)
            {
                neigh.elementList.remove(Element.ODEUR);
            }
        }
        System.out.println("Tirer sur la case : "+ r.toString());
    }

}

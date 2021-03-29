public class Effecteur
{

    Room[][] map;
    Environnement env;

    public Effecteur( Environnement env)
    {
        this.env = env;
        this.map = env.map;
    }

    public void SetNewEnvironnement(Environnement env){
        this.env = env;
        this.map = env.map;
    }

    public void Teleportation(Player p, Room r)
    {
        p.x = r.x;
        p.y = r.y;

        System.out.println("Teleportation sur la case : "+ r.toString());
    }

    public void Tirer(Room r)
    {
        System.out.println("Tirer sur la case : "+ r.toString());
        r.facts.rockThrown = true;
        if(r.elementList.contains(Element.MONSTRE))
        {
            r.elementList.remove(Element.MONSTRE);
            for(Room room : r.neighbors)
                room.elementList.remove(Element.ODEUR);
            System.out.println("Un monstre a été tué sur la case "+r.toString());
            env.UpdateNeighborKnowledge();
        }
    }

}

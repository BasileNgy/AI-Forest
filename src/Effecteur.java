public class Effecteur
{

    Room[][] map;
    Environnement env;
    Player player;

    public Effecteur(Player p)
    {
        this.player = p;
    }

    public void SetNewEnvironnement(Environnement env){
        this.env = env;
        this.map = env.map;
    }

    public void Teleportation(Room r)
    {
        player.x = r.x;
        player.y = r.y;

        System.out.println("Teleportation sur la case : "+ r.toString());
    }

    public void Tirer(Room r)
    {
        System.out.println("Tirer sur la case : "+ r.toString());
        r.facts.rockThrown = true;
        if(r.elementList.contains(Element.MONSTRE))
        {
            r.elementList.remove(Element.MONSTRE);
            System.out.println("Un monstre a été tué sur la case "+r.toString());
            for(Room room : r.neighbors){
                room.elementList.remove(Element.ODEUR);
                System.out.println("Odeur removed from cell " +room.toString());
            }
            env.UpdateNeighborKnowledge();
        }
    }

}

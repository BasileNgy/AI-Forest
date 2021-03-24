public class main
{



    public static void main(String[] args)
    {
        int n = 3;
        Environnement envir = new Environnement(n);
        envir.SetUpInitialState();

        Player player = new Player();

        Graphic graph = new Graphic(n);

        Agent agent = new Agent(player, new Capteur(envir.map), new Effecteur(envir.map), n);

        do {
            graph.UpdateGraphic(envir.map, player);
            agent.Resolution();
        }while(true);

    }
}

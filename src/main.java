public class main
{



    public static void main(String[] args)
    {
        int n = 3;
        Environnement envir = new Environnement(n);
        envir.SetUpInitialState();

        Player player = new Player();
        Agent agent = new Agent(new Capteur(envir.map, player), new Effecteur(envir.map), player, n, envir.map);

        Graphic graph = new Graphic(n);

        graph.UpdateGraphic(envir.map, player);
        agent.Resolution();

        //TODO boucle infinie foret
    }
}

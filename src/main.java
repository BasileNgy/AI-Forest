
public class main
{


    public static void main(String[] args)
    {

        main main = new main();
        int n = 3;

        Player player = new Player();
        Capteur capteur = new Capteur(player);
        Effecteur effecteur = new Effecteur(player);

        Agent agent = new Agent(capteur, effecteur, main);
        agent.ResetAgent(player, n);


        Environnement envir = new Environnement(n, player);
        envir.SetUpInitialState();

        capteur.SetNewEnvironnement(envir);
        effecteur.SetNewEnvironnement(envir);

        Graphic graph = new Graphic(n, agent);
        agent.SetMap(envir.map, graph);
        agent.BeginningDetection();
    }

    public void CreateNewEnvironnement(int n, Player player, Agent agent, Capteur capteur, Effecteur effecteur, Graphic graph)
    {

        agent.ResetAgent(player, n);

        Environnement envir = new Environnement(n, player);
        envir.SetUpInitialState();

        capteur.SetNewEnvironnement(envir);
        effecteur.SetNewEnvironnement(envir);

        graph.SetNewEnvironnement(n);

        agent.SetMap(envir.map, graph);
        agent.BeginningDetection();

    }

}


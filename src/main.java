import java.util.Random;

public class main
{


    public static void main(String[] args)
    {

        int n = 9;
//        boolean keepPlaying = true;

        Player player = new Player();
        Capteur capteur = new Capteur(player);
        Effecteur effecteur = new Effecteur(player);

        Agent agent = new Agent(capteur, effecteur);
        agent.ResetAgent(player, n);


        Environnement envir = new Environnement(n, player);
        envir.SetUpInitialState();

        capteur.SetNewEnvironnement(envir);
        effecteur.SetNewEnvironnement(envir);

        Graphic graph = new Graphic(n, agent);
        agent.SetMap(envir.map, graph);
        agent.BeginningDetection();

//        main obj = new main();
//        Thread mainThread = new Thread(obj);
//        mainThread.start();

//        do {
            //if(mainTimer = true){

                /*boolean agentResolved = agent.Resolution();
                //mainTimer = false;
                if (!agentResolved) {
                    System.out.println("Player died");
//                    keepPlaying = false;
//            mainRunning = false;
                }
                System.out.println("Resolution done, generate next level");*/
//                n++;
//                envir.CreateNextForest(n);
//                envir.SetUpInitialState();
//                Graphic newGraph = new Graphic(n);
//
//                player.SetPosition(0,0);
//                capteur.UpdateMap(envir.map);
//                effecteur.UpdateEnv(envir);
//                agent.ResetAgent(player, n, envir.map, newGraph);
//                try{
//                    Thread.sleep(5000);
//                } catch(Exception e){
//                    e.printStackTrace();
//                }
//            }
//        } while (keepPlaying);
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


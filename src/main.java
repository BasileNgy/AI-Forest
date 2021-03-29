 public class main
{


    public static void main(String[] args)
    {

        int n = 3;
//        boolean keepPlaying = true;

        Player player = new Player(n);
        Environnement envir = new Environnement(n, player);
        envir.SetUpInitialState();
        Capteur capteur = new Capteur(envir.map, player);
        Effecteur effecteur = new Effecteur(envir);
        Agent agent = new Agent(capteur, effecteur);
        Graphic graph = new Graphic(n, agent);
        agent.ResetAgent(player, n, envir.map, graph);

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
        Environnement envir = new Environnement(n, player);
        envir.SetUpInitialState();

        capteur.SetNewEnvironnement(envir);
        effecteur.SetNewEnvironnement(envir);

        graph.SetNewEnvironnement(n);

        agent.ResetAgent(player, n, envir.map, graph);
    }

}


 public class main /*implements Runnable*/
{
//    private static boolean mainRunning;
//    private static boolean mainTimer;

    public static void main(String[] args)
    {
//        mainRunning = true;
//        mainTimer = true;

        int n = 9;
//        boolean keepPlaying = true;

        Graphic graph = new Graphic(n);
        Player player = new Player(n);
        Environnement envir = new Environnement(n, player);
        envir.SetUpInitialState();
        Capteur capteur = new Capteur(envir.map, player);
        Effecteur effecteur = new Effecteur(envir);
        Agent agent = new Agent(capteur, effecteur);
        agent.ResetAgent(player, n, envir.map, graph);


        Thread agentThread = new Thread(agent);
        agentThread.start();

//        main obj = new main();
//        Thread mainThread = new Thread(obj);
//        mainThread.start();

//        do {
            //if(mainTimer = true){

                boolean agentResolved = agent.Resolution();
                //mainTimer = false;
                if (!agentResolved) {
                    System.out.println("Player died");
//                    keepPlaying = false;
//            mainRunning = false;
                }
                System.out.println("Resolution done, generate next level");
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
/*
    @Override
    public void run() {
       while(mainRunning)
        {
          *//*  try{
                if(!mainTimer){
                    Thread.sleep(2000);
                    mainTimer = true;
                }
                //System.out.println("Everything is okay");
            } catch(Exception e){
                e.printStackTrace();
            }*//*
        }
       System.out.println("Ending main");
    }*/
}


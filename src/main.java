public class main implements Runnable
{

    static boolean timerEnded = true;

    public static void main(String[] args)
    {
        int n = 3;
        Environnement envir = new Environnement(n);
        envir.SetUpInitialState();

        Player player = new Player();
        Agent agent = new Agent(new Capteur(envir.map, player), new Effecteur(envir.map), player, n, envir.map);

        Graphic graph = new Graphic(n);

        main obj = new main();
        Thread mainThread = new Thread(obj);
        mainThread.start();

        agent.InitAgentKnowledge();
        graph.UpdateGraphic(envir.map, player);

        do {
            System.out.println(timerEnded);
            if(timerEnded)
            {
                agent.Resolution();
                graph.UpdateGraphic(envir.map, player);
                timerEnded = false;
            }
        }while(!agent.exitReached && !agent.playerIsDead);
        System.out.println("OUT");
    }

    @Override
    public void run() {
        while(true)
        {
            try {
                Thread.sleep(3000);
                timerEnded = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


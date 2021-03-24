public class main
{



    public static void main(String[] args)
    {
        int n = 3;
        Environnement envir = new Environnement(n);
        envir.SetUpInitialState();

        Player player = new Player(new Capteur(envir.map), new Effecteur(envir.map));

        Graphic graph = new Graphic(n);

        graph.UpdateGraphic(envir.map, player);
    }
}

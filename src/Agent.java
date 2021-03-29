import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

enum Action
{
    TIRER, TELEPORTER
}

public class Agent {

    private int size;
    private final Capteur capteur;
    private final Effecteur effecteur;
    private Player player;
    private Room[][] map;
    private RulesCreator rulesCreator;
    private Moteur moteur;
    private Graphic graph;
    public ArrayList<Room> interestingRooms;
    public ArrayList<Room> knownRooms;
    public ArrayList<Room> fringe;
    public HashMap<Room, Action> throwsTried;
    public boolean exitReached;
    public boolean playerIsDead;
    static boolean agentRunning;
    private int performance;

    public Agent(Capteur capteur, Effecteur effecteur)
    {
        this.capteur = capteur;
        this.effecteur = effecteur;
        moteur = new Moteur();
        rulesCreator = new RulesCreator(moteur);
        performance = 0;
    }

    public void ResetAgent(Player p, int size, Room[][] map, Graphic graph){
        agentRunning = true;
        this.size = size;
        this.player = p;
        this.map = map;
        this.graph = graph;

        interestingRooms = new ArrayList<>();
        knownRooms = new ArrayList<>();
        fringe = new ArrayList<>();
        throwsTried = new HashMap<>();
        exitReached = false;
        playerIsDead = false;
//      timerEnded = true;

        BeginningDetection();
    }

    public void BeginningDetection()
    {
        if(InitAgentKnowledge()){
            System.out.println("Exit reached !");
            exitReached = true;
            agentRunning = false;
        }
        graph.UpdateGraphic(map, player, fringe, performance);
    }

    public void Resolution()
    {
        if(!exitReached && !playerIsDead)
        {


            //Lancement de l'inférence
            moteur.Inference(interestingRooms);

            //Choix de la prochaine action à effectuer
            Room nextActionRoom = NextRoomChoice();
            Action nextAction = NextActionChoice(nextActionRoom);

            //Application de l'action
            ActionApply(nextAction, nextActionRoom);

            //Mets à jours les faits grace aux capteurs et recalcule les frontières
            UpdateAgentKnowledge();

            graph.UpdateGraphic(map, player, fringe, performance);
        }
        else
        {
            agentRunning = false;
            if(playerIsDead){
                {
                    System.out.println("I died :/");
                    performance -= 10*size;
                    CreateNewForest(3);
                }
            } else {
                performance += 10*size;
                CreateNewForest(size + 1);
            }

        }
    }

    private void CreateNewForest(int n)
    {
        main main = new main();
        System.out.println("Found exit ! go to next forest :)");
        main.CreateNewEnvironnement(n, player, this, capteur, effecteur, graph);
    }

    private void ActionApply(Action nextAction, Room nextRoom)
    {
        if(nextAction == Action.TIRER)
        {
            throwsTried.put(nextRoom,nextAction);
            effecteur.Tirer(nextRoom);
            performance -= 10;
            graph.UpdateLabel(nextRoom);
        }
        else if(nextAction == Action.TELEPORTER)
        {
            effecteur.Teleportation(player, nextRoom);
            performance -= 1;
        }
    }

    private Action NextActionChoice(Room actionRoom)
    {
        if(actionRoom.facts.mayContainMonster && !throwsTried.containsKey(actionRoom))
           return Action.TIRER;
       else
           return Action.TELEPORTER;
    }

    //Ordonnencement des rooms frontières selon leurs dangers
    private Room NextRoomChoice()
    {
        Collections.sort(fringe);
        int smallestDangerValue = fringe.get(0).facts.danger;
        ArrayList<Room> lessDangerousRooms = new ArrayList<>();
        for (Room room : fringe) {
            if(room.facts.danger == smallestDangerValue)
                lessDangerousRooms.add(room);
        }

        //lessDangerousRooms = CalculateClosestRoom(lessDangerousRooms);
        Random rand = new Random();
        return lessDangerousRooms.get(rand.nextInt(lessDangerousRooms.size()));
    }

  /*  private ArrayList<Room> CalculateClosestRoom(ArrayList<Room> lessDangerousRooms){
        ArrayList<Room> closestRooms = new ArrayList<>();
        double bestDistance = CalculateDistance(lessDangerousRooms.get(0));

        for(Room room : lessDangerousRooms){
            double roomDistance = CalculateDistance(room);
            if(bestDistance > roomDistance)
                bestDistance = roomDistance;
        }

        for(Room room : lessDangerousRooms){
            if(bestDistance == CalculateDistance(room))
                closestRooms.add(room);
        }
        return closestRooms;
    }

    private double CalculateDistance(Room room){
        double distance;
        distance = Math.sqrt((room.x- player.x) * (room.x- player.x) + (room.y- player.y) * (room.y- player.y) );
        return distance;
    }*/

    //Initialise la connaissance de l'agent : on ajoute la case sur laquelle il démarre à la liste des cases connues,
    // puis met à jour la frontière
    public boolean InitAgentKnowledge()
    {
        knownRooms.clear();

        System.out.println("Player in ["+player.x+","+player.y+"]");
        int initX = player.x;
        int initY = player.y;

        //maj des faits avec capteurs
        if(DetectEnvironment(map[initX][initY]))
        {
            exitReached = true;
            return true;
        }

        //Initialisation de la frontiere
        UpdateFringe();
        return false;
    }

    private void UpdateAgentKnowledge()
    {
        System.out.println("Player in ["+player.x+","+player.y+"]");
        //maj des faits avec capteurs
        if(DetectEnvironment(map[player.x][player.y]))
            exitReached = true;


        //Initialisation de la frontiere
        UpdateFringe();
        graph.UpdateGraphic(map, player, fringe, performance);
    }


    //Vérifie quels éléments se trouvent sur la case explorée. S'il y a un monstre ou une crevasse, on met à jour les
    // faits de la case, puis on tue le joueur sans ajouter la case aux cases connues.
    private boolean DetectEnvironment(Room room)
    {
        knownRooms.add(room);

        room.facts.isEmpty = false;
        room.facts.isShiny = false;
        room.facts.isSmelly = false;
        room.facts.isWindy = false;

        room.facts.isSafe = false;

        room.facts.containsMonster = false;
        room.facts.containsCanyon = false;
        room.facts.containsExit = false;

        if(capteur.isItShining()){
            room.facts.isShiny = true;
            room.facts.isSafe = true;
            room.facts.containsExit = true;
            room.facts.discoveredRoom = true;
            return true;
        }

        if(capteur.isThereNothing()){
            room.facts.isEmpty = true;
            room.facts.isSafe = true;
            room.facts.discoveredRoom = true;
            return false;
        }

        if(capteur.isThereSmell())
            room.facts.isSmelly = true;

        if(capteur.isThereWind())
            room.facts.isWindy = true;

        if(capteur.isThereMonster())
            room.facts.containsMonster = true;

        if ( capteur.isThereRift() )
            room.facts.containsCanyon = true;

        if(room.facts.containsCanyon || room.facts.containsMonster){
            playerIsDead = true;
            return  false;
        }

        room.facts.discoveredRoom = true;

        room.facts.isSafe = true;
        return false;
    }

    //Met à jour la frontière
    private void UpdateFringe(){
        fringe.clear();
        for(Room room : knownRooms){
            for(Room neigbhor : room.neighbors){
                if(!neigbhor.facts.discoveredRoom && !fringe.contains(neigbhor))
                    fringe.add(neigbhor);
            }
        }
        UpdateInterestingRooms();
    }

    /*
    Récupère les cases intéressantes pour l'inférence, c'est à dire les cases frontières et leurs voisines connues
     */
    private void UpdateInterestingRooms() {
        interestingRooms.clear();
        for(Room fringeRoom : fringe){
            if(!interestingRooms.contains(fringeRoom))
                interestingRooms.add(fringeRoom);
            for(Room neighbor : fringeRoom.neighbors){
                if(neighbor.facts.discoveredRoom && !interestingRooms.contains(neighbor))
                    interestingRooms.add(neighbor);
            }
        }

    }


}

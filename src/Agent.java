import com.sun.tools.javac.Main;

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
    private main main;

    public Agent(Capteur capteur, Effecteur effecteur, main main)
    {
        this.capteur = capteur;
        this.effecteur = effecteur;
        moteur = new Moteur();
        rulesCreator = new RulesCreator(moteur);
        performance = 0;
        this.main = main;
    }

    /*
    Reset des paramètres de l'agent pour commencer sur une nouvelle forêt
     */
    public void ResetAgent(Player p, int size){
        agentRunning = true;
        this.size = size;
        this.player = p;

        interestingRooms = new ArrayList<>();
        knownRooms = new ArrayList<>();
        fringe = new ArrayList<>();
        throwsTried = new HashMap<>();
        exitReached = false;
        playerIsDead = false;

        Random rand = new Random();
        this.player.SetPosition(rand.nextInt(size), rand.nextInt(size));
    }

    public void SetMap(Room[][] map, Graphic graph){
        this.map = map;
        this.graph = graph;
    }

    /*
    Première détection de l'environnement et inférence, permet de détecter si le joueur n'est pas sur la sortie
     */
    public void BeginningDetection()
    {
        if(InitAgentKnowledge()){
            System.out.println("Exit reached !");
            exitReached = true;
            agentRunning = false;
        }
        graph.UpdateGraphic(map, player, fringe, performance);
    }

    /*
    Cycle de résolution, méthode appelée par le bouton graphique
    Choisi l'action à appliquer, l'applique puis détecte l'environnement
    sur la nouvelle case et exécute un nouveau cycle d'inférence
     */
    public void Resolution()
    {
        if(!exitReached && !playerIsDead)
        {

            //Choix de la prochaine action à effectuer
            Room nextActionRoom = NextRoomChoice();
            Action nextAction = NextActionChoice(nextActionRoom);

            //Application de l'action
            ActionApply(nextAction, nextActionRoom);

            //RAZ des dangers
            for(Room fringeRoom : fringe) {
                fringeRoom.facts.globalDanger = 0;
                fringeRoom.facts.crevasseDanger = 0;
                fringeRoom.facts.monsterDanger = 0;
            }

            //Mets à jours les faits grace aux capteurs et recalcule les frontières
            UpdateAgentKnowledge();
        }
        else
        {
            agentRunning = false;
            //
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

    /*
    Création d'une nouvelle forêt de dimension n, raz des éléments agent, Player, Capteur, Effecteur et graphique
     */
    private void CreateNewForest(int n)
    {
        main.CreateNewEnvironnement(n, player, this, capteur, effecteur, graph);
    }

    /*
    Application de a prochaine action à effectuer
     */
    private void ActionApply(Action nextAction, Room nextRoom)
    {
        if(nextAction == Action.TIRER)
        {
            throwsTried.put(nextRoom,nextAction);
            effecteur.Tirer(nextRoom);
            performance -= 10;
            graph.UpdateLabel(nextRoom, player, fringe);
            for (Room room : nextRoom.neighbors)
                graph.UpdateLabel(room, player, fringe);
        }
        else if(nextAction == Action.TELEPORTER)
        {
            effecteur.Teleportation(nextRoom);
            performance -= 1;
        }
    }

    /*
    Détermine l'action à effectuer selon si la prochaine room contient un monstre ou non
     */
    private Action NextActionChoice(Room actionRoom)
    {
        if(actionRoom.facts.monsterDanger > 0 && !throwsTried.containsKey(actionRoom))
           return Action.TIRER;
       else
           return Action.TELEPORTER;
    }

    /*
    Ordonnencement des rooms frontières selon leurs dangers
    Retourne une des rooms avec le niveau de danger le plus bas
     */
    private Room NextRoomChoice()
    {
        Collections.sort(fringe);
        int smallestDangerValue = fringe.get(0).facts.globalDanger;
        ArrayList<Room> lessDangerousRooms = new ArrayList<>();
        for (Room room : fringe) {
            if(room.facts.globalDanger == smallestDangerValue)
                lessDangerousRooms.add(room);
        }

        Random rand = new Random();
        return lessDangerousRooms.get(rand.nextInt(lessDangerousRooms.size()));
    }

    //Initialise la connaissance de l'agent : on ajoute la case sur laquelle il démarre à la liste des cases connues,
    // puis met à jour la frontière et lancement du moteur d'inférence
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


        //Lancement de l'inférence
        moteur.Inference(interestingRooms);

        return false;
    }

    /*
    Mets à jour les connaissances de l'agent, la frontière et lance le cycle d'inférence
    */
    private void UpdateAgentKnowledge()
    {
        System.out.println("Player in ["+player.x+","+player.y+"]");
        //maj des faits avec capteurs
        if(DetectEnvironment(map[player.x][player.y]))
            exitReached = true;


        //Update de la frontiere
        UpdateFringe();

        //Lancement de l'inférence
        moteur.Inference(interestingRooms);

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
        room.facts.containsCrevasse = false;
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
            room.facts.containsCrevasse = true;

        if(room.facts.containsCrevasse || room.facts.containsMonster){
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
    public ArrayList<Room> interestingRooms;
    public ArrayList<Room> knownRooms;
    public ArrayList<Room> fringe;
    public boolean exitReached;
    public boolean playerIsDead;

    public Agent(Capteur capteur, Effecteur effecteur, Player p, int size, Room[][] map)
    {
        this.capteur = capteur;
        this.effecteur = effecteur;
        this.size = size;
        this.player = p;
        this.map = map;

        moteur = new Moteur();
        rulesCreator = new RulesCreator(moteur);
        interestingRooms = new ArrayList<>();
        knownRooms = new ArrayList<>();
        fringe = new ArrayList<>();
        exitReached = false;
        playerIsDead = false;

    }

    public void Resolution()
    {
        //Mets à jours les faits grace aux capteurs et recalcule les frontières
        UpdateAgentKnowledge();

        //Lancement de l'inférence
        moteur.Inference(interestingRooms);

        //Choix de la prochaine action à effectuer
        Action nextAction = NextActionChoice();

        //Application de l'action
        ActionApply(nextAction);
    }

    private void ActionApply(Action nextAction)
    {
        if(nextAction == Action.TIRER)
            effecteur.Tirer(fringe.get(0));
        else if(nextAction == Action.TELEPORTER)
            effecteur.Teleportation(player, fringe.get(0));
    }

    private Action NextActionChoice()
    {
        CalculDanger();
        if(fringe.get(0).facts.mayContainMonster)
           return Action.TIRER;
       else
           return Action.TELEPORTER;
    }

    //Ordonnencement des rooms frontières selon leurs dangers
    private void CalculDanger()
    {
        Collections.sort(fringe);
        //TODO créer un choix probabiliste si 2 room ont le même niveau de danger
    }


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
        //maj des faits avec capteurs
        if(DetectEnvironment(map[player.x][player.y]))
        {
            System.out.println("J'ai trouvé la sortie");
            exitReached = true;
        }

        //Initialisation de la frontiere
        UpdateFringe();
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
            return true;
        }

        if(capteur.isThereNothing()){
            room.facts.isEmpty = true;
            room.facts.isSafe = true;
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

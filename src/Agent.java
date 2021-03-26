import java.util.ArrayList;
import java.util.HashMap;

public class Agent {

    private int size;
    private final Capteur capteur;
    private final Effecteur effecteur;
    private Player player;
    private final Room[][] map;
    private RulesCreator rulesCreator;
    private Moteur moteur;
    public ArrayList<Room> interestingRooms;
    public ArrayList<Room> knownRooms;
    public ArrayList<Room> fringe;

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

    }

    public void Resolution()
    {
        if(InitAgentKnowledge()){
            //TODO passer à la forêt suivante
            return;
        }

        //Lancement de l'inférence
        moteur.Inference(interestingRooms);
        //Choix de l'action
    }


    //Initialise la connaissance de l'agent : on ajoute la case sur laquelle il démarre à la liste des cases connues,
    // puis met à jour la frontière
    private boolean InitAgentKnowledge(){
        knownRooms.clear();

        System.out.println("Player in ["+player.x+","+player.y+"]");
        Room initialRoom = map[player.x][player.y];

        //maj des faits avec capteurs
        DetectEnvironment(initialRoom);
        knownRooms.add(initialRoom);

        if(initialRoom.facts.containsExit){
            return true;
        }

        //Initialisation de la frontiere
        UpdateFringe();
        return false;
    }


    //Vérifie quels éléments se trouvent sur la case explorée. S'il y a un monstre ou une crevasse, on met à jour les
    // faits de la case, puis on tue le joueur sans ajouter la case aux cases connues.
    private void DetectEnvironment(Room room)
    {

        room.facts.isEmpty = false;
        room.facts.isShiny = false;
        room.facts.isSmelly = false;
        room.facts.isWindy = false;

        room.facts.isSafe = false;
        room.facts.isKnown = false;

        room.facts.containsMonster = false;
        room.facts.containsCanyon = false;
        room.facts.containsExit = false;

        if(capteur.isThereNothing()){
            room.facts.isEmpty = true;
            room.facts.isSafe = true;
            room.facts.isKnown = true;
            return ;
        }



        if(capteur.isItShining()){
            room.facts.isShiny = true;
            room.facts.isSafe = true;
            room.facts.isKnown = true;
            room.facts.containsExit = true;
            return;
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
            //TODO appel un gameover
            return ;
        }

        room.facts.isSafe = true;
        room.facts.isKnown = true;
    }

    //Met à jour la frontière
    private void UpdateFringe(){
        fringe.clear();
        for(Room room : knownRooms){
            for(Room neigbhor : room.neighbors){
                if(!neigbhor.facts.isKnown)
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
            interestingRooms.add(fringeRoom);
            for(Room neighbor : fringeRoom.neighbors){
                if(neighbor.facts.isKnown && !interestingRooms.contains(neighbor))
                    interestingRooms.add(neighbor);
            }
        }

    }


}

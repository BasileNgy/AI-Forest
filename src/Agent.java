import java.util.ArrayList;
import java.util.HashMap;

public class Agent {

    private int size;
    private final Capteur capteur;
    private final Effecteur effecteur;
    private Player player;
    private final Room[][] map;
    private RulesCreator rulesCreator;
    public ArrayList<HashMap<Rule, Room>> applicableRules;
    public ArrayList<Room> activeRoomsList;

    public Agent(Capteur capteur, Effecteur effecteur, Player p, int size, Room[][] map)
    {
        this.capteur = capteur;
        this.effecteur = effecteur;
        this.size = size;
        this.player = p;
        this.map = map;
        rulesCreator = new RulesCreator();
    }

    public void Resolution()
    {
        //maj des faits avec capteurs
        UpdateFacts();

        //application des règles, choix de l'action
        RulesApplication();

        //action
    }

    private void RulesApplication()
    {
        GetListRoomsFrontiereAndAdjacente();
        for(Room r : activeRoomsList)
            applicableRules.add(rulesCreator.GetRuleApplicable(r));


    }

    private void UpdateFacts()
    {
        if(capteur.isLumiere())
            map[player.x][player.y].facts.isShiny = true;
        if(capteur.isOdeur())
            map[player.x][player.y].facts.isSmelly = true;
        if(capteur.isVent())
            map[player.x][player.y].facts.isWindy = true;

        if(capteur.isMonstre() && capteur.isCrevasse())
        {
            if(capteur.isMonstre())
                map[player.x][player.y].facts.containsMonster = true;
            if(capteur.isCrevasse())
                map[player.x][player.y].facts.containsCanyon = true;
            //TODO appel un gameover
        }
        else
            map[player.x][player.y].facts.isSafe = true;

        map[player.x][player.y].facts.isKnown = true;
    }

    /*
    Getter des rooms Frontières et des rooms connues adjacentes aux frontières
     */
    private void GetListRoomsFrontiereAndAdjacente()
    {
        activeRoomsList = new ArrayList<>();

        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
            {
                if(map[i][j].facts.isKnown)
                {
                    for(Room r : map[i][j].neighbors)
                        if(!r.facts.isKnown)
                        {
                            activeRoomsList.add(map[i][j]);
                            continue;
                        }
                }
                else
                {
                    for(Room r : map[i][j].neighbors)
                        if(r.facts.isKnown)
                        {
                            activeRoomsList.add(map[i][j]);
                            continue;
                        }
                }
            }
    }

}

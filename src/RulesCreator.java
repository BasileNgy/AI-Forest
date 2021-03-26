import java.util.ArrayList;
import java.util.HashMap;

public class RulesCreator {

    public ArrayList<Rule> rulesList;
    public HashMap<Rule, Room> ruleApplicable;
    private Rule r1;
    private Rule r2;
    private Rule r3;
    private Rule r4;

    public RulesCreator()
    {
        rulesList = new ArrayList<>();
        ruleApplicable = new HashMap<>();

        //La case est vide, les cases adjacentes ne sont pas dangereuses
        r1 = new Rule(
                room -> {
                    if(room.facts.isEmpty){
                        //Ajouter aux regles applicables
                        ruleApplicable.put(r1, room);
                    }
                },
                room->{
                    for(Room voisin : room.neighbors){
                        if(!voisin.facts.isKnown){
                            voisin.facts.isSafe = true;
                        }
                    }
                }
        );

        //La case brille, la sortie est sur cette room
        r2 = new Rule(
                room -> {
                    if(room.facts.isShiny){
                        //Ajouter aux regles applicables
                        ruleApplicable.put(r2, room);
                    }
                },
                room-> room.facts.containsExit = true
        );

        //La case sent, un monstre doit être présent dans les cases adjacentes
        r3 = new Rule(
                room -> {
                    if(room.facts.isSmelly){
                        //Ajouter aux regles applicables
                        ruleApplicable.put(r3, room);
                    }
                },
                room->{
                    for(Room voisin : room.neighbors){
                        if(!voisin.facts.isKnown){
                            voisin.facts.mayContainMonster = true;
                        }
                    }
                }
        );

        //La case est venteuse, une crevasse doit être présente dans les cases adjacentes
        r4 = new Rule(
                room -> {
                    if(room.facts.isWindy){
                        //Ajouter aux regles applicables
                        ruleApplicable.put(r4, room);
                    }
                },
                room->{
                    for(Room voisin : room.neighbors){
                        if(!voisin.facts.isKnown){
                            voisin.facts.mayContainCanyon = true;
                        }
                    }
                }
        );

        rulesList.add(r1);
        rulesList.add(r2);
        rulesList.add(r3);
        rulesList.add(r4);
    }

    public HashMap<Rule, Room> GetRuleApplicable(Room r)
    {
        for(Rule rule : rulesList)
            rule.premise.apply(r);

        return ruleApplicable;
    }
}

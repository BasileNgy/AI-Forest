import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class RulesCreator {

    private Rule r1;
    private Rule r2;
    private Rule r3;
    private Rule r4;

    public RulesCreator(Moteur moteur)
    {
        //La case est vide, les cases adjacentes ne sont pas dangereuses
        r1 = new Rule(
                room -> {
                    if(room.facts.isEmpty) return true;
                    else return false;
                },
                room->{
                    for(Room voisin : room.neighbors){
                        if(!voisin.facts.discoveredRoom){
                            voisin.facts.isSafe = true;
                            voisin.facts.mayContainCanyon = false;
                            voisin.facts.mayContainMonster = false;
                            voisin.facts.danger = 0;
                        }
                    }

                },
                1
        );

        //La case sent, un monstre doit être présent dans les cases adjacentes
        r2 = new Rule(
                room -> {
                    if(room.facts.isSmelly) return true;
                    else return false;
                },
                room->{
                    for(Room voisin : room.neighbors){
                        if(!voisin.facts.isSafe && !voisin.facts.discoveredRoom && !room.facts.rockThrown){
                            if(!voisin.facts.mayContainMonster)
                                voisin.facts.danger += 10;

                            voisin.facts.mayContainMonster = true;

                        }
                    }
                },
                2
        );

        //La case est venteuse, une crevasse doit être présente dans les cases adjacentes
        r3 = new Rule(
                room -> {
                    if(room.facts.isWindy) return true;
                    else return false;
                },
                room->{
                    for(Room voisin : room.neighbors){
                        if(!voisin.facts.isSafe && !voisin.facts.discoveredRoom){
                            if(!voisin.facts.mayContainCanyon)
                                voisin.facts.danger += 100;
                            voisin.facts.mayContainCanyon = true;

                        }
                    }
                },
                3
        );

        //Le joueur a tiré sur cette case, on retire le fait mayContainMonster et on diminue le niceau de danger de la case
        r4 = new Rule(
                room -> {
                    if(room.facts.rockThrown) return true;
                    else return false;
                },
                room->{
                    if(room.facts.mayContainMonster)
                        room.facts.danger -= 10;
                    room.facts.mayContainMonster = false;
                },
                4
        );

        moteur.rulesList.add(r1);
        moteur.rulesList.add(r2);
        moteur.rulesList.add(r3);
        moteur.rulesList.add(r4);
        Collections.sort(moteur.rulesList);
        for(Rule rule : moteur.rulesList){
            moteur.markedRules.put(rule, new ArrayList<>());
        }
    }

}

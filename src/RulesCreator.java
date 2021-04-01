import java.util.ArrayList;
import java.util.Collections;

public class RulesCreator {

    private Rule r1;
    private Rule r2;
    private Rule r3;
    private Rule r4;
    private Rule r5;
    private Rule r6;
    private Rule r7;
    private Rule r8;
    private Rule r9;

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

                            voisin.facts.globalDanger = 0;
                            voisin.facts.crevasseDanger = 0;
                            voisin.facts.monsterDanger = 0;
                        }
                    }

                },
                1
        );

        //La case sent, un monstre doit être présent dans les cases adjacentes
        r2 = new Rule(
                room -> {
                    if (room.facts.isSmelly) return true;
                    else return false;
                },
                room -> {
                    for (Room voisin : room.neighbors) {

                        if (!voisin.facts.isSafe && !voisin.facts.discoveredRoom && voisin.facts.monsterDanger < voisin.neighbors.size() * 10) {

                            voisin.facts.monsterDanger += 10;
                            voisin.facts.globalDanger = voisin.facts.crevasseDanger + voisin.facts.monsterDanger;

                        }
                    }
                },
                2
        );

        //La case est venteuse, une crevasse doit être présente dans les cases adjacentes
        r3 = new Rule(
                room -> {
                    if (room.facts.isWindy) return true;
                    else return false;
                },
                room -> {
                    for (Room voisin : room.neighbors) {

                        if (!voisin.facts.isSafe && !voisin.facts.discoveredRoom && voisin.facts.crevasseDanger < voisin.neighbors.size() * 100) {

                            voisin.facts.crevasseDanger += 100;
                            voisin.facts.globalDanger = voisin.facts.crevasseDanger + voisin.facts.monsterDanger;
                        }
                    }
                },
                3
        );

        //Le joueur a tiré sur cette case, on retire le fait mayContainMonster
        r4 = new Rule(
                room -> {
                    if(room.facts.rockThrown) return true;
                    else return false;
                },
                room->{
                    if(room.facts.monsterDanger > 0){
                        room.facts.monsterDanger = 0;
                        room.facts.globalDanger = room.facts.crevasseDanger;

                    }

                },
                4
        );

        //si le niveau de danger correspond à l'ensemble des cases adjacentes à cette case, alors on admets qu'un monstre s'y trouve
        r5 = new Rule(
                room -> {
                    if(room.facts.monsterDanger == room.neighbors.size() * 10) return true;
                    else return false;
                },
                room -> {
                    room.facts.containsMonster = true;
                    room.facts.monsterDanger = 50;
                    room.facts.globalDanger = room.facts.crevasseDanger + 50;
                },
                5

        );

        //si le niveau de danger correspond à l'ensemble des cases adjacentes à cette case, alors on admets qu'une crevasse s'y trouve
        r6 = new Rule(
                room -> {
                    if(room.facts.crevasseDanger == room.neighbors.size() * 100) return true;
                    else return false;
                },
                room -> {
                    room.facts.containsCrevasse = true;
                    room.facts.crevasseDanger = 500;
                    room.facts.globalDanger = room.facts.monsterDanger + 500;
                },
                6
        );

        //Si le danger est nul alors la case ne présente pas de danger
        r7 = new Rule(
                room -> {
                    if(room.facts.globalDanger == 0) return true;
                    else return false;

                },
                room -> {
                    room.facts.isSafe = true;
                },
                7
        );

        //Si une case odorante ne possède qu'une seule adjacente contenant possiblement un monstre,
        // alors on admets que le monstre est présent sur cette case adjacente
        r8 = new Rule(
                room -> {
                    if (room.facts.isSmelly) {
                        int smellEmitters = 0;
                        for (Room neighbor : room.neighbors) {
                            if (!neighbor.facts.discoveredRoom && neighbor.facts.monsterDanger > 0)
                                smellEmitters++;
                        }
                        if (smellEmitters == 1) return true;
                        else return false;

                    } else return false;
                },
                room -> {
                    for (Room neighbor : room.neighbors) {
                        if (!neighbor.facts.discoveredRoom && neighbor.facts.monsterDanger > 0) {
                            neighbor.facts.monsterDanger = 50;
                            neighbor.facts.globalDanger = neighbor.facts.crevasseDanger + 50;
                            neighbor.facts.containsMonster = true;
                        }
                    }
                },
                8

        );

        //Si une case venteuse ne possède qu'une seule adjacente contenant possiblement une crevasse,
        // alors on admets que la crevasse est présent sur cette case adjacente
        r9 = new Rule(
                room -> {
                    if (room.facts.isWindy) {
                        int windEmitters = 0;
                        for (Room neighbor : room.neighbors) {
                            if (!neighbor.facts.discoveredRoom && neighbor.facts.crevasseDanger > 0)
                                windEmitters++;
                        }
                        if (windEmitters == 1) return true;
                        else return false;

                    } else return false;
                },
                room -> {
                    for (Room neighbor : room.neighbors) {
                        if (!neighbor.facts.discoveredRoom && neighbor.facts.crevasseDanger > 0) {
                            neighbor.facts.crevasseDanger = 500;
                            neighbor.facts.globalDanger = neighbor.facts.monsterDanger + 500;
                            neighbor.facts.containsCrevasse = true;
                        }
                    }
                },
                8

        );

        moteur.rulesList.add(r1);
        moteur.rulesList.add(r2);
        moteur.rulesList.add(r3);
        moteur.rulesList.add(r4);
        moteur.rulesList.add(r5);
        moteur.rulesList.add(r6);
        moteur.rulesList.add(r7);
        moteur.rulesList.add(r8);
        moteur.rulesList.add(r9);

        Collections.sort(moteur.rulesList);
        for(Rule rule : moteur.rulesList){
            moteur.markedRules.put(rule, new ArrayList<>());
        }
    }

}

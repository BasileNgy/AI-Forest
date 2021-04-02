import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Moteur {

    public ArrayList<Rule> rulesList;
    public ArrayList<Room> inferenceRooms;
    LinkedHashMap<Rule, ArrayList<Room>> applicableRules;
    HashMap<Rule, ArrayList<Room>> markedRules;

    public Moteur  (){
        rulesList = new ArrayList<>();
        inferenceRooms = new ArrayList<>();
        applicableRules = new LinkedHashMap<>();
        markedRules = new HashMap<>();
    }

    /*
    Applique le cycle d'inférence :
    - test les règles applicables
    - applique la règle
    - marque la règle
    tant que la liste de règles applicables n'est pas vide ou que toutes les règles applicable ne sont pas marquées
     */
    public void Inference(ArrayList<Room> inferenceRooms){

        this.inferenceRooms = inferenceRooms;
        applicableRules.clear();
        for(Rule rule : markedRules.keySet()){
            markedRules.get(rule).clear();
        }

        do{
            for (Rule rule : rulesList){
                applicableRules.put(rule, new ArrayList<>());
                IsRuleApplicable(rule);
                if(applicableRules.get(rule).isEmpty())
                    applicableRules.remove(rule);
            }
            if(!applicableRules.isEmpty()){
                Map.Entry<Rule, ArrayList<Room>> firstRuleApplicable = applicableRules.entrySet().iterator().next();
                Rule ruleToApply = firstRuleApplicable.getKey();
                Room roomToApply = firstRuleApplicable.getValue().get(0);
                ruleToApply.consequence.apply(roomToApply);
                markedRules.get(ruleToApply).add(roomToApply);
            }

        } while ( !applicableRules.isEmpty());

    }

    /*
    Test une règle sur l'ensemble des rooms, les règles applicables sont ajoutées à la liste
     */
    public void IsRuleApplicable(Rule rule){
        for(Room room : inferenceRooms){

            if(!markedRules.get(rule).contains(room) && rule.premise.check(room))
                applicableRules.get(rule).add(room);
        }
    }

}

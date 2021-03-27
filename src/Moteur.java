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

    public void Inference(ArrayList<Room> inferenceRooms){

        this.inferenceRooms = inferenceRooms;
        for(Room room : inferenceRooms ){
            //System.out.println("Interesting room in ["+room.x+","+room.y+"]");
        }
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
            //System.out.println("Applicable rules "+applicableRules);
            if(!applicableRules.isEmpty()){
                Map.Entry<Rule, ArrayList<Room>> firstRuleApplicable = applicableRules.entrySet().iterator().next();
                Rule ruleToApply = firstRuleApplicable.getKey();
                Room roomToApply = firstRuleApplicable.getValue().get(0);
                ruleToApply.consequence.apply(roomToApply);
                markedRules.get(ruleToApply).add(roomToApply);
                //System.out.println("Applied rule "+ruleToApply+" on room ["+roomToApply.x+","+roomToApply.y+"]");
            }
            //System.out.println("Marked rules " +markedRules);

        } while ( !applicableRules.isEmpty());

    }

    public void IsRuleApplicable(Rule rule){
        for(Room room : inferenceRooms){

            if(!markedRules.get(rule).contains(room) && rule.premise.check(room))
                applicableRules.get(rule).add(room);
            else ;//System.out.println("Can't apply rule "+rule+" to room ["+room.x+","+room.y+"]");
        }
    }

}

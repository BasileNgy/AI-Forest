interface RulePremise{
    boolean check(Room room);
}
interface RuleConsequence{
    void apply(Room room);
}

public class Rule implements Comparable{
    RulePremise premise;
    RuleConsequence consequence;


    public Rule( RulePremise _premise, RuleConsequence _consequence){
        premise = _premise;
        consequence = _consequence;
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
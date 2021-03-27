interface RulePremise{
    boolean check(Room room);
}
interface RuleConsequence{
    void apply(Room room);
}

public class Rule implements Comparable<Rule>{
    RulePremise premise;
    RuleConsequence consequence;
    int priority;


    public Rule( RulePremise _premise, RuleConsequence _consequence, int _priority){
        premise = _premise;
        consequence = _consequence;
        priority = _priority;
    }


    @Override
    public int compareTo(Rule rule)
    {
        return this.priority - rule.priority;
    }
}
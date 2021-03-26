interface RuleAction{
    void apply(Room room);
}

public class Rule {
    RuleAction premise;
    RuleAction consequence;


    public Rule( RuleAction _premise, RuleAction _consequence){
        premise = _premise;
        consequence = _consequence;
    }
}
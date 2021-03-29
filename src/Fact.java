import java.util.ArrayList;

public class Fact {

    public int x;
    public int y;
    public ArrayList<Element> elementList;
    /*TODO Si je dis pas de betise, quand on fais les tests pour les room connues etc on utilise isKnown, mais en fait
       on devrait checker discoveredRoom
       isKnown c'est justement quand tu tires une roche ou quand tu meurs sur une case, tu sais que y'a plus rien
       dessus donc t'updates tes fais en ajoutant isKnown
       Et les cases avec isKnown peuvent aps etre modifiees par des regles pour pas faire de la merde avec l'inférence
       genre si tu as une case connue avec une odeur adjacente avec une case de la frontiere sur laquelle t'as tiré une
       roche, faut pas que l'inférence dise "possiblement y'a un monstre ici"

     */
    public boolean discoveredRoom;

    public boolean isEmpty;
    public boolean isWindy;
    public boolean isSmelly;
    public boolean isShiny;

    //doutes
    public boolean mayContainMonster;
    public boolean mayContainCanyon;

    //certitudes
    public boolean isSafe;
    public boolean containsMonster;
    public boolean containsCanyon;
    public boolean containsExit;
    public boolean rockThrown;

    public int danger;

    public Fact(int x, int y)
    {
        this.x = x;
        this.y = y;
        elementList = new ArrayList<>();
        discoveredRoom = false;

        isEmpty = false;
        isShiny = false;
        isSmelly = false;
        isWindy = false;

        isSafe = false;

        mayContainMonster = false;
        mayContainCanyon = false;

        containsMonster = false;
        containsCanyon = false;
        containsExit = false;
        rockThrown = false;
    }

    @Override
    public String toString() {
        return "Fact{" +
                "x=" + x +
                ", y=" + y +
                ", elementList=" + elementList +
                ", discoveredRoom=" + discoveredRoom +
                ", isEmpty=" + isEmpty +
                ", isWindy=" + isWindy +
                ", isSmelly=" + isSmelly +
                ", isShiny=" + isShiny +
                ", mayContainMonster=" + mayContainMonster +
                ", mayContainCanyon=" + mayContainCanyon +
                ", isSafe=" + isSafe +
                ", containsMonster=" + containsMonster +
                ", containsCanyon=" + containsCanyon +
                ", containsExit=" + containsExit +
                ", danger=" + danger +
                '}';
    }

}

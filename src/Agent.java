import java.util.ArrayList;
import java.util.Collections;

public class Agent{

    private Player player;
    private Room[][] factsMap;
    private int size;
    public boolean exitReached;
    private Capteur capteur;
    private Effecteur effecteur;
    private Action nexAction;
    private ArrayList<Room> frontiereRooms;

    public Agent(Player p, Capteur capteur, Effecteur effecteur, int size)
    {
        this.player = p;
        this.capteur = capteur;
        this.effecteur = effecteur;
        this.size = size;

        factsMap = new Room[size][size];
        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
                factsMap[i][j] = new Room(i,j);

        frontiereRooms = new ArrayList<>();
    }
// competance autonomie lien social
    public void Resolution()
    {
        //Prise d'information avec les capteurs, update des faits
        UpdateFacts();
        //Test des règles, choix de l'action
        RulesApplication();
        //application de l'action choisie
        MakeAction();
    }

    private void MakeAction() {
        switch (nexAction)
        {
            case DROITE -> effecteur.Droite(player);
            case BAS -> effecteur.Bas(player);
        }

    }

    private void UpdateFacts() {
        if(capteur.isLumiere(player))
        {
            System.out.println("Je suis sur la sortie");
            factsMap[player.x][player.y].elementList.add(Element.SORTIE);
        }

        if (capteur.isVent(player))
        {
            SetUpDangerAdjacentesRoom(factsMap[player.x][player.y]);
            factsMap[player.x][player.y].elementList.add(Element.VENTEUSE);
            System.out.println("Je détecte du vent");
        }

        if (capteur.isOdeur(player))
        {
            SetUpDangerAdjacentesRoom(factsMap[player.x][player.y]);
            factsMap[player.x][player.y].elementList.add(Element.ODEUR);
            System.out.println("Je détecte une odeur");
        }

        if (capteur.isCrevasse(player) || capteur.isMonstre(player))
        {
            System.out.println("/////////////////////////////Je suis sur une case mortelle//////////////////////////////////");
        }

        factsMap[player.x][player.y].discoveredRoom = true;

        UpdateFrontieres();
    }

    /*
    Liste les rooms inconnus adjacentes aux rooms connues
     */
    private void UpdateFrontieres()
    {
        ArrayList<Room> knownRooms = new ArrayList<>();
        frontiereRooms.clear();

        //liste les rooms connues
        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
                if(factsMap[i][j].discoveredRoom)
                    knownRooms.add(factsMap[i][j]);

        //ajoute toutes les rooms adjacents non connues à la liste frontières
        for (Room r: knownRooms) {
            //UP
            if(r.y != 0)
                if(!factsMap[r.x][r.y-1].discoveredRoom && !frontiereRooms.contains(r))
                    frontiereRooms.add(r);
            //RIGHT
            if(r.x != size-1)
                if(!factsMap[r.x+1][r.y].discoveredRoom && !frontiereRooms.contains(r))
                    frontiereRooms.add(r);
            //DOWN
            if(r.y != size-1)
                if(!factsMap[r.x][r.y+1].discoveredRoom && !frontiereRooms.contains(r))
                    frontiereRooms.add(r);
            //LEFT
            if(r.x != 0)
                if(!factsMap[r.x-1][r.y].discoveredRoom && !frontiereRooms.contains(r))
                    frontiereRooms.add(r);
        }
    }

    private void RulesApplication()
    {
        Collections.sort(frontiereRooms);

        if(factsMap[player.x][player.y].elementList.contains(Element.SORTIE))
            nexAction = Action.SORTIR;

        if(factsMap[player.x+1][player.y].nivDanger <= factsMap[player.x][player.y+1].nivDanger)
            nexAction = Action.DROITE;
        else
            nexAction = Action.BAS;

        System.out.println("l'action que je vais faire : "+nexAction);
        //GetKnownRoomNextFrontiere(frontiereRooms.get(0));
    }

    private Room GetKnownRoomNextFrontiere(Room r)
    {
        Room knownRoom = new Room(20,20);

        //UP
        if(r.y != 0)
        {
            if(factsMap[r.x][r.y-1].discoveredRoom)
                knownRoom = factsMap[r.x][r.y-1];
        }
        //RIGHT
        else if(r.x != size-1)
        {
            if(factsMap[r.x+1][r.y].discoveredRoom)
                knownRoom = factsMap[r.x+1][r.y];
        }
        //DOWN
        else if(r.y != size-1)
        {
            if(factsMap[r.x][r.y+1].discoveredRoom)
                knownRoom = factsMap[r.x][r.y+1];
        }
        //LEFT
        else if(r.x != 0)
        {
            if(factsMap[r.x-1][r.y].discoveredRoom)
                knownRoom = factsMap[r.x-1][r.y];
        }

        return knownRoom;
    }

    /*
    augmente le niveau probable de danger d'une Room adjacent
     */
    private void SetUpDangerAdjacentesRoom(Room r)
    {
        //UP
        if(r.y != 0)
            if(!factsMap[r.x][r.y-1].discoveredRoom)
                factsMap[r.x][r.y-1].nivDanger ++;
        //RIGHT
        if(r.x != size-1)
            if(!factsMap[r.x+1][r.y].discoveredRoom)
                factsMap[r.x+1][r.y].nivDanger ++;
        //DOWN
        if(r.y != size-1)
            if(!factsMap[r.x][r.y+1].discoveredRoom)
                factsMap[r.x][r.y+1].nivDanger ++;
        //LEFT
        if(r.x != 0)
            if(!factsMap[r.x-1][r.y].discoveredRoom)
                factsMap[r.x-1][r.y].nivDanger ++;
    }
}

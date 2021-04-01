import java.util.Random;

public class Environnement
{

    public Room[][] map;
    private Room exit;
    int size;

    //Probabilités spawn
    float probaMonstre = 0.2f;
    float probaCrevasse = 0.1f;
    Player player;

    public Environnement(int n, Player player)
    {
        this.player = player;
        CreateNextForest(n);
    }

    public void CreateNextForest(int n){
        map = new Room[n][n];
        size = n;
        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
                map[i][j] = new Room(i,j);

    }

    /*
    Comptabilise les voisins et mets en place les différents éléments dans les rooms
     */
    public void SetUpInitialState()
    {
        SetUpNeighbourRoom();
        SetUpSortie();
        SetUpMonstreCrevasse();
        UpdateNeighborKnowledge();

        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
                map[i][j].SetGraphicText();
    }

    /*
    Liste les cases voisines les unes des autres et les ajoute à l'attribut neighbour
     */
    private void SetUpNeighbourRoom()
    {
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                Room r = map[i][j];
                //UP
                if(r.y != 0)
                    r.neighbors.add(map[r.x][r.y-1]);
                //RIGHT
                if(r.x != size-1)
                    r.neighbors.add(map[r.x+1][r.y]);
                //DOWN
                if(r.y != size-1)
                    r.neighbors.add(map[r.x][r.y+1]);
                //LEFT
                if(r.x != 0)
                    r.neighbors.add(map[r.x-1][r.y]);
            }
        }
    }

    /*
    Mets en place la sortie dans l'environnement de manière aléatoire, la sortie est unique et obligatoire
     */
    private void SetUpSortie() {
        Random rand = new Random();
        int randNbmX;
        int randNbmY;

        randNbmX = rand.nextInt(size);
        randNbmY = rand.nextInt(size);

        System.out.println("Sortie added to map [" + randNbmX + "," + randNbmY + "]");
        map[randNbmX][randNbmY].AddElement(Element.SORTIE);
        exit = map[randNbmX][randNbmY];
    }

    /*
    Ajoute des monstres et des crevasses selon les probabilités définis en attributs
     */
    private void SetUpMonstreCrevasse() {
        Random rand = new Random();
        float randMonster;
        float randCrevasse;

        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                randMonster = rand.nextFloat();
                randCrevasse = rand.nextFloat();

                if ( (i != player.x || j != player.y) && (i != exit.x || j != exit.y) ) {
                    if (randMonster < probaMonstre)
                        map[i][j].AddElement(Element.MONSTRE);

                    if (randCrevasse < probaCrevasse)
                        map[i][j].AddElement(Element.CREVASSE);
                }
            }
        }
    }

    /*
    Remets à jours les éléments Odeur et Vent de toutes les cases
     */
    public void UpdateNeighborKnowledge() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                Room room = map[i][j];

                if (room.elementList.contains(Element.MONSTRE) || room.elementList.contains(Element.CREVASSE)) {

                    for (Room neighbor : room.neighbors) {
                        if (room.elementList.contains(Element.MONSTRE)){
                            neighbor.AddElement(Element.ODEUR);

                        }

                        if (room.elementList.contains(Element.CREVASSE))
                            neighbor.AddElement(Element.VENTEUSE);

                    }
                }
            }
        }
    }


}

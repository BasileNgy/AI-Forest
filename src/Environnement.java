import java.util.Random;

public class Environnement
{

    public Room[][] map;
    int size;

    //Probabilités spawn
    float probaMonstre = 0.1f;
    float probaCrevasse = 0.1f;

    public Environnement(int n)
    {
        map = new Room[n][n];
        size = n;
        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
                map[i][j] = new Room(i,j);
    }

    public void SetUpInitialState()
    {
        SetUpSortie();
        SetUpMonstreCrevasse();

        for(int j=0;j<size;j++)
            for(int i=0;i<size;i++)
                map[i][j].SetGraphicText();
    }

    /*
    Mets en place la sortie dans l'environnement, la sortie est unique et obligatoire
    La probabilité augmente de 1/size² à chaque itération
     */
    private void SetUpSortie()
    {
        Random rand = new Random();
        float randNbm;
        float probaSortie = 1f/(size*size);

        outer:
        for(int j=0;j<size;j++)
        {
            for(int i=0;i<size;i++)
            {
                //sécurité pour s'assurer que la sortie soit assigné
                if(j == size-1 && i == size-1)
                    probaSortie = 1;

                randNbm = rand.nextFloat();

                if (randNbm <= probaSortie)
                {
                    map[i][j].AddElement(Element.SORTIE);
                    break outer;
                }
                else
                    probaSortie += probaSortie;
            }
        }
    }

    /*
    Ajoute des monstres et des crevasses selon les probabilités définis en attributs
     */
    private void SetUpMonstreCrevasse()
    {
        Random rand = new Random();
        float randMonster;
        float randCrevasse;

        for(int j=0;j<size;j++)
        {
            for(int i=0;i<size;i++)
            {
                randMonster = rand.nextFloat();
                randCrevasse = rand.nextFloat();

                if(i != 0 && j != 0)
                {
                    if (randMonster < probaMonstre)
                    {
                        if(map[i][j].AddElement(Element.MONSTRE))
                            SetUpAdjacentesRoom(map[i][j], Element.ODEUR);
                    }

                    if (randCrevasse < probaCrevasse)
                    {
                        if(map[i][j].AddElement(Element.CREVASSE))
                            SetUpAdjacentesRoom(map[i][j], Element.VENTEUSE);
                    }
                }
            }
        }
    }

    /*
    Ajoute l'élément Odeur ou Venteuse sur les cases adjacentes aux Monstres et aux Crevasses
     */
    private void SetUpAdjacentesRoom(Room r,Element e)
    {
        //UP
        if(r.y != 0)
            map[r.x][r.y-1].AddElement(e);
        //RIGHT
        if(r.x != size-1)
            map[r.x+1][r.y].AddElement(e);
        //DOWN
        if(r.y != size-1)
            map[r.x][r.y+1].AddElement(e);
        //LEFT
        if(r.x != 0)
            map[r.x-1][r.y].AddElement(e);
    }

}

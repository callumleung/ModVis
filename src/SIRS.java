import java.util.Random;

public class SIRS {


    //again data out put and analysis are omitted for now
    /*SIRS model of epidemics spreading
    S: An individual that is susceptible to infection
    I: An infected individual
    R: Recovered from infection

    S changes to I when a susceptible agent comes into contact with an
    infected one; recovery changes the agent from I to R; eventually, in the absence
    of contact with infected agents, the immune response wears off and R changes
    back to S.

    */

    //randomly sets each lattice to a state 0,1,2 corresponding to S,I,R
    static int S = 0;
    static int I = 1;
    static int R = 2;


    static void setLattice(int[][] lattice, int x, int y) {

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                double nextD = new Random().nextDouble();

                if (nextD<=((double) 1/3)) {
                    lattice[i][j] = 0;
                } else if ( nextD<=((double) 2/3)) {
                    lattice[i][j] = 1;
                } else {
                    lattice[i][j] = 2;
                }
            }
        }

    }


    //a method to return true if a cell has an infected neighbour and false if not
    private static boolean testI(int[][] lattice, int x, int y, int i, int j) {
        //x,y are size of lattice, i,j are the coords of the randomly selected cell
        int iup;
        int idown;
        int jup;
        int jdown;

        if(i == x-1) {iup=lattice[0][j];}
        else{ iup = lattice[i+1][j];}

        if(i == 0){idown= lattice[x-1][j];}
        else{ idown= lattice[i-1][j];}

        if(j == y-1){jup= lattice[i][0];}
        else{ jup = lattice[i][j+1];}

        if(j== 0){jdown = lattice[i][y-1];}
        else{ jdown = lattice[i][j-1];}

        // we now need to test for an I in a neighbouring cell
        if (iup == I || idown == I || jup == I || jdown == I) {
            return true;
        } else {
            return false;
        }

    }


    //updates a specific lattice location according to the progression rules
    /*
        An individual at location (i,j) in state S becomes infected (I) with probability p1, if it has at least one infected neighbour.
        Otherwise it remains in state S.

        A site in state I becomes recovered (R) with probability p2.

        A site in State R goes to state S with probability p3.
     */
    public static void SIRS(int[][] lattice,int i, int j,double p1, double p2, double p3){
        int x = lattice.length;
        int y = lattice[0].length;
        int S = 0;
        int I = 1;
        int R = 2;

        double rand =new Random().nextDouble();

        if (lattice[i][j] == S && testI(lattice, x, y, i, j)) {
            //S goes to I with prob p1 if at least one neighbour is I
            if (rand <= p1) {
                lattice[i][j] = I;
            } else {
            }
        }

        //for lattice[i][j] = I, goes to R with prob p2
        else if (lattice[i][j] == I && rand <= p2) {
            lattice[i][j] = R;
        }

        //if R goes to S with prob p3
        else if (lattice[i][j] == R &&  rand <= p3) {
            lattice[i][j] = S;

        }

    }


}

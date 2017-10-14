import java.util.Random;

public class SIRS {

    //again data out put and analysis are omitted for now
    /*SIRS model of epidemics spreading
    S: An individual that is susceptible to infection
    I: An infected individual
    R: Recovered from infection


    */

    static void setLattice(int[][] lattice, int x, int y) {


        int S = 0;
        int I = 1;
        int R = 2;


        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                //this should be random I think
                double nextD = new Random().nextDouble();


                //int rand= rnd.nextInt(3);
                //System.out.print( rand);
                if (nextD<=((double) 1/3)) {
                    lattice[i][j] = 0;
                } else if ( nextD<=((double) 2/3)) { //rand > 0.333333 &&
                    lattice[i][j] = 1;
                } else {
                    lattice[i][j] = 2;
                }
            }
        }

    }



}

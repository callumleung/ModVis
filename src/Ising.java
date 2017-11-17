import java.util.Random;
import java.util.stream.DoubleStream;

public class Ising {


    public static int setSpin(int r) {

        double x = Math.random();
        //System.out.println(x);
        if(x==0.5){x = Math.random();}
        else{
            if (x<0.5){
                x =-1.0;
            }
            else{
                x=1.0;
            }
        }
        r = (int) x;
        return r;
    }

    //a method to calculate the sum of the neighbours of a lattice location
    static int nNeighbours( int[][] lattice, int i, int j, int x, int y){

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


        int nn = iup + idown + jup + jdown;

        return nn;

    }


    //method to calculate the delta E
    //probably unused
    static double energy(int[][] lattice,int i, int j,int x, int y){

        int spin = lattice[i][j];
        int nn = nNeighbours(lattice, i, j, x, y);
        double E1 = -spin*nn;

        return  E1;
    }

    //a method to perform the glauber
    static void glauber(int[][] lattice, int x, int y, double temp){

        //choose a random x position and a random y position
        Random rand = new Random();

        int  i = rand.nextInt(x) + 0;
        // x is the max and 1 is the minimum
        int j = rand.nextInt(y) + 0;
        // we have our randomly chosen spin int[i][j]

        int spin = lattice[i][j];

        //energy is calculated according to E(S_i)=-J[sum over nearest neighbours{s_is_j}]
        //J and K_b have been set to 1
        //setting nearest neighbours and accounting for periodic boundary conditions

        int nn = nNeighbours(lattice, i, j, x, y);

        double E1 = -spin*nn;
        //flipping the spin
        spin = -spin;

        double E2 = -spin*nn;
        double deltaE = E2-E1;

        //calculate the probability for which the flip is performed if deltaE>0
        double prob = Math.exp(-1*deltaE / temp);


        //we now need to accept this flip for deltaE<0 and accept with probability P for deltaE>0

        if(deltaE < 0){
            lattice[i][j] = -lattice[i][j];
        }
        else{
            if(Math.random()<= prob) {
                // System.out.printf("%.10f  ", prob);
                lattice[i][j] = -lattice[i][j];
            }
            else{}


        }
    }


    //an alternative method to the Glauber method

    //kawasaki method
    static void kawasaki(int[][] lattice,int x, int y, double temp){
        //we first need to randomly choose our two distinct sites
        Random rand = new Random();

        int  i1 = rand.nextInt(x) + 0;

        int j1 = rand.nextInt(y) + 0;

        int i2 = rand.nextInt(x) +0;

        int j2 = rand.nextInt(y) +0;


        //again x,y are the maximums and 0 the minimum

        int spin1 = lattice[i1][j1];
        int spin2 = lattice[i2][j2];


        //calculating the nearest neighbour sum
        int temp1 = spin2;
        int temp2 = spin1;

        int iup;
        int idown;
        int jup;
        int jdown;

        if (i1 == x - 1) {
            iup = lattice[0][j1];
        } else {
            iup = lattice[i1 + 1][j1];
        }

        if (i1 == 0) {
            idown = lattice[x - 1][j1];
        } else {
            idown = lattice[i1 - 1][j1];
        }

        if (j1 == y - 1) {
            jup = lattice[i1][0];
        } else {
            jup = lattice[i1][j1 + 1];
        }

        if (j1 == 0) {
            jdown = lattice[i1][y - 1];
        } else {
            jdown = lattice[i1][j1 - 1];
        }


        int iup2;
        int idown2;
        int jup2;
        int jdown2;

        if (i2 == x - 1) {
            iup2 = lattice[0][j2];
        } else {
            iup2 = lattice[i2 + 1][j2];
        }

        if (i2 == 0) {
            idown2 = lattice[x - 1][j2];
        } else {
            idown2 = lattice[i2 - 1][j2];
        }

        if (j2 == y - 1) {
            jup2 = lattice[i2][0];
        } else {
            jup2 = lattice[i2][j2 + 1];
        }

        if (j2 == 0) {
            jdown2 = lattice[i2][y - 1];
        } else {
            jdown2 = lattice[i2][j2 - 1];
        }

        if( (j1 == j2 &&  (i2 == iup|| i2 == idown)) || (i1 == i2 && (j2 == jup || j2 == jdown)) ) {
            //do nothing
            }

        else {

            int nn = iup + idown + jup + jdown;
            int nn2 = iup2 + idown2 + jup2 + jdown2;

            double E1i = -spin1 * nn;
            double E2i = -spin2 * nn2;
            double Ei = E1i + E2i;

            double E1f = -temp1 * nn;
            double E2f = -temp2 * nn;
            double Ef = E1f + E2f;

            double deltaE = Ef - Ei;


            double prob = Math.exp(-deltaE / temp);

            //applying the markov chain
            if (deltaE < 0) {
                lattice[i1][j1] = temp1;
                lattice[i2][j2] = temp2;
            } else {
                if (Math.random() <= prob) {
                    lattice[i1][j1] = temp1;
                    lattice[i2][j2] = temp2;

                } else {
                }
            }


        }

    }


    //methods to carry out the iterations of the system and update the display
    static void simGlauber(int[][] lattice, int x, int y, double temp, double stepsPerSweep, Visualization vis){

        for (int i = 0; true; i++) {

            glauber(lattice, x, y, temp);

            //print after so many iterations
            if (i % stepsPerSweep == 0) {
                //Is it proper to call Main.anything() ?
                Main.visualize(vis, lattice, x, y, 2);
            }
        }
    }

    static void simGlauberData(int[][] lattice, int x, int y, double temp, double sw, int numMeasurements, Visualization vis, String outFileName){


        /* Want to keep track of:
        *      Total magnetisation
        *      Total magnetisation squared
        *      Total Energy
        *
        *       at each chosen multiple of sweeps want to add the above values
        *
        *       Create arrays to hold the values of measurements of mag and energy
        *
        *      The arrays will be summed at the end for total mag, mag^2 and energy
        *
         */

        double[] mags = new double[numMeasurements];
        double[] energies = new double[numMeasurements];

        int j = 2;


        for (int i = 0; true; i++) {
            glauber(lattice, x, y, temp);

            if (i == 100*sw) {
                mags[0] = IsingOutput.magnetisation(lattice, x, y);
                energies[0] = IsingOutput.latticeEnergy(lattice, x, y);
            }
            if ((i > 101*sw) && (i % sw == 0)){

                mags[j-1] = IsingOutput.magnetisation(lattice, x, y);
                energies[j-1] = IsingOutput.latticeEnergy(lattice, x, y);

                ++j;
            }

            if (j == numMeasurements){
                break;
            }
            //we now calculate the average magnetisation

            //sum the two arrays
            double magTot = DoubleStream.of(mags).sum();
            double energyTot = DoubleStream.of(energies).sum();


        }


    }

    static void simKawasaki(int[][] lattice, int x, int y, double temp, double sw, double s, Visualization vis){

        int j = 2;

        for (int i = 0; true; i++) {
            kawasaki(lattice, x, y, temp);

            if (i % s == 0) {
                Main.visualize(vis, lattice, x, y, 2);
            }


            if (i == 100*sw) {

                //data outputs
            }
            if (i > 101*sw && i%(sw) == 0) {
                j= j+1;
                //data outputs

            }

            if (j==1001){
                break;
            }
            //we now calculate the average magnetisation


        }

        //calculations and data output
    }

}

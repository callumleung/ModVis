import java.util.Random;

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

        int nn = nNeighbours(lattice, x, y, i, y);

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

}


//a series of methods used for data output and analysis in the Ising method

public class IsingOutput extends Ising{


    //a method to calculate the total magnetisation of a lattice
    public static double magnetisation(int[][] lattice, int x, int y){
        //x,y the size of the lattice
        double mag= 0.0;
        for (int i=0; i<x; i++){
            for(int j=0; j<y; j++){
                mag= mag + lattice[i][j];
            }
        }

        return mag;
    }

    //a method to calculate Chi of the system given the average magnetisation and magnetisation squared of N measurements of magnetisation
    public static double Chi(double avMag, double avMag2, double N, double temp){

        double var = avMag2 - (avMag*avMag);
        double chi = (1/(N*temp))*var;

        return chi;
    }

    //a method to calculate the total energy of a lattice
    //the energy at each lattice site is calculated and then summed
    //TODO performance can be increased by only counting half of the interactions per site, halving the number of calculations and removing need for halving the result. i.e up and to the right only

    public static double latticeEnergy(int[][] lattice, int x, int y){
        double totalE = 0.0;

        for (int i = 0; i<x; i++){
            for(int j = 0; j<y; j++){
                double energyij = energy(lattice, i, j, x,y);
                totalE = totalE +energyij;
            }
        }
        //to avoid over counting the energy we divide by two
        totalE = totalE/2;
        return totalE;
    }











}

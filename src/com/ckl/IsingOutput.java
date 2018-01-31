package com.ckl;
//a series of methods used for data output and analysis in the com.ckl.Ising method

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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
    //TODO remove if only used in below Sus method
    public static double Chi(double avMag, double avMag2, double N, double temp){

        double var = avMag2 - (avMag*avMag);
        double chi = (1/(N*temp))*var;

        return chi;
    }

    //susceptibility?
    // works out the mag and mag squared of N measurements and returns chi based on these values
    public static double Sus(double[] mags, double temp){

        //first find n (number of measurements)
        int N = mags.length;

        //we now need to pick N random measurements
        //in this case N = 1000
        double tempMag = 0.0;
        double tempMag2 = 0.0;

        //Random rand = new Random();
        for(int i = 0; i < N ; i++ ){
            tempMag = tempMag+ mags[i];
            tempMag2 = tempMag2 +(mags[i]*mags[i]);
        }

        //we now calculate the averages of tempMag and tempMag^2
        double avM = tempMag/N ;
        double avM2 = tempMag2/N;

        return Chi(avM, avM2, N, temp);
    }

    //same value as Sus but divded by the temp.
    //TODO is this necessary
    public static double Cv(double[] mags, double temp){
        return Sus(mags, temp)/temp ;
    }

    //a method to calculate the total energy of a lattice
    //the energy at each lattice site is calculated and then summed
    //TODO performance can be increased by only counting half of the interactions per site, halving the number of calculations and removing need for halving the result. i.e up and to the right only

    public static double latticeEnergy(int[][] lattice, int x, int y){
        double totalE = 0.0;

        for (int i = 0; i<x; i++){
            for(int j = 0; j<y; j++){
                double energyij = energy(lattice, i, j, x,y);
                totalE = totalE + energyij;
            }
        }
        //to avoid over counting the energy we divide by two
        totalE = totalE/2;
        return totalE;
    }

    //a bootstrap method to reduce the error on measurements
    public static double bootstrap(double[] mags, double temp){
        //a double to hold the running total of C
        double Ctot = 0.0;
        double C2tot = 0.0;
        int N = mags.length;


        for (int k=0 ; k<500; k++){
            //perform bootstrap
            double C = Sus(mags, temp);
            Ctot = Ctot + C;
            C2tot = C2tot + (C*C);

        }

        // we now calculate the averate of the k measurements of C
        double avC = Ctot/N;
        double avC2 = C2tot/N;

        //we now calculate sigma according to sqrt(bar{c^2}- bar{c}^2)

        double sigma = Math.sqrt((avC2-avC)*2/2500);


        return sigma;



    }


    static void simulateGlauber(int[][] lattice, int x, int y, double temp, double sw, double s, Visualization vis, boolean visTrue) throws IOException{

        BufferedWriter totEnergyOut = new BufferedWriter(new FileWriter("Total_Energy.dat"));

        int j = 2;

        for (int i = 0; true; i++) {
            glauber(lattice, x, y, temp);

            if (i % s == 0) {
                if (visTrue){
                Main.visualize(vis, lattice, x, y, 2);
                }
            }


            if (i == 100*sw) {

               totEnergyOut.write(String.valueOf(latticeEnergy(lattice, x, y)) + "\n");
                //data outputs
            }
            if (i > 101*sw && i%(sw) == 0) {
                j= j+1;
                totEnergyOut.write(String.valueOf(latticeEnergy(lattice, x, y)) + "\n");
                //data outputs

            }

            if (j==1001){
                totEnergyOut.write(String.valueOf(latticeEnergy(lattice, x, y)) + "\n");
                totEnergyOut.close();
                System.exit(0);
            }
            //we now calculate the average magnetisation

        }

        //calculations and data output
    }








}

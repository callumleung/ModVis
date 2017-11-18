package com.ckl;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        //select method

        //com.ckl.SIRS / Fisher /

        ising(50, 50,0.1, 2500.0, 7.0);

        //com.ckl.SIRS(50, 50, 1, 0.5, 0.4, 1);

    }


    //runs the ising method
    //arguments width
    //x,y are the dimensions of the system, temp in the temperature of the system, sw: number of increments per sweep, s: number of sweeps per print
    //lacking all data output. ie total energy, magnetisation and bootstrap method
    static void ising(int x, int y, double temp, double sw, double s){

        Scanner scan = new Scanner(System.in);

        //init buffered writers here

        //set up the system and the visualisation
        int[][] lattice = new int[x][y];
        Visualization vis = new Visualization(x,y);


        //for troubleshooting, setting all the lattice up or down (0, 1) is a useful step
        for(int i = 0; i <y;i++){
            for(int j=0; j < x; j++){
                lattice[i][j] = Ising.setSpin(lattice[i][j]);
            }
        }

        System.out.println("Enter 1 to use Glauber and 2 to use Kawasaki");

        //TODO change to enums

        int choice = Integer.valueOf(scan.nextLine());

        //glauber
        switch(choice) {
            default:  Ising.simGlauber(lattice, x, y, temp, s, vis);
                break;

            case 2:  Ising.simKawasaki(lattice, x, y, temp, sw, s, vis);
                break;

                //TODO: write data output versions for both glauber and kawasaki
            //case 3: com.ckl.Ising.simulateGlauberData();

            //case 4: com.ckl.Ising.simulateKawasakiData();

        }

    }


    static void SIRS(int x, int y, double p1, double p2, double p3, int steps){

        int sweep = x*y;
        int[][] lattice = new int[x][y];

        Visualization vis = new Visualization(x, y);
        Visualization.draw(vis, lattice, x, y, 3);

        SIRS.setLattice(lattice, x, y);
        for (int k = 0; true; k++) {
            if (k % steps == 0) {
                Visualization.draw(vis, lattice, x, y, 3);
            }

            //select a random site in the lattice
            Random rand = new Random();

            int i = rand.nextInt(x);
            // x is the max
            int j = rand.nextInt(y);

            SIRS.SIRS(lattice, i, j, p1, p2, p3);


        }

    }


}



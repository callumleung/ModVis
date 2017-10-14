import java.io.*;
import java.util.*;
import java.awt.Color;

public class Main {



    static void visualize(Visualization vis, int[][] grid, int W, int H, int D) {
        for (int col = 0; col < W; col++)
            for (int row = 0; row < H; row++)
                vis.set(col, row, grid[col][row] == 0 ? Color.BLACK : Color.getHSBColor(0.666666666666667f * (1f - Math.min(grid[col][row], D) / (float) D), 1f, 1f));

        vis.draw();
    }


    public static void main(String[] args) {

        //select method

        //SIRS / Fisher /

        ising(50, 50,0.1, 2500.0, 5.0, "glauber");

    }


    //runs the ising method
    //arguments width
    //x,y are the dimensions of the system, temp in the temperature of the system, sw: number of increments per sweep, s: number of sweeps per print
    //lacking all data output. ie total energy, magnetisation and bootstrap method
    static void ising(int x, int y, double temp, double sw, double s, String method){

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
        int choice = Integer.valueOf(scan.nextLine());

        //glauber
        switch(choice) {
           // case 1:  Ising.simulateGlauber(lattice, x, y, temp, sw, s, vis);

            case 2:  Ising.simulateKawasaki(lattice, x, y, temp, sw, s, vis);

            default:  Ising.simulateKawasaki(lattice, x, y, temp, sw, s, vis);
        }


        //kawasaki


        //




    }


}



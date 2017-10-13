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



    }


}

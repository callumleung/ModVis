package com.ckl;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

class Visualization extends Frame {

    private BufferedImage foreground;
    private BufferedImage background;

    Visualization(int width, int height) {
        foreground = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        background = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        setVisible(true);
        setSize(10*width, 5 * (height + getInsets().top));
        addWindowListener(new WindowAdapter() {public void windowClosing(WindowEvent event) {System.exit(0);}});
    }

    void set(int column, int row, Color color) {
        background.setRGB(column, row, color.getRGB());
    }

    void draw() {
        foreground.setData(background.getData());
        getGraphics().drawImage(foreground, 0, getInsets().top, getWidth(), getHeight() - getInsets().top, null);
    }

    public void paint(Graphics graphics) {
        graphics.drawImage(foreground, 0, getInsets().top, getWidth(), getHeight() - getInsets().top, null);
    }


    static void draw(Visualization vis, int[][] grid, int W, int H, int D) {
        for (int col = 0; col < W; col++)
            for (int row = 0; row < H; row++)
                vis.set(col, row, grid[col][row] == 0 ? Color.BLACK : Color.getHSBColor(0.666666666666667f * (1f - Math.min(grid[col][row], D) / (float) D), 1f, 1f));

        vis.draw();
    }

}

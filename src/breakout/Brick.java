package breakout;

import java.awt.*;

public class Brick {

    public int [][] map;
    public int brickWidth;
    public int brickHeight;

    public Brick(int row, int col) {
        map = new int[row][col];

        for (int i=0; i < map.length; i++) {
            for (int j=0; j < map[0].length; j++) {
                map[i][j] = 1;
            }
        }

        brickWidth = 540/col;
        brickHeight = 150/row;
    }

    public void draw(Graphics2D graphics2D) {
        for (int i=0; i < map.length; i++) {
            for (int j=0; j < map[0].length; j++) {
                if (map[i][j] > 0) {
                    // style bricks
                    graphics2D.setColor(Color.gray);
                    graphics2D.fillRect((j * brickWidth + 80), (i * brickHeight + 50), brickWidth, brickHeight);

                    graphics2D.setStroke(new BasicStroke(3));
                    graphics2D.setColor(Color.black);
                    graphics2D.drawRect((j * brickWidth + 80), (i * brickHeight + 50), brickWidth, brickHeight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col) {
        System.out.println("I was called");
        map[row][col] = value;
    }

}

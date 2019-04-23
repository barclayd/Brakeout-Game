package breakout;

import java.util.LinkedList;
import java.util.List;
import java.awt.*;
import java.util.Random;


public class Brick {

    public int [][] map;
    public int brickWidth;
    public int brickHeight;

    private Color[] coloursList = {Color.GREEN, Color.CYAN, Color.YELLOW, Color.ORANGE, Color.PINK, Color.PINK, Color.CYAN, Color.YELLOW, Color.WHITE, Color.BLUE};

    private List<Color> colours = new LinkedList<>();


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
        randomiseColours();
            for (int i=0; i < map.length; i++) {
                for (int j=0; j < map[0].length; j++) {
                    if (map[i][j] > 0) {
                        // style bricks
                        graphics2D.setColor(colours.get(i+j));
                        graphics2D.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);

                        graphics2D.setStroke(new BasicStroke(3));
                        graphics2D.setColor(Color.black);
                        graphics2D.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
                    }
                }
            }
    }

    public void setBrickValue(int value, int row, int col) {
        map[row][col] = value;
    }

    private Color getRandomColour() {
        Random rand = new Random();
        return colours.get(rand.nextInt(colours.size()));
    }

    private void randomiseColours() {
        for (int i = 0; i < 48; i++) {
            int index = new Random().nextInt(coloursList.length);
            colours.add(coloursList[index]);
        }
    }
}

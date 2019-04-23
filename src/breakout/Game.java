package breakout;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener {

    // set up config for game
    private boolean play = false;
    private int score = 0;

    // bricks
    private int brickRows = 3;
    private int brickColumns = 7;


    private int totalBricks = brickColumns * brickRows;

    private Timer timer;
    private int playerX = 310;

    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballXDirection = -1;
    private int ballYDirection = -2;

    private Brick brick;


    public Game() {
        int delay = 8;

        brick = new Brick(brickRows, brickColumns);

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics graphics) {
        // background
        graphics.setColor(Color.black);
        graphics.fillRect(1,1, 700, 600);

        // bricks
        brick.draw((Graphics2D) graphics);

        // border
        graphics.setColor(Color.blue);
        graphics.fillRect(0,0, 3, 592);
        graphics.fillRect(0,0, 697, 3);
        graphics.fillRect(697,0, 3, 592);
        graphics.fillRect(0,575, 697, 3);

        // score
        graphics.setColor(Color.white);
        graphics.setFont(new Font("sans-serif", Font.BOLD, 25));
        graphics.drawString(""+score, 590, 30);

        // paddle
        graphics.setColor(Color.white);
        graphics.fillRect(playerX, 550, 100, 8);

        // ball
        graphics.setColor(Color.orange);
        graphics.fillOval(ballPositionX, ballPositionY, 20, 20);

        // game over
        gameOver(graphics);

        // level complete
        levelComplete(graphics);

        graphics.dispose();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {
            ballLogic();
            ballBrickCollision();
            ballPaddleCollision();
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!play) {
                play = true;
                ballPositionX = 120;
                ballPositionY = 350;
                ballXDirection = -1;
                ballYDirection = -2;
                score = 0;
                totalBricks = brickRows * brickColumns;
                // reset bricks
                brick = new Brick(brickRows, brickColumns);
                repaint();
            }
        }

    }

    private void moveRight() {
        play = true;
        playerX += 20;

    }

    private void moveLeft() {
        play = true;
        playerX -= 20;
    }

    private void ballLogic() {
        ballPositionX += ballXDirection;
        ballPositionY += ballYDirection;

        // check for collisions with border top, left and right
        if (ballPositionX < 0) {
            ballXDirection = -ballXDirection;
        }

        if (ballPositionY < 0) {
            ballYDirection = -ballYDirection;
        }

        if (ballPositionX > 670) {
            ballXDirection = -ballXDirection;
        }
    }

    private void ballPaddleCollision() {
        if (new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
            ballYDirection = -ballYDirection;
        }
    }

    private void ballBrickCollision() {
        A: for (int i=0; i < brick.map.length; i++) {
            for (int j=0; j < brick.map[0].length; j++) {
                    if (brick.map[i][j] > 0) {
                        int brickX = j * brick.brickHeight + 80;
                        int brickY = i * brick.brickWidth + 50;

                        // instances
                        Rectangle ball = new Rectangle(ballPositionX, ballPositionY, 20, 20);
                        Rectangle singleBrick = new Rectangle(brickX, brickY, brick.brickWidth, brick.brickHeight);

                        if (ball.intersects(singleBrick)) {
                            brick.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;
                            // change ball direction after collision with bricks
                            if(ballPositionX + 19 <= singleBrick.x || ballPositionX + 1 >= singleBrick.x + singleBrick.width) {
                                ballXDirection = -ballXDirection;
                            }
                            else {
                                ballYDirection = -ballYDirection;
                            }
                            break A;
                        }
                    }
                }
            }

    }

    private void gameOver(Graphics graphics) {
        if (ballPositionY > 590) {
            play = false;
            ballXDirection = 0;
            ballYDirection = 0;

            // game over message
            graphics.setColor(Color.red);
            graphics.setFont(new Font("sans-serif", Font.BOLD, 32));
            graphics.drawString("Game Over! Final Score: "+score, (700/2) - 200, 300);
            graphics.setColor(Color.white);
            graphics.setFont(new Font("sans-serif", Font.PLAIN, 20));
            graphics.drawString("Press the space bar to play again", (700/2) - 150, 450);

        }
    }

    private void levelComplete(Graphics graphics) {
        if (totalBricks == 0) {
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("sans-serif", Font.BOLD, 32));
            graphics.drawString("Level Complete! Current Score: "+score, (700/2) - 250, 300);
            play = false;
        }
    }


}

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
    private int delay = 8;

    private int playerX = 310;

    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballXDirection = -1;
    private int ballYDirection = -2;

    private Brick brick;


    public Game() {
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


        // paddle
        graphics.setColor(Color.white);
        graphics.fillRect(playerX, 550, 100, 8);

        // ball
        graphics.setColor(Color.orange);
        graphics.fillOval(ballPositionX, ballPositionY, 20, 20);

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
                            score++;
                            // change ball direction after collision with bricks
                            if (ballPositionX + 19 <= singleBrick.x || ballPositionX + 1 >= singleBrick.x + singleBrick.width) {
                                ballXDirection -= ballXDirection;
                            } else {
                                ballYDirection -= ballYDirection;
                            }
                            break A;
                        }
                    }
                }
            }

    }



}

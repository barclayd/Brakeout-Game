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
    private int brickRows = 4;
    private int brickColumns = 12;


    private int totalBricks = brickColumns * brickRows;

    private Timer timer;

    // initial positions
    private int playerX = 310;
    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballXDirection = -1;
    private int ballYDirection = -2;

    // player controls
    private int playerXMovement = 20;

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
        graphics.setColor(Color.BLACK);
        graphics.fillRect(1,1, 700, 600);

        // draw bricks to screen
        brick.draw((Graphics2D) graphics);

        // border
        graphics.setColor(Color.BLUE);
        graphics.fillRect(0,0, 3, 592);
        graphics.fillRect(0,0, 697, 3);
        graphics.fillRect(697,0, 3, 592);
        graphics.fillRect(0,575, 697, 3);

        // score
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font("sans-serif", Font.BOLD, 25));
        graphics.drawString(""+score, 590, 30);

        // paddle
        graphics.setColor(Color.WHITE);
        graphics.fillRect(playerX, 550, 100, 8);

        // ball
        graphics.setColor(Color.ORANGE);
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
            // implement logic and check for collisions
            ballLogic();
            ballBrickCollision();
            ballPaddleCollision();
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}
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
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
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
        playerX += playerXMovement;

    }

    private void moveLeft() {
        play = true;
        playerX -= playerXMovement;
    }

    private void ballLogic() {
        ballPositionX += ballXDirection;
        ballPositionY += ballYDirection;

        // check for collisions with border top, left and right
        if (ballPositionX < 0 || ballPositionX > 670) {
            ballXDirection = -ballXDirection;
        }

        if (ballPositionY < 0) {
            ballYDirection = -ballYDirection;
        }
    }

    private void ballPaddleCollision() {
        if ((new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) || new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX + 30, 550, 40, 8)))  {
            ballYDirection = -ballYDirection;
            speedUpBall();
        }
        else if(new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX + 70, 550, 30, 8))) {
            ballYDirection = -ballYDirection;
            ballXDirection += 1;
            speedUpBall();
        }
    }

    private void ballBrickCollision() {
        A: for (int i = 0; i<brick.map.length; i++) {
            for (int j =0; j<brick.map[0].length; j++) {

                if (brick.map[i][j] > 0) {
                    int brickX = j * brick.brickWidth + 80;
                    int brickY = i * brick.brickHeight + 50;
                    int brickWidth = brick.brickWidth;
                    int brickHeight = brick.brickHeight;

                    Rectangle brickRectangle = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                    Rectangle ballRectangle = new Rectangle(ballPositionX, ballPositionY, 20, 20);

                    if (ballRectangle.intersects(brickRectangle)) {
                        brick.setBrickValue(0, i, j);
                        score+=5;
                        totalBricks--;

                        // collision between ball and right or left of a brick
                        if(ballPositionX + 19 <= brickRectangle.x || ballPositionX + 1 >= brickRectangle.x + brickRectangle.width) {
                            ballXDirection = -ballXDirection;
                            speedUpBall();
                        }
                        // collision between top and bottom of a brick
                        else {
                            ballYDirection = -ballYDirection;
                            speedUpBall();
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
            graphics.drawString("Level Complete! Current Score: "+score, (700/2) - 300, 300);
            play = false;
        }
    }

    private void speedUpBall() {
        ballXDirection *= 1.4;
        ballYDirection *= 1.4;
    }


}

package breakout;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class Game extends JPanel implements KeyListener, ActionListener {


    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPositionX = 120;
    private int ballPositionY = 350;
    private int ballXdirection = -1;
    private int ballYdirection = -2;


    public Game() {
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

        // borders
        graphics.setColor(Color.orange);
        graphics.fillRect(0,0, 3, 592);
        graphics.fillRect(0,0, 697, 3);
        graphics.fillRect(697,0, 3, 592);
        graphics.fillRect(0,575, 697, 3);


        // paddle
        graphics.setColor(Color.white);
        graphics.fillRect(playerX, 550, 100, 8);

        // ball
        graphics.setColor(Color.yellow);
        graphics.fillOval(ballPositionX, ballPositionY, 20, 20);

        graphics.dispose();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {
            ballLogic();
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
            if (playerX <= 10) {
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
        ballPositionX += ballXdirection;
        ballPositionY += ballYdirection;

        // check for collisions with border top, left and right
        if (ballPositionX < 0) {
            ballXdirection = -ballXdirection;
        }

        if (ballPositionY < 0) {
            ballYdirection = -ballYdirection;
        }

        if (ballPositionX > 670) {
            ballXdirection = -ballXdirection;
        }
    }

    private void ballPaddleCollision() {
        if (new Rectangle(ballPositionX, ballPositionY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
            ballYdirection = -ballYdirection;
        }
    }



}

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
        graphics.setColor(Color.BLACK);
        graphics.fillRect(1,1, 692, 592);

        // borders
        graphics.setColor(Color.ORANGE);
        graphics.fillRect(0,0, 3, 592);
        graphics.fillRect(0,0, 692, 3);
        graphics.fillRect(691,0, 3, 592);

        // paddle
        graphics.setColor(Color.WHITE);
        graphics.fillRect(playerX, 550, 100, 8);

        // ball
        graphics.setColor(Color.YELLOW);
        graphics.fillRect(ballPositionX, ballPositionY, 20, 20);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


}

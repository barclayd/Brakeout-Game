package breakout;

import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {

        // instances
        JFrame obj = new JFrame();
        Game game = new Game();

        // window properties
        obj.setBounds(10, 10, 700, 600);
        obj.setTitle("Breakout Game");
        obj.setResizable(false);
        obj.setVisible(true);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(game);
        // centre on screen
        obj.setLocationRelativeTo(null);
        obj.setVisible(true);
    }

}

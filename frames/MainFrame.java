package frames;

import frames.panels.DisplayPanel;
import maps.Stage;
import players.Player;
import players.Move;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame implements KeyListener
{
	private DisplayPanel dp;
	private Stage stage;
	private Player player;

	public MainFrame(String title)
	{
		// Set title
		super(title);

		// Set stage size (x, y)
		stage = new Stage(1200, 800);

        // Set player and connect it to stage (starting position x, y, speed, hitboxLength, Stage stage)
		player = new Player(200, 200, 10, 10, stage);
		
		// Prepare stage display panel
        // Add stage and player
		dp = new DisplayPanel(stage, player);

		// Make some walls
		// Draws a wall from a line b/w two points (x1, y1, x2, y2)
		stage.makeWall(100, 100, 1100, 100);
		stage.makeWall(1100, 100, 1100, 700);
		stage.makeWall(1100, 700, 100, 700);
		stage.makeWall(100, 700, 100, 100);

		// Add keyboard listener
		addKeyListener(this);

		// Add panel to MainFrame
		add(dp, BorderLayout.CENTER);

		// Standard procedure of JFrame
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	@Override
    public void keyTyped(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key typed");
            // Do nothing
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left key typed");
            // Do nothing
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("Up key typed");
            // Do nothing
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("Down key typed");
            // Do nothing
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        	System.out.println("Right key pressed");
            player.move(Move.RIGHT);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        	System.out.println("Left key pressed");
            player.move(Move.LEFT);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
        	System.out.println("Up key pressed");
            player.move(Move.UP);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        	System.out.println("DOWN key pressed");
            player.move(Move.DOWN);
            dp.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            System.out.println("Right key released");
            // Do nothing
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            System.out.println("Left key released");
            // Do nothing
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("Up key released");
            // Do nothing
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            System.out.println("Down key released");
            // Do nothing
        }
    }
}
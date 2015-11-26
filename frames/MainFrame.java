package frames;

import frames.panels.DisplayPanel;
import maps.Stage;
import maps.By;
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
	private Player player1;
    private Player player2;

	public MainFrame(String title)
	{
		// Set title
		super(title);

		// Set stage size (x, y)
		stage = new Stage(1200, 800);

        // (xStart, yStart, speed, hitboxLength, stage)
		player1 = new Player(200, 200, 3, 10, stage, By.PLAYER1);
        player2 = new Player(1000, 600, 3, 10, stage, By.PLAYER2);
		
		// Prepare stage display panel
        // Add stage and player1
		dp = new DisplayPanel(stage, player1, player2);

		// Make some walls
		// Draws a wall from a line b/w two points (x1, y1, x2, y2)
        // Has to be a rectangle!
		stage.makeWall(100, 100, 1100, 100);
		stage.makeWall(1100, 100, 1100, 700);
		stage.makeWall(1100, 700, 100, 700);
		stage.makeWall(100, 700, 100, 100);

        stage.makeWall(400, 400, 400, 600);
        stage.makeWall(400, 600, 600, 600);
        stage.makeWall(600, 600, 600, 400);
        stage.makeWall(600, 400, 400, 400);

        stage.makeWall(800, 150, 850, 150);
        stage.makeWall(850, 150, 850, 200);
        stage.makeWall(850, 200, 800, 200);
        stage.makeWall(800, 200, 800, 150);

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
            // Do nothing
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            // Do nothing
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            // Do nothing
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            // Do nothing
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        // Player 1 controls
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player1.releasedR = false;
            player1.move(Move.RIGHT);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player1.releasedL = false;
            player1.move(Move.LEFT);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player1.releasedU = false;
            player1.move(Move.UP);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player1.releasedD = false;
            player1.move(Move.DOWN);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            dp.releasedS = false;
            dp.addProjectile(player1);
            dp.repaint();
        }

        // Player 2 controls
        if (e.getKeyCode() == KeyEvent.VK_D) {
            player2.releasedR = false;
            player2.move(Move.RIGHT);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            player2.releasedL = false;
            player2.move(Move.LEFT);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player2.releasedU = false;
            player2.move(Move.UP);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            player2.releasedD = false;
            player2.move(Move.DOWN);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            dp.releasedSh = false;
            dp.addProjectile(player2);
            dp.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        // Player 1 controls
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player1.releasedR = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player1.releasedL = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player1.releasedU = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player1.releasedD = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            dp.releasedS = true;
        }

        // Player 2 controls
        if (e.getKeyCode() == KeyEvent.VK_D) {
            player2.releasedR = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            player2.releasedL = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            player2.releasedU = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            player2.releasedD = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            dp.releasedSh = true;
        }
    }
}
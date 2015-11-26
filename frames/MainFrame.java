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

        // (xStart, yStart, speed, hitboxLength, stage)
		player = new Player(200, 200, 3, 10, stage);
		
		// Prepare stage display panel
        // Add stage and player
		dp = new DisplayPanel(stage, player);

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
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.releasedR = false;
            player.move(Move.RIGHT);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.releasedL = false;
            player.move(Move.LEFT);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.releasedU = false;
            player.move(Move.UP);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.releasedD = false;
            player.move(Move.DOWN);
            dp.repaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            dp.releasedS = false;
            dp.addProjectile();
            dp.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.releasedR = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.releasedL = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            player.releasedU = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            player.releasedD = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            dp.releasedS = true;
        }
    }
}
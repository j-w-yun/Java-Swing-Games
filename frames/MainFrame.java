package frames;

import frames.panels.DisplayPanel;
import frames.panels.ButtonPanel;
import maps.Stage;
import maps.By;
import players.Player;
import players.Move;
import projectiles.Projectile;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.KeyListener;

public class MainFrame extends JFrame implements KeyListener
{
	private DisplayPanel dp;
	private Stage stage;
	private Player player1;
    private Player player2;

    // Constructor
	public MainFrame(String title)
	{
		// Set title
		super(title);

		// Set stage size (x, y)
		stage = new Stage(1200, 800);

        // (xStart, yStart, speed, hitboxLength, stage)
        // DEFAULT: (200, 200, 2, 10, stage, By.PLAYER#);
		player1 = new Player(200, 200, 2, 10, stage, By.PLAYER1);
        player2 = new Player(1000, 600, 2, 10, stage, By.PLAYER2);
		
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

        stage.makeWall(900, 300, 1000, 300);
        stage.makeWall(1000, 300, 1000, 400);
        stage.makeWall(1000, 400, 900, 400);
        stage.makeWall(900, 400, 900, 300);

        stage.makeWall(750, 550, 800, 550);
        stage.makeWall(800, 550, 800, 500);
        stage.makeWall(800, 500, 750, 500);
        stage.makeWall(750, 500, 750, 550);

        stage.makeWall(230, 380, 280, 380);
        stage.makeWall(280, 380, 280, 430);
        stage.makeWall(280, 430, 230, 430);
        stage.makeWall(230, 430, 230, 380);

		// Add keyboard listener
		addKeyListener(this);

		// Add panel to MainFrame
		add(dp, BorderLayout.CENTER);

		// Standard procedure of JFrame
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Create Timer for animations
        // (Time between next call (ms), action to be called)
        // 15ms yields 60 fps
        new Timer(10, paintTimer).start();

		setVisible(true);
	}

    // Timer associated
    Action paintTimer = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        {
            if(dp.projectiles != null)
            {
                for(int j = 0; j < dp.projectiles.size(); j++)
                {
                    Projectile temp = (Projectile) dp.projectiles.get(j);
                    temp.move();
                }
            }

            // Smooth movement
            // Player 1 movement
            if(!player1.releasedR)
            {
                player1.move(Move.RIGHT);
            }
            else if(!player1.releasedL)
            {
                player1.move(Move.LEFT);
            }
            else if(!player1.releasedD)
            {
                player1.move(Move.DOWN);
            }
            else if(!player1.releasedU)
            {
                player1.move(Move.UP);
            }

            // Player 2 movement
            if(!player2.releasedR)
            {
                player2.move(Move.RIGHT);
            }
            else if(!player2.releasedL)
            {
                player2.move(Move.LEFT);
            }
            else if(!player2.releasedD)
            {
                player2.move(Move.DOWN);
            }
            else if(!player2.releasedU)
            {
                player2.move(Move.UP);
            }

            if(stage.winner != null)
            {
                System.out.println(stage.winner);
                System.exit(0);
            }

            dp.repaint();
        }
    };


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
            // Do nothing.
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
            // Do nothing.
        }
    }
}
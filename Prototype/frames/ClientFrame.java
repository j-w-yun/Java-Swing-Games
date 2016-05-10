package frames;

import network.Client;
import maps.Stage;
import maps.By;
import players.Player;
import players.Move;
import frames.panels.DisplayPanel;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.event.KeyListener;

public class ClientFrame extends JFrame implements KeyListener
{
	private Client client;
	private DisplayPanel dp;
	private Stage stage;
	private Player player1;
    private Player player2;


	public ClientFrame(Client client)
	{
		super("Client");
		this.client = client;

		// Set stage size (x, y)
		stage = new Stage(1200, 800);

        // (xStart, yStart, speed, hitboxLength, stage)
        // DEFAULT: (200, 200, 2, 10, stage, By.PLAYER#);
		player1 = new Player(200, 200, 2, 10, stage, By.PLAYER1);
        player2 = new Player(1000, 600, 2, 10, stage, By.PLAYER2);
		
		// Prepare stage display panel
        // Add stage and player1
		dp = new DisplayPanel(stage, player1, player2);

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
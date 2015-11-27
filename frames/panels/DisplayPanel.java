package frames.panels;

import maps.Stage;
import maps.By;
import players.Player;
import players.Move;
import projectiles.Projectile;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DisplayPanel extends JPanel
{
	// For drawing pixels
	private Graphics g;

	// Get in constructor
	private Stage stage;
	private Player player1;
	private Player player2;

	// Projectiles
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	// Default wall color is Color.RED
	// Change by calling setWallColor();
	private Color wallColor = Color.CYAN;

	// Change by calling setPlayer#Color();
	private Color player1Color = new Color(0x24, 0x7a, 0xfd);
	private Color player2Color = new Color(0xfe, 0x2f, 0x4a);

	// Default projectile color is Color.CYAN
	// Change by calling setProjectileColor();
	private Color projectileColor = Color.CYAN;

	public DisplayPanel(Stage stage, Player player1, Player player2)
	{
		// Set background to black
		setOpaque(true);
		setBackground(Color.BLACK);

		this.stage = stage;
		this.player1 = player1;
		this.player2 = player2;

		// Set panel size new Dimension(x, y)
		setPreferredSize(new Dimension(stage.tile.length, stage.tile[0].length));

		// Create Timer for animations
		// (Time between next call (ms), action to be called)
		// 15ms yields 60 fps
		new Timer(15, paintTimer).start();
	}

	Action paintTimer = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e)
		{
			if(projectiles != null)
			{
				for(int j = 0; j < projectiles.size(); j++)
				{
					Projectile temp = (Projectile) projectiles.get(j);
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

			repaint();
		}
	};

	public void addProjectile(Player player)
	{
		// (xStart, yStart, facing, speed, hitboxLength, stage)
		Projectile temp = new Projectile(player.getPos()[0], player.getPos()[1], player.getFace(), 5, 5, stage);
		projectiles.add(temp);
	}


	@Override
	public void paintComponent(Graphics g)
	{
		// First line in paintComponent
		super.paintComponent(g);

		// To be used in other methods to fill a pixel
		this.g = g;

		// Determine colors
		for(int j = 0; j < stage.tile.length; j++)
		{
			for(int k = 0; k < stage.tile[0].length; k++)
			{
				if(stage.tile[j][k].occupied == By.WALL)
				{
					drawDot(j, k, wallColor);
				}
				else if(stage.tile[j][k].occupied == By.PLAYER1)
				{
					drawDot(j, k, player1Color);
				}
				else if(stage.tile[j][k].occupied == By.PLAYER2)
				{
					drawDot(j, k, player2Color);
				}
				else if(stage.tile[j][k].occupied == By.PROJECTILE)
				{
					drawDot(j, k, new Color(0xb7, 0xff, 0xfa));
				}
				else if(stage.tile[j][k].occupied == By.DEBRIS)
				{
					drawDot(j, k, new Color(0x66, 0xcc, 0xff));
				}
			}
		}
	}
	public void drawDot(int x, int y, Color color)
	{
		g.setColor(color);

		// Toggle thick lines: 4 pixels
		g.drawLine(x, y, x + 1, y + 1);
		g.drawLine(x + 1, y, x, y + 1);

		// Toggle thin lines: 1 pixel
		// g.drawLine(x, y, x, y);
	}

	// Mutators
	public void setWallColor(Color wallColor)
	{
		this.wallColor = wallColor;
	}
	public void setPlayer1Color(Color player1Color)
	{
		this.player1Color = player1Color;
	}
	public void setPlayer2Color(Color player2Color)
	{
		this.player2Color = player2Color;
	}
	public void setProjectileColor(Color projectileColor)
	{
		this.projectileColor = projectileColor;
	}
}
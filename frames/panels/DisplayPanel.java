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
	private Player player;

	// Projectiles
	ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

	// Default wall color is Color.CYAN
	// Change by calling setWallColor();
	private Color wallColor = Color.CYAN;

	// Default player color is Color.MAGENTA
	// Change by calling setPlayerColor();
	private Color playerColor = Color.MAGENTA;

	// Default projectile color is Color.YELLOW
	// Change by calling setProjectileColor();
	private Color projectileColor = Color.CYAN;

	public DisplayPanel(Stage stage, Player player)
	{
		// Set background to black
		setOpaque(true);
		setBackground(Color.BLACK);

		this.stage = stage;
		this.player = player;

		// Set panel size new Dimension(x, y)
		setPreferredSize(new Dimension(stage.tile.length, stage.tile[0].length));

		// Create Timer for animations
		// (Time between next call (ms), action to be called)
		// 15ms yields 60 fps
		new Timer(20, paintTimer).start();
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
			if(!player.releasedR)
			{
				player.move(Move.RIGHT);
			}
			if(!player.releasedL)
			{
				player.move(Move.LEFT);
			}
			if(!player.releasedD)
			{
				player.move(Move.DOWN);
			}
			if(!player.releasedU)
			{
				player.move(Move.UP);
			}
			repaint();
		}
	};

	public void addProjectile()
	{
		// (xStart, yStart, facing, speed, hitboxLength, stage)
		Projectile temp = new Projectile(player.getPos()[0], player.getPos()[1], player.getFace(), 5, 10, stage);
		
		projectiles.add(temp);
		System.out.println(projectiles.size());
		System.out.println(temp.xPos + " " + temp.yPos);
		System.out.println(stage.tile[temp.xPos][temp.yPos].occupied);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		// First line in paintComponent
		super.paintComponent(g);

		// To be used in other methods to fill a pixel
		this.g = g;

		// DEBUG
		//drawDot(player.getPos()[0], player.getPos()[1], playerColor);

		for(int j = 0; j < stage.tile.length; j++)
		{
			for(int k = 0; k < stage.tile[0].length; k++)
			{
				if(stage.tile[j][k].occupied == By.WALL)
				{
					drawDot(j, k, wallColor);
				}
				else if(stage.tile[j][k].occupied == By.PLAYER)
				{
					drawDot(j, k, playerColor);
				}
				else if(stage.tile[j][k].occupied == By.PROJECTILE)
				{
					drawDot(j, k, projectileColor);
				}
				else if(stage.tile[j][k].occupied == By.DEBRIS)
				{
					drawDot(j, k, Color.RED);
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

	// Set Color wallColor
	public void setWallColor(Color wallColor)
	{
		this.wallColor = wallColor;
	}
	public void setPlayerColor(Color playerColor)
	{
		this.playerColor = playerColor;
	}
	public void setProjectileColor(Color projectileColor)
	{
		this.projectileColor = projectileColor;
	}
}
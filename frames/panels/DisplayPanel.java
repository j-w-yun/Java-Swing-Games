package frames.panels;

import maps.Stage;
import maps.By;
import players.Player;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class DisplayPanel extends JPanel
{
	// For drawing pixels
	private Graphics g;

	// Get in constructor
	private Stage stage;
	private Player player;

	// Default wall color is Color.CYAN
	// Change by calling setWallColor();
	private Color wallColor = Color.CYAN;
	private Color playerColor = Color.MAGENTA;

	public DisplayPanel(Stage stage, Player player)
	{
		// Set background to black
		setOpaque(true);
		setBackground(Color.BLACK);

		this.stage = stage;
		this.player = player;

		// Set panel size new Dimension(x, y)
		setPreferredSize(new Dimension(stage.tile.length, stage.tile[0].length));
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
}
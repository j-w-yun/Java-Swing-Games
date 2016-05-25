// a static import of the constants that make Color and direction info (encoded in integer) easier to comprehend
import static util.Constants.*;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

class DisplayPanel extends JPanel implements Drawable, KeyListener
{
	private static final long serialVersionUID = 1;

	private final Color BACKGROUND_COLOR = Color.BLACK;
	private final Color GRID_COLOR = Color.LIGHT_GRAY;
	private final Color WALL_COLOR = Color.CYAN;
	private final Color PLAYER_COLOR = Color.ORANGE;
	private final Color MOVEABLE_WALL_COLOR = Color.GREEN;
	private Graphics g;

	private final int WIDTH = 800;
	private final int HEIGHT = 800;

	GameState gameState;
	Button resume;

	public DisplayPanel(Button resume)
	{
		this.resume = resume;
		setBackground(BACKGROUND_COLOR); // self explanatory
		setPreferredSize(new Dimension(WIDTH, HEIGHT)); // need to pass in Dimension

		// initialize GameState
		// send an instance of DisplayPanel to invoke repaint()
		gameState = new GameState(WIDTH, HEIGHT, this, 0, 0, true);

		// add KeyListener--which this class implements--to itself
		// KeyListener generates an action event which is then read to advance player
		addKeyListener(this);
		// this makes it so that DisplayPanel is focused for listening for user inputs
		// otherwise MainFrame (or any class that holds display as its component) has the focus
		setFocusable(true);
	}

	public void draw()
	{
		repaint();
	}

	// calling repaint()--an inherited method--invokes a call to this function!
	// always call this when there is a change in GameState
	@Override // this doesn't do anything but lets the reader know this method being overriden
	public void paintComponent(Graphics g)
	{
		// always call; not really being overriden in this sense
		super.paintComponent(g);

		// need to reference g from drawDot(), a helper method
		this.g = g;

		// we draw from this point
		int[][] gameGrid = gameState.getGrid();
		int pixelsPerGrid = gameState.pixelsPerGrid();
		for(int j = 0; j < gameGrid.length; j++)
		{
			for(int k = 0; k < gameGrid[j].length; k++)
			{
				// gameGrid[j][k] : blank is 0, player is 1, wall is 2
				drawDot(j, k, gameGrid[j][k]);
			}
		}
		drawGrid();
	}
	// helper method to draw a square
	public void drawDot(int x, int y, int color)
	{
		// blank is 0, player is 1, wall is 2
		if(color == BLANK)
			g.setColor(BACKGROUND_COLOR);
		else if(color == PLAYER)
			g.setColor(PLAYER_COLOR);
		else if(color == WALL)
			g.setColor(WALL_COLOR);
		else if(color == MOVEABLE_WALL)
			g.setColor(MOVEABLE_WALL_COLOR);

		int pixelsPerGrid = gameState.pixelsPerGrid();
		int widthGrid = (x * pixelsPerGrid) + 1;
		int heightGrid = (y * pixelsPerGrid) + 1;
		for(int j = 0; j < pixelsPerGrid - 1; j++)
		{
			g.drawLine(widthGrid, heightGrid + j,
				widthGrid + pixelsPerGrid - 2, heightGrid + j);
		}
	}
	// helper method to draw the grid
	public void drawGrid()
	{
		g.setColor(GRID_COLOR);

		int pixelsPerGrid = gameState.pixelsPerGrid();
		int widthGrid = (gameState.numGridsOnWidth() * pixelsPerGrid) + 1;
		int heightGrid = (gameState.numGridsOnHeight() * pixelsPerGrid) + 1;
		for(int j = 0; j < widthGrid + pixelsPerGrid; j += pixelsPerGrid)
		{
			g.drawLine(0, j, widthGrid, j);
		}
		for(int j = 0; j < heightGrid + pixelsPerGrid; j += pixelsPerGrid)
		{
			g.drawLine(j, 0, j, heightGrid);
		}
	}

	// these are provided in the KeyListener interface
	// to implement the interface, we must override all three methods below
	@Override public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			if(resume.isEnabled())
			{
				gameState.generateMap();
				resume.setEnabled(false);
				resume.setBackground(Color.LIGHT_GRAY);
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			try
			{
				gameState.advance(EAST);
				if(gameState.isComplete())
				{
					System.out.println("YAY!");
					resume.setEnabled(true);
					resume.setBackground(Color.GREEN);
				}
			}
			catch(Exception ex)
			{
				System.out.println("You cannot go there!");
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			try
			{
				gameState.advance(WEST);
				if(gameState.isComplete())
				{
					System.out.println("YAY!");
					resume.setEnabled(true);
					resume.setBackground(Color.GREEN);
				}
			}
			catch(Exception ex)
			{
				System.out.println("You cannot go there!");
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			try
			{
				gameState.advance(SOUTH);
				if(gameState.isComplete())
				{
					System.out.println("YAY!");
					resume.setEnabled(true);
					resume.setBackground(Color.GREEN);
				}
			}
			catch(Exception ex)
			{
				System.out.println("You cannot go there!");
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			try
			{
				gameState.advance(NORTH);
				if(gameState.isComplete())
				{
					System.out.println("YAY!");
					resume.setEnabled(true);
					resume.setBackground(Color.GREEN);
				}
			}
			catch(Exception ex)
			{
				System.out.println("You cannot go there!");
			}
		}

		if(e.getKeyCode() == KeyEvent.VK_SHIFT)
		{
			try
			{
				gameState.back();
			}
			catch(Exception ex)
			{
				System.out.println("You cannot go there!");
			}
		}
	}
	// do nothing with these for now
	@Override public void keyReleased(KeyEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}
}
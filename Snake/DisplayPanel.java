import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
*	@author Jaewan Yun (Jay50@pitt.edu)
*	@version 1.0.0
*/

public class DisplayPanel extends JPanel
{
	private Graphics g;
	private Color gridColor = Color.WHITE;
	private Color background = Color.GRAY;

	private final int HEIGHT = 1200;
	private final int WIDTH = 800;
	private final int DIV = 50;

	private int gridsX;
	private int gridsY;

	private int foodX;
	private int foodY;
	private boolean foodExists;
	private boolean doNotDelete;

	private JayList<Key> queue;

	private GameState state;

	public DisplayPanel()
	{
		setOpaque(true);
		setBackground(background);
		setPreferredSize(new Dimension(HEIGHT, WIDTH));

		repaint();

		gridsX = HEIGHT / DIV;
		gridsY = WIDTH / DIV;

		state = new GameState(gridsX, gridsY);
		queue = new JayList<Key>();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.g = g;

		drawGrid(gridColor);

		Set set = state.getKeys();
		Iterator it = set.iterator();

		while(it.hasNext())
		{
			Key read = (Key) it.next();
			drawDot(read.x, read.y, Color.CYAN);
		}

		if(foodExists)
			drawDot(foodX, foodY, Color.GREEN);
	}

	private void drawGrid(Color color)
	{
		g.setColor(color);

		for(int j = 0; j <= HEIGHT; j += DIV)
		{
			g.drawLine(j, 0, j, WIDTH);
		}
		for(int k = 0; k <= WIDTH; k += DIV)
		{
			g.drawLine(0, k, HEIGHT, k);
		}
	}

	public void food(int x, int y)
	{
		if(!foodExists)
		{
			foodX = x;
			foodY = y;

			foodExists = true;
		}
	}

	public void fill(int x, int y, Color color)
	{
		if(!foodExists)
		{
			food((new Random()).nextInt(gridsX), (new Random()).nextInt(gridsY));
		}

		if(x < 0 || x > gridsX - 1|| y < 0 || y > gridsY - 1)
			lose();

		if(x == foodX && y == foodY)
		{
			foodExists = false;
			doNotDelete = true;
		}

		Key key = state.acquireKey(x, y);

		if(state.occupied(key))
			lose();
		else
		{
			state.add(key, color);
			queue.addLast(key);
			if(queue.length() > 5 && !doNotDelete)
				state.delete(queue.removeFirst());
			doNotDelete = false;
		}
	}

	public void lose()
	{
		state.clear();
		System.out.println("INTERSECT");
		System.exit(0);
	}

	public void drawDot(int x, int y, Color color)
	{
		g.setColor(color);

		int gridx = (x * DIV) + 1;
		int gridy = (y * DIV) + 1;

		for(int j = 0; j < DIV - 1; j++)
		{
			g.drawLine(gridx, gridy + j, gridx + DIV - 2, gridy + j);
		}

		repaint();
	}
}
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class DisplayPanel extends JPanel
{
	private Graphics g;
	private Color gridColor = Color.WHITE;
	private Color background = Color.GRAY;

	private final int HEIGHT = 1080;
	private final int WIDTH = 720;

	private int gridsX;
	private int gridsY;

	private GameState state;

	public DisplayPanel()
	{
		setOpaque(true);
		setBackground(background);
		setPreferredSize(new Dimension(HEIGHT, WIDTH));

		repaint();

		gridsX = HEIGHT / 10;
		gridsY = WIDTH / 10;

		state = new GameState(gridsX, gridsY);
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
			// System.out.println(state.length());
		}
	}

	private void drawGrid(Color color)
	{
		g.setColor(color);

		for(int j = 0; j <= HEIGHT; j += 10)
		{
			g.drawLine(j, 0, j, WIDTH);
		}
		for(int k = 0; k <= WIDTH; k += 10)
		{
			g.drawLine(0, k, HEIGHT, k);
		}
	}

	public void fill(int x, int y, Color color)
	{
		Key key = state.acquireKey(x, y);

		if(state.occupied(key))
		{
			System.out.println("INTERSECT");
		}
		else
		{
			state.add(key, color);
		}
	}

	public void drawDot(int x, int y, Color color)
	{
		g.setColor(color);

		int gridx = (x * 10) + 1;
		int gridy = (y * 10) + 1;

		for(int j = 0; j < 9; j++)
		{
			g.drawLine(gridx, gridy + j, gridx + 8, gridy + j);
		}

		repaint();
	}
}
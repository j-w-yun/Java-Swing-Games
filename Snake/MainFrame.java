import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import javax.swing.*;

/**
*	@author Jaewan Yun (Jay50@pitt.edu)
*	@version 1.0.0
*/

public class MainFrame extends JFrame implements KeyListener
{
	private DisplayPanel dp;
	private int posx;
	private int posy;

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;

	private boolean wait;

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	public MainFrame(String title)
	{
		super(title);

		dp = new DisplayPanel();
		add(dp, BorderLayout.CENTER);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		centerWindow();

		addKeyListener(this);

		// My lazy game loop. Run with higher value on supercomputers :) vice versa for slower comps.
		// TODO : implement proper gameloop with variable timer setting for difficulty and game progression
		new Timer(70, paintTimer).start();

		posx = 10;
		posy = 10;

		right = false;
		left = false;
		up = false;
		down = false;

		setVisible(true);
	}

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	Action paintTimer = new AbstractAction()
	{
		@Override public void actionPerformed(ActionEvent e)
		{
			if(right)
			{
				dp.fill(++posx, posy, Color.BLACK);
				wait = false;
			}
			else if(left)
			{
				dp.fill(--posx, posy, Color.BLACK);
				wait = false;
			}
			else if(down)
			{
				dp.fill(posx, ++posy, Color.BLACK);
				wait = false;
			}
			else if(up)
			{
				dp.fill(posx, --posy, Color.BLACK);
				wait = false;
			}

			dp.repaint();
		}
	};

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	@Override public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT && right)
			return;
		if(e.getKeyCode() == KeyEvent.VK_LEFT && left)
			return;
		if(e.getKeyCode() == KeyEvent.VK_UP && up)
			return;
		if(e.getKeyCode() == KeyEvent.VK_DOWN && down)
			return;

		if(!wait)
		{
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			{
				if(!left)
				{
					right = true;
					left = false;
					up = false;
					down = false;

					wait = true;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
			{
				if(!right)
				{
					right = false;
					left = true;
					up = false;
					down = false;

					wait = true;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_DOWN)
			{
				if(!up)
				{
					right = false;
					left = false;
					up = false;
					down = true;

					wait = true;
				}
			}
			if(e.getKeyCode() == KeyEvent.VK_UP)
			{
				if(!down)
				{
					right = false;
					left = false;
					up = true;
					down = false;

					wait = true;
				}
			}
		}
	}

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	@Override public void keyReleased(KeyEvent e) {}

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	@Override public void keyTyped(KeyEvent e) {}

	/**
	*	@since 1.0.0
	*	@author Jaewan Yun (Jay50@pitt.edu)
	*/
	private void centerWindow()
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
}
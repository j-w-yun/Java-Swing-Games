import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyListener;
import javax.swing.*;

public class MainFrame extends JFrame implements KeyListener
{
	private DisplayPanel dp;
	private int posx;
	private int posy;

	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;

	public MainFrame(String title)
	{
		super(title);

		dp = new DisplayPanel();
		add(dp, BorderLayout.CENTER);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		centerWindow();

		addKeyListener(this);
		new Timer(10, paintTimer).start();

		posx = 50;
		posy = 50;
		right = false;
		left = false;
		up = false;
		down = false;

		setVisible(true);
	}

	Action paintTimer = new AbstractAction()
	{
		public void actionPerformed(ActionEvent e)
		{
			if(right)
			{
				dp.fill(++posx, posy, Color.BLACK);
			}
			else if(left)
			{
				dp.fill(--posx, posy, Color.BLACK);
			}
			else if(down)
			{
				dp.fill(posx, ++posy, Color.BLACK);
			}
			else if(up)
			{
				dp.fill(posx, --posy, Color.BLACK);
			}

			dp.repaint();
		}
	};

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			dp.fill(++posx, posy, Color.BLACK);
			right = true;
			left = false;
			up = false;
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			dp.fill(--posx, posy, Color.BLACK);
			right = false;
			left = true;
			up = false;
			down = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
		{
			dp.fill(posx, ++posy, Color.BLACK);
			right = false;
			left = false;
			up = false;
			down = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			dp.fill(posx, --posy, Color.BLACK);
			right = false;
			left = false;
			up = true;
			down = false;
		}
	}

	public void keyReleased(KeyEvent e)
	{

	}

	public void keyTyped(KeyEvent e)
	{

	}

	private void centerWindow()
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - getHeight()) / 2);
		setLocation(x, y);
	}
}
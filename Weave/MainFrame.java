import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class MainFrame extends JFrame implements ActionListener
{
	// ensure one instance is ever created
	private static MainFrame mf;
	private Button resume;
	private DisplayPanel dp;

	// static factory method ensures singleton from public
	// this is the only method in this package avaiable outside the package
	public static MainFrame getWindow(String title)
	{
		return (mf == null) ? mf = new MainFrame(title) : mf;
	}

	// private constructor
	private MainFrame(String title)
	{
		// standard stuff
		super(title);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// initializeMenuBar();


		resume = new Button("Next Map (Enter)");
		resume.setEnabled(false);
		resume.setBackground(Color.LIGHT_GRAY);
		dp = new DisplayPanel(resume); // my preference over constructors
		resume.addActionListener(this);
		add(dp, BorderLayout.CENTER); // add the display to this frame else game is not displayed
		add(resume, BorderLayout.SOUTH);


		setResizable(false);
		// make sure to call these methods after adding everything to the frame, and in this order
		pack(); // calling pack before adding stuff to this frame hides whatever is added after
		centerWindow(); // center the window after packing else it will most likely be off-center
		setVisible(true);

	}





	// nothing important below this point
	public void actionPerformed(ActionEvent e)
	{
		if(resume.isEnabled())
		{
			dp.gameState.generateMap();
			resume.setEnabled(false);
			resume.setBackground(Color.LIGHT_GRAY);
		}
	}
	private void initializeMenuBar()
	{
		JMenuBar menubar = new JMenuBar();
		// Tabs shown in the menu
		JMenu file = new JMenu("File");
		JMenu about = new JMenu("About");
		// Dropdown item
		JMenuItem fMenuItem_1 = new JMenuItem("Exit", null);
		fMenuItem_1.setMnemonic(KeyEvent.VK_E);
		fMenuItem_1.setToolTipText("Exit application");
		fMenuItem_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
		// Dropdown item
		JMenuItem aMenuItem_1 = new JMenuItem("Developers", null);
		aMenuItem_1.setMnemonic(KeyEvent.VK_D);
		aMenuItem_1.setToolTipText("Show developers");
		aMenuItem_1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent event)
			{
				JOptionPane.showMessageDialog(
					null,
					"Jaewan Yun\nZachary Hankinson",
					"Developers",
					-1);
			}
		});
		// Add items to tabs
		file.add(fMenuItem_1);
		about.add(aMenuItem_1);
		// Add tabs to menu
		menubar.add(file);
		menubar.add(about);
		// Add menu to JFrame
		setJMenuBar(menubar);
	}
	private void centerWindow()
	{
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - getHeight()) / 2);
	    setLocation(x, y - 15);
	}
}
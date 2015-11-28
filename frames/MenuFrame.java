package frames;

import frames.panels.ButtonPanel;

import java.awt.*;
import javax.swing.*;

public class MenuFrame extends JFrame
{
	private ButtonPanel bp;
	public MenuFrame(String title)
	{
		super("Prototype");

		bp = new ButtonPanel(this);

		add(bp);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}
}
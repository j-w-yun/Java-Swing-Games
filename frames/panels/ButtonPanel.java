package frames.panels;

import frames.MainFrame;
import network.Get;
import network.Server;
import network.Client;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ButtonPanel extends JPanel
{
	private JTextField myIP;
	private JTextField serverIP;
	private JButton host;
	private JButton join;
	private JButton offline;

	private Server server;
	private Client client;

	public ButtonPanel()
	{
		super();

		myIP = new JTextField(10);
		myIP.setEditable(false);
		myIP.setText(Get.ip());

		host = new JButton("Create Room");
		host.addActionListener(new ButtonListener());

		serverIP = new JTextField(10);
		serverIP.setText("192.168.");

		join = new JButton("Join Room");
		join.addActionListener(new ButtonListener());

		offline = new JButton("Play OffLine");
		offline.addActionListener(new ButtonListener());

		setLayout(new GridLayout(1, 5));

		add(myIP);
		add(host);
		add(serverIP);
		add(join);
		add(offline);

		new Timer(1000, paintTimer).start();
	}

	// Timer associated
    Action paintTimer = new AbstractAction()
    {
        public void actionPerformed(ActionEvent e)
        {
        	if(server != null)
        	{
	        	if(server.start)
	        	{
	        		new MainFrame("Server");
	        		server.start = false;
	        	}
	        }

	        if(client != null)
	        {
	        	if(client.start)
	        	{
	        		new MainFrame("Client");
	        		client.start = false;
	        	}
        	}
        }
    };

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == offline)
			{
				new MainFrame("Offline");
			}
			else if(e.getSource() == host)
			{
				server = new Server();
			}
			else if(e.getSource() == join)
			{
				client = new Client(serverIP.getText());
			}
		}
	}
}
package frames.panels;

import frames.MainFrame;
import frames.MenuFrame;
import frames.ClientFrame;
import network.Get;
import network.Server;
import network.Client;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class ButtonPanel extends JPanel
{
	MenuFrame mf;

	private JTextField myIPlabel;
	private JTextField myIP;
	private JTextField serverIP;
	private JTextField serverIPLabel;
	private JButton host;
	private JButton join;
	private JButton offline;
	private JButton about;

	private Server server;
	private Client client;

	public ButtonPanel(MenuFrame mf)
	{
		super();
		this.mf = mf;

		myIPlabel = new JTextField(10);
		myIPlabel.setEditable(false);
		myIPlabel.setText("Your IP Address");

		myIP = new JTextField(10);
		myIP.setEditable(false);
		myIP.setText(Get.ip());

		host = new JButton("Create Room");
		host.addActionListener(new ButtonListener());

		serverIPLabel = new JTextField(10);
		serverIPLabel.setEditable(false);
		serverIPLabel.setText("IP Address to Join");

		serverIP = new JTextField(10);
		serverIP.setText("192.168.");

		join = new JButton("Join Room");
		join.addActionListener(new ButtonListener());

		offline = new JButton("Play OffLine");
		offline.addActionListener(new ButtonListener());

		about = new JButton("About");
		about.addActionListener(new ButtonListener());

		setLayout(new GridLayout(0, 4));

		setBackground(Color.LIGHT_GRAY);

		add(myIPlabel);
		add(host);
		add(serverIPLabel);
		add(join);
		add(myIP);
		add(offline);
		add(serverIP);
		add(about);

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
	        		new ClientFrame(client);
	        		new ThreadOne(client).start();
	        		// new MainFrame("Client");
	        		client.start = false;
	        	}
        	}
        }
    };

    private class ThreadOne extends Thread
	{
		private Client client;
		public ThreadOne(Client client)
		{
			this.client = client;
		}
		public void run()
		{
			client.listen();
		}
	}

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == offline)
			{
				new MainFrame("Offline");
				mf.setVisible(false);
				mf.dispose();

			}
			else if(e.getSource() == host)
			{
				server = new Server();
			}
			else if(e.getSource() == join)
			{
				client = new Client(serverIP.getText());
			}
			else if(e.getSource() == about)
			{
				JOptionPane.showMessageDialog(null, "Developer:\nJaewan Yun\nJAY50@pitt.edu");
			}
		}
	}
}
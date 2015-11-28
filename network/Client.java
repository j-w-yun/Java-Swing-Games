package network;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client
{
	public boolean start = false;
	private Socket myClient = null;
	private DataInputStream input = null;
	private PrintStream output = null;

	public void listen()
	{
		while(myClient != null && input != null && output != null)
		{
			// Scanner sc = new Scanner(System.in);
			// output.println(sc.nextLine());
			// String response = input.readLine();
			// System.out.println("Server: " + response);
		}
	}

	public Client(String args)
	{
		System.out.println("Trying " + args);
		try
		{
			// PortNumber has to be greater than 1,023
			// Because 0 through 1,023 are reserved for privileged users
			myClient = new Socket(args, 2333);

			input = new DataInputStream(myClient.getInputStream());

			output = new PrintStream(myClient.getOutputStream());

			System.out.println("Connected to server.");
			start = true;

		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}
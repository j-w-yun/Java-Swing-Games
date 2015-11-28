package network;

import frames.panels.ButtonPanel;

import java.io.*;
import java.net.*;

public class Server
{
	public boolean start = false;

	public Server()
	{
		// Open a socket for the server
		ServerSocket myService = null;

		// Create a socket object from ServerSocket
		// In order to listen for and accept connections
		Socket serviceSocket = null;

		// Create an input stream to receive input from the client
		DataInputStream input = null;

		// DataOutputStream allows writing primitive data types
		PrintStream output = null;

		try
		{
			// PortNumber has to be greater than 1,023
			// Because 0 through 1,023 are reserved for privileged users
			myService = new ServerSocket(2333);

			serviceSocket = myService.accept();

			input = new DataInputStream(serviceSocket.getInputStream());

			output = new PrintStream(serviceSocket.getOutputStream());
			
			System.out.println("Connected to client.");
			start = true;

			while(true)
			{
				// String temp = input.readLine();
				// output.println(temp);
				// System.out.println(temp);
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}
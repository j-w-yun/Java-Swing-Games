package network;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client
{
	public static void connect()
	{
		// Open a socket for the client
		Socket myClient = null;

		// Create an input stream to receive response from the server
		DataInputStream input = null;

		// DataOutputStream allows writing primitive data types
		PrintStream output = null;

		try
		{
			// Open text file with stored IP
			File file = new File("ip.txt");
			Scanner fileReader = new Scanner(file);
			String ip = fileReader.nextLine();

			// PortNumber has to be greater than 1,023
			// Because 0 through 1,023 are reserved for privileged users
			myClient = new Socket(ip, 2333);

			input = new DataInputStream(myClient.getInputStream());

			output = new PrintStream(myClient.getOutputStream());

			System.out.println("Connected to server.");

			while(myClient != null && input != null && output != null)
			{
				Scanner sc = new Scanner(System.in);
				output.println(sc.nextLine());
				String response = input.readLine();
				System.out.println("Server: " + response);
			}
		}
		catch(IOException e)
		{
			System.out.println(e);
		}

		// Close output and input stream before closing socket
		try
		{
			output.close();
			input.close();
			myClient.close();
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}
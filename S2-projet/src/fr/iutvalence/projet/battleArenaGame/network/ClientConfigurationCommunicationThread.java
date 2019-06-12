package fr.iutvalence.projet.battleArenaGame.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import fr.iutvalence.projet.battleArenaGame.UserController;

public class ClientConfigurationCommunicationThread extends Thread
{
	private Socket s;
	private UserController userController;
	
	public ClientConfigurationCommunicationThread(Socket sock, UserController userController )
	{
		super();
		this.userController = userController;
		this.s = sock;
	}
	
	public void run()
	{
		BufferedReader bufr = null;
		try {
			bufr = new BufferedReader(new InputStreamReader(s.getInputStream()));
			bufr.readLine();

		}catch(IOException e)
		{}
		
		
	}
}

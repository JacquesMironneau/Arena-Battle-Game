package fr.iutvalence.projet.battleArenaGame.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import fr.iutvalence.projet.battleArenaGame.User;

public class ClientConfigurationCommunicationThread extends Thread
{
	private Socket s;
	
	public ClientConfigurationCommunicationThread(Socket sock, User user)
	{
		super();
		this.s = sock;
	}
	
	public void run()
	{
		BufferedWriter bufw = null;
		try {
			bufw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			bufw.append(msg);
			
		}catch(IOException e)
		{}
		
		
	}
}

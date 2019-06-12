package fr.iutvalence.projet.battleArenaGame.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class GameLauncherServerClientHandler 
{

	
	private ArrayList<ClientConnectionInfo> clientList;
	
	private int nbPlayers;
	
	
	public GameLauncherServerClientHandler(int nbPlayer)
	{
		this.nbPlayers = nbPlayer;
		this.clientList = new ArrayList<ClientConnectionInfo>();
	
		while(this.clientList.size() < this.nbPlayers)
		{
			int connectedPlayers = this.clientList.size()+1; // +1 : pour compter l'host 
			this.broadcast("Joueurs connectés : "+ connectedPlayers+"/"+nbPlayers);
		}
	}
	
	private void broadcast(String msg)
	{
		for(ClientConnectionInfo c: this.clientList)
		{
			try
			{
				c.getWriter().append(msg + "\n");
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void addClient(Socket s)
	{
		try
		{
			this.clientList.add(new ClientConnectionInfo(new BufferedReader(new InputStreamReader(s.getInputStream())), new BufferedWriter(new OutputStreamWriter(s.getOutputStream())),s));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}

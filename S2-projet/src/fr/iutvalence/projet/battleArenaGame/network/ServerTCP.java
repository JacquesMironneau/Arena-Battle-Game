package fr.iutvalence.projet.battleArenaGame.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerTCP
{
	public final static int PORT = 19999;
	
	/**
	 * How many host are connected (players playing on the same node as the server)
	 */
	public final static int NUMBER_PLAYER_BASIS = 1;
	
	private int nbPlayers;
	
	private int currentNbPlayers;
	
	private ArrayList<Socket> socketList;
	
	public ServerTCP(int nbPlayers)
	{
		this.socketList = new ArrayList<Socket>();
		this.nbPlayers = nbPlayers;
		this.currentNbPlayers = NUMBER_PLAYER_BASIS;
		ServerSocket ss;
		try
		{
			ss = new ServerSocket(ServerTCP.PORT);
			while(this.currentNbPlayers < this.nbPlayers)
			{
				Socket socketClient = ss.accept(); 
				this.socketList.add(socketClient);
				new Thread(() ->
				{
					broadcast(socketClient);
				}).start();
				this.currentNbPlayers++;
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		

	}
	
	public ArrayList<Socket> getSockets()
	{
		return this.socketList;
	}

	private void broadcast(Socket socket)
	{
		OutputStream i = null;
		try
		{
			i = socket.getOutputStream();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			BufferedWriter b;
			b = new BufferedWriter(new OutputStreamWriter(i));
			b.append("Connected"+this.currentNbPlayers+"/"+this.nbPlayers+"\n");
			b.flush();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}

package fr.iutvalence.projet.battleArenaGame.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientTCP
{
	
	private Socket socket;
	
	public ClientTCP(String ip)
	{
		try
		{
			socket = new Socket(ip, ServerTCP.PORT);
			
			BufferedReader bufr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new Thread(() -> {
			while(true)
				try
				{
					System.out.println(bufr.readLine()); //TODO move to view 
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}).start();
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 *Getter
	 */
	public Socket getSocket()
	{
		return this.socket;
	}
	
}

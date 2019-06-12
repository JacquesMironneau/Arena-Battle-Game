package fr.iutvalence.projet.battleArenaGame.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ClientTCP
{
	
	private Socket s;
	
	public ClientTCP(String ip)
	{
		try
		{
			s = new Socket(ip, ServerTCP.PORT);
			
			BufferedReader bufr = new BufferedReader(new InputStreamReader(s.getInputStream()));
			new Thread(() -> {
			while(true)
				try
				{
					//TODO view ! and not syso 
					System.out.println(bufr.readLine());
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
	
	public Socket getSocket()
	{
		return this.s;
	}
	
	public static void main(String[] args)
	{
		new Thread(() -> {
			ArrayList<GameClientHandler> sl = new ArrayList<GameClientHandler>();
			for(Socket s :new ServerTCP(4).getSockets())
				sl.add(new GameClientHandler(s));
			
			for(GameClientHandler c :sl)
			{
				System.out.println(c);
			}
		}).start();
		
		new ClientTCP("127.0.0.1");
		new Thread(() ->
		{
			new ClientTCP("127.0.0.1");	
		}).start();
		
		new ClientTCP("127.0.0.1");

		
	}
}

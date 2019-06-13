package fr.iutvalence.projet.battleArenaGame.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TestNetwork5
{
	public static void main(String[] args) throws IOException
	{
		new Thread(() -> {
			try
			{
				new BufferedWriter(new OutputStreamWriter(new ServerTCP(2).getSockets().get(0).getOutputStream())).append("OLLA\n").flush();
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
		}).start();
		
		
		new Thread(() -> {
			try
			{
				System.out.println(new BufferedReader(new InputStreamReader(new ClientTCP("127.0.0.1").getSocket().getInputStream())).readLine());
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}).start();
				
	}
}

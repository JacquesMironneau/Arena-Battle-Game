package fr.iutvalence.projet.battleArenaGame.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class GameLauncherServerClientHandler 
{

	private int port;
	
	private BufferedReader serverBufReader;
	
	private BufferedWriter serverBufWriter;
	
	private BufferedReader toClientBufReader;
	
	private BufferedWriter toClientBufWriter;
	
	private ServerSocket serverSocket;
	
	private Socket clientSocket;
	
	public GameLauncherServerClientHandler(int port)
	{
		this.port = port;
		this.serverSocket = null;
		
		try {
			this.serverSocket = new ServerSocket(port);
			this.clientSocket = this.serverSocket.accept();

			this.toClientBufReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			this.toClientBufWriter = new BufferedWriter(new OutputStreamWriter(this.clientSocket.getOutputStream()));
			
		}catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void initClient()
	{
		try {
			Socket socket = new Socket(InetAddress.getLoopbackAddress(), 19999);
			this.serverBufReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.serverBufWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));


		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	
	
}

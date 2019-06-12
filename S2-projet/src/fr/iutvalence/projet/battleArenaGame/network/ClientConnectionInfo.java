package fr.iutvalence.projet.battleArenaGame.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
/**
 * Information of clients
 * including his socket and his differents streams (to send and receive)
 * @author pashmi
 *
 */
public class ClientConnectionInfo
{
	private BufferedReader reader;
	
	private BufferedWriter writer;
	
	private Socket clientSocket;

	
	public ClientConnectionInfo(BufferedReader reader, BufferedWriter writer, Socket clientSocket)
	{
		this.reader = reader;
		this.writer = writer;
		this.clientSocket = clientSocket;
	}


	/*
	 * Getters
	 */
	public BufferedReader getReader()
	{
		return reader;
	}


	public BufferedWriter getWriter()
	{
		return writer;
	}


	public Socket getClientSocket()
	{
		return clientSocket;
	}


	@Override
	public String toString()
	{
		return "ClientConnectionInfo [reader=" + reader + ", writer=" + writer + ", clientSocket=" + clientSocket + "]";
	}
	
	
}

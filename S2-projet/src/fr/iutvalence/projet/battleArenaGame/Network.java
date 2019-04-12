package fr.iutvalence.projet.battleArenaGame;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents the network of the game it enables the system to send and receive data
 * @author durantho
 *
 */
public class Network {
	private Socket socket;
	private InetAddress myAddr;
	private ServerSocket lobby;
	private int listeningPort;
}

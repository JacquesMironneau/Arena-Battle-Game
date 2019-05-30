 package fr.iutvalence.projet.battleArenaGame.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import fr.iutvalence.projet.battleArenaGame.Game;

/**
 * Client class represent one of the player who can connect to the server, receive data or send others to the server
 * In the future, player will be able to discover the IP of the server just with the 2 last methods.(With udp features, soon done)
 * For now it does use the IP address in the constructor.
 * 
 * @author pashmi
 *
 */

public class Client implements Communication
{

    //The port of the server
	private int port;
	
	//The address of the server
    private String serverAddress;
    
    //Where we write/send object
    private ObjectInputStream in;
    
    //Where we read object
    private ObjectOutputStream out;
    
    //Socket of the client
    private Socket socket;
    
    //Network of the system: Used to translate received object into methods calls
    private Network myNetwork;
    
    
    /**
     * Constructor for the client
     * create a client ready to connect to a server with a given port and a given address.
     * @param port: Port of communication with the server
     * @param address : address of the server
     */
    public Client(int port, String address, Network pNetwork)
    {
        this.port = port;
        this.serverAddress = address;
        this.myNetwork = pNetwork;
    }
    
    /**
     * Here the player try to connect to the server
     * And create the input and output stream in order to send and receive data
     * 
     */
    public void init() 
    {
        System.out.println("Client!");

        //Ask for connection to the server
        try {
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to : " + serverAddress +" (" + socket.getInetAddress().getHostName() + ")");

            //It get the outputStream and inputStream from the client socket

            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Initialized out");

            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Initialized in");


            /*
             * Create a thread in order to listen the  data sent by the server
             */
            
           Thread t_listener = new Thread(this::receive);

            t_listener.start();
        }

        catch (IOException io){
        	io.printStackTrace();
        }
    }

    //Used in a thread
    /**
     * Listen to the server and readObject in the inputStream 
     */
	private void receive()
    {

        while(socket.isConnected()){
        	Object obj_receive = null;
            try{
                
            	
                obj_receive = this.in.readObject();
                System.out.println(socket.getInetAddress() + " (server) : " + obj_receive);
                if(obj_receive != null)
                	myNetwork.Transform(obj_receive);
                
            }
            catch(Exception io){
               //TODO Fix the few exceptions that are coming 
            	io.printStackTrace();
            }
        }
    }

    /**
     * Method to call in order to disconnect/close the socket to the server 
     */
    public void Disconnect()
    {
        if(socket.isConnected()){
            this.sendToOther(Game.QUIT);

            try{
                socket.close();
            }
            catch(IOException io){
                io.printStackTrace();
            }
        }
        else {
            System.out.println("Can't disconnect : not connected");
        }
    }

    /**
     * Send an object to the server
     * by writing in the outputStream the object
     * @param o object written in the output stream
     */
    public void sendToOther(Object o)
    {
        if(socket.isConnected()){
            try
            {
                out.writeObject(o);
            }
            catch(IOException io)
            {
                io.printStackTrace();
            }
        }
        else {
            System.out.println("Can't send : not connected");
        }
    }


}

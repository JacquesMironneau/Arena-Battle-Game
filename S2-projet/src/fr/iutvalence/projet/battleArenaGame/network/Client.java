package fr.iutvalence.projet.battleArenaGame.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Client class represent one of the player who can connect to the server, receive data or send others to the server
 * In the future, player will be able to discover the ip of the server just with the 2 last methods.
 * For now it does use the ip address in the constructor.
 * @author pashmi
 *
 */

//TODO: move constants to game class (msg_udp)
public class Client
{
    public static final byte MSG_UDP = 1;

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

    
    /**
     * Constructor for the client
     * create a client ready to connect to a server with a given port and a given address.
     * @param port: Port of communication with the server
     * @param address : address of the server
     */
    public Client(int port, String address)
    {
        this.port = port;
        this.serverAddress = address;
    }
    
    /**
     * Here the player try to connect to the server
     * And create the input and output stream in order to send and receive data
     * 
     */
    public void connect() 
    {
        System.out.println("Client!");

        //Ask for connection to the server
        try {
            socket = new Socket(serverAddress, port);
            System.out.println("Connected to : " + serverAddress);

            //It get the outputStream and inputStream from the client socket

            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Initialized out");

            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Initialized in");


            /*
             * Create a thread in order to listen the  data sent by the server
             */
            Thread t_listener = new Thread(this::Receive);

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
    private void Receive()
    {

        while(socket.isConnected()){
            try{
                Object obj_receive = in.readObject();
                System.out.println(socket.getInetAddress() + " (server) : " + obj_receive);
            }
            catch(Exception io){
                io.printStackTrace();
            }
      /*      catch (ClassNotFoundException e) {
                e.printStackTrace();
            }*/


        }

    }

    /**
     * Method to call in order to disconnect/close the socket to the server 
     */
    public void Disconnect()
    {
        if(socket.isConnected()){
            this.Send(Server.QUIT);

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
     * @param o object written in the outputstream
     */
    public void Send(Object o)
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

    /**
     * 
     * @return if the socket of the client is connected
     */
    public boolean isConnected()
    {
        return socket.isConnected();
    }

    /**
     * NOT USED YET, WORK IN PROGRESS
     * @return
     */
    public HashSet<String> discoverNetwork()
    {
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        NetworkInterface networkInterface = null;
        try {
            networkInterface = NetworkInterface.getByInetAddress(localHost);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //Local address
        byte[] local_byte = localHost.getAddress();

        //Get the mask
        byte[] mask = networkInterface.getInterfaceAddresses().get(0).getBroadcast().getAddress();

        ByteAddress ba = new ByteAddress(mask, local_byte);
        ba.Afficher();

        //Create all accessible addresses
        ArrayList<InetAddress> all_addresses = ba.getAllAddresses();

        //Test one address
//        all_addresses.clear();
//        try {
//            all_addresses.add(InetAddress.getByName("10.188.88.106"));
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }

        //Prepare to send
        DatagramSocket socketClient = null;
        try 
        {
            socketClient = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        //Message
        byte[] msgSend = new byte[1];
        msgSend[0] = MSG_UDP;


        //Create a thread to listen
        DatagramSocket finalSocketClient = socketClient;
        Thread t_receive = new Thread(() -> listenUDPReceive(finalSocketClient));
        t_receive.start();

        //Send the message to everyone
        for (int i = 0; i < all_addresses.size(); i++) 
        {
            DatagramPacket dp = new DatagramPacket(msgSend, msgSend.length, all_addresses.get(i), port);
            //Send
            try {
                socketClient.send(dp);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        HashSet<String> res = new HashSet<>();
        return res;
    }

    
    //To add feature: not used yet
    /**
     * 
     * @param s_Client
     */
    public void listenUDPReceive(DatagramSocket s_Client)
    {
        while(true){
            try {
                byte[] buff = new byte[1];
                DatagramPacket dp_listener = new DatagramPacket(buff, buff.length);
                System.out.println("Ready to receive UDP...");
                s_Client.receive(dp_listener);
                System.out.println("Received response from : " + dp_listener.getAddress().toString());

            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


}

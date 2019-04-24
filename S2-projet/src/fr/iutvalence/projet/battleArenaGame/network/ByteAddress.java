package fr.iutvalence.projet.battleArenaGame.network;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

//TODO: documentation
// WORK IN PROGRESS DO NOT TOUCH

public class ByteAddress
{
	private byte[] mask;
    private byte[] min_Addr;
    private byte[] max_Addr;
    private byte[] local_Addr;

    public ByteAddress(byte[] mask_in, byte[] local_Addr){
        this.local_Addr = local_Addr.clone();
        mask = new byte[4];
        min_Addr = new byte[4];
        max_Addr = new byte[4];

        //Get the mask like 255.255.0.0
        for (int i = 0; i < mask_in.length; i++) {
            if(mask_in[i] == local_Addr[i])
                mask[i] = 0;
            else
                mask[i] = mask_in[i];
            mask[i] = (byte)~mask[i];
        }

        this.initMinMax();
    }

    public void initMinMax(){
        //Base addr given mask
        for (int i = 0; i < 4; i++) {
            min_Addr[i] = (byte)((local_Addr[i] & mask[i])) ;
        }

        //Max addr given mask
        for (int i = 0; i < 4; i++) {
            if(mask[i] == -1)
                max_Addr[i] = min_Addr[i];
            else
                max_Addr[i] = (byte)~mask[i];
        }
    }


    public void Afficher(){
        System.out.print("local_byte : ");
        for (byte b : local_Addr) {
            System.out.print(b & 0xFF);
            System.out.print(".");
        }
        System.out.println();

        System.out.print("mask : ");
        for (byte b : mask) {
            System.out.print(b & 0xFF);
            System.out.print(".");
        }
        System.out.println();

        System.out.print("min_Addr : ");
        for (byte b : min_Addr) {
            System.out.print(b & 0xFF);
            System.out.print(".");
        }
        System.out.println();

        System.out.print("max_Addr : ");
        for (byte b : max_Addr) {
            System.out.print(b & 0xFF);
            System.out.print(".");
        }
        System.out.println();
    }

    public byte[] getMask() {
        return mask;
    }

    public byte[] getMin_Addr() {
        return min_Addr;
    }

    public byte[] getMax_Addr() {
        return max_Addr;
    }

    public byte[] getLocal_Addr() {
        return local_Addr;
    }

    public ArrayList<InetAddress> getAllAddresses(){
        ArrayList<InetAddress> res = new ArrayList<InetAddress>();
        byte[] address = min_Addr;

        while(!byteArrayEqual(address,max_Addr))
        {
            if(address[3] == max_Addr[3]){
                address[3] = min_Addr[3];

                if(address[2] == max_Addr[2]){
                    address[2] = min_Addr[2];

//                    if(address[1] == max_Addr[1]+1){
//                        address[1] = min_Addr[1];
//
//                        if(address[0] == max_Addr[0]+1){
//                            address[0] = min_Addr[0];
//                        }
//                        address[0] += 1;
//                    }
//                    address[1] += 1;
                }
                address[2] += 1;
            }
            address[3] += 1;

            try {
                res.add(InetAddress.getByAddress(address));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        return res;
    }

    public static boolean byteArrayEqual(byte[] arr1, byte[] arr2){
        if(arr1.length != arr2.length)
            return false;

        for (int i = 0; i < arr1.length; i++) {
            if(arr1[i] != arr2[i])
                return false;
        }

        return true;
    }

    public static String byteToString(byte[] addr){
        String res = "";
        for (int i = 0; i < addr.length-1; i++) {
            res += (addr[i] & 0xFF)+ ".";
        }
        res += addr[addr.length-1] & 0xFF;

        return res;
    }
}

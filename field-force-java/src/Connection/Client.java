package Connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {

    private static Client instance;

    private String ip;
    private int port;
    private DataOutputStream output;
    private DataInputStream input;
    private Socket socket;

    private boolean gameIsRunning = false;

    public Client(){

    }

    public static Client getInstance(){
        if(instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public void initConnection(String ip, int port) {
        this.ip = ip;
        this.port = port;
        try {
            socket = new Socket(this.ip, this.port);
            this.output = new DataOutputStream(socket.getOutputStream());
            this.input = new DataInputStream(socket.getInputStream());

            ClientSend.sendPlayername();

            int length;
            int type;
            this.gameIsRunning = true;

            while (this.gameIsRunning) {
                Packet packet;
                length = getMessageLength();
                type = getMessageLength();
                packet = new Packet(getMessage(length-4));
                handleMessages(type, packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void handleMessages(int type, Packet packet){
        switch (type){
            case 1: //Login to server with playername and skills
                System.out.println("Type 1: Shouldn't be received. Only used to tell the server own playerinformation");
                break;
            case 2: //Server sends gamemode and id
                try {
                    ClientHandle.handleGamemode(packet);
                    System.out.println("Type 2");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 3: //Server sends playerturns and playerinformation (with ids for turnorder and skills)
                try {
                    ClientHandle.handlePlayerinformation(packet);
                    System.out.println("Type 3");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 4: //Server sends initial Gamefield
                try {
                    ClientHandle.handleInitialMap(packet);
                    System.out.println("Type 4");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 5: //Server sends a moverequest
                try {
                    ClientHandle.handleMoveRequest(packet);
                    System.out.println("Type 5");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 6: //Connection.Client sends movereply
                System.out.println("Type 6: Shouldn't be received. Only used to tell the server own move");
                break;
            case 7: //Server sends a movereply of player in turn to ALL players
                break;
            case 8: //Server sends the new gamestate after calculating a move
                break;
            case 9: //Server sends an error. Could be illegal moves or
                try {
                    ClientHandle.handleErrors(packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 10: //Server sends players that the game is over and the winners' id
                break;
            default:
                System.out.println("Error: No type detected: " + type);
                break;
        }
    }

    public void sendPacket(Packet packet){
        try{
            output.write(packet.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] getMessage(int length){
        byte[] message=new byte[length];
        try {
            this.input.read(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }

    private int getMessageLength(){
        byte[] buffer = new byte[4];
        try {
            this.input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Packet.convertToInt(buffer);
    }
}
package Connection;

import Connection.Packet;

public class ClientHandle {

    public static void handleGamemode(Packet packet) throws Exception {
        int gamemode = packet.readInt();
        int ownID = packet.readInt();
        System.out.println("CLIENTHANDLE gamemode: " + gamemode);
    }

    public static void handlePlayerinformation(Packet packet) throws Exception {
        int playerCount = packet.readInt();
        for (int i = 0; i < playerCount; i++){
            int playerNumber = packet.readInt();
            String playerName = packet.readString();
        }
        System.out.println("CLIENTHANDLE " + playerCount);
    }

    public static void handleInitialMap(Packet packet) throws Exception {
        int dimension = packet.readInt();
        char[][] map = packet.readMap(dimension);
    }

    public static void handleMoveRequest(Packet packet) throws Exception {
        int ownId = packet.readInt();
        String message = packet.readString();
        System.out.println("CLIENTHANDLE " + ownId + " " + message);
    }

    public static void handleMovedistribution(Packet packet) throws Exception {
        int gamemode = packet.readInt();
        String message = packet.readString();
        System.out.println("CLIENTHANDLE " + gamemode + " " + message);
    }

    public static void handleNewGamestate(Packet packet) throws Exception {
        int gamemode = packet.readInt();
        String message = packet.readString();
        System.out.println("CLIENTHANDLE " + gamemode + " " + message);
    }

    public static void handleErrors(Packet packet) throws Exception {
        String errorMessage = packet.readString();
        System.out.println("CLIENTHANDLE errormessage: " + errorMessage);
    }

    public static void handleGameover(Packet packet) throws Exception {
        int gamemode = packet.readInt();
        String message = packet.readString();
        System.out.println("CLIENTHANDLE " + gamemode + " " + message);
    }
}
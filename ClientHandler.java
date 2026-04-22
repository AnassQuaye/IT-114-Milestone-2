import java.io.*;
import java.net.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    
    private Player player;
    private GameRoom currentRoom;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void setGameRoom(GameRoom room) {
        this.currentRoom = room;
    }

    @Override
    public void run() {
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("SYSTEM: Enter your name to join matchmaking:");
            String name = in.readLine();
            
            this.player = new Player(name, out);
            this.player.setHandler(this); 
            
            LexiconServer.addToQueue(this.player);

            String input;
            while ((input = in.readLine()) != null) {
                if (this.currentRoom != null && this.player.isWordMaster()) {
                    currentRoom.setTargetWord(input);
                    this.player.setWordMaster(false); // Reset after they submit
                } else if (this.currentRoom != null) {
                    System.out.println(this.player.getName() + " typed: " + input);
                }
            }
        } catch (IOException e) {
            System.out.println("A player disconnected."); // FIX: Corrected typo
        }
    }
}
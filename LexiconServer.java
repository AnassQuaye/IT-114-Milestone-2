import java.io.*;
import java.net.*;
import java.util.*;

public class LexiconServer {
    private static final int PORT = 8765;

    private static List<Player> waitingQueue = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        System.out.println("Lexicon Lariat Server Started");
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void addToQueue(Player p) {
        waitingQueue.add(p);
        System.out.println(p.getName() + " joined the queue. (" + waitingQueue.size() + "/3)");

        if (waitingQueue.size() >= 3) {
            List<Player> matchPlayers = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                matchPlayers.add(waitingQueue.remove(0));
            }
            new GameRoom(matchPlayers).start();
            System.out.println("Match started! Queue cleared for next group.");
        }
    }
}
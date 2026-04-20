import java.io.*;
import java.net.*;
import java.util.Scanner;

public class LexiconClient {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 8765;

        try (Socket socket = new Socket(host, port)) {
            new Thread(() -> {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String serverMsg;
                    while ((serverMsg = in.readLine()) != null) {
                        System.out.println("\n[SERVER] " + serverMsg);
                        System.out.print("> "); 
                    }
                } catch (IOException e) {
                    System.out.println("Lost connection to server.");
                }
            }).start();

            
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);
            
            while (true) {
                if (scanner.hasNextLine()) {
                    String userInput = scanner.nextLine();
                    out.println(userInput);
                }
            }

        } catch (IOException e) {
            System.err.println("Error: Could not connect to Lexicon Server. Is it running?");
        }
    }
}
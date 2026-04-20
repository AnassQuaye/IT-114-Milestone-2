import java.io.*;
import java.net.*;
public class ClientHandler extends Thread{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }
    @Override
    public void run(){
        try{
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out.println("SYSTEM: Enter your name to join matchmaking:");
            String name = in.readLine();
            
            Player player = new Player(name, out);
            LexiconServer.addToQueue(player);

            String input;
            while((input = in.readLine())!= null){
                System.out.println(name + "typed:" + input);
            }
        } catch (IOException e) {
            System.out.println("A player disconnted.");
        }
    }
}

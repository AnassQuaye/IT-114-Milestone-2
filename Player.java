import java.io.PrintWriter;

public class Player {
    private String name;
    private int score = 0;
    private PrintWriter out;
    
    private boolean wordMaster = false;
    private ClientHandler handler;

    public Player(String name, PrintWriter out) {
        this.name = name;
        this.out = out;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public void addPoint() { this.score++; }
    public void sendMessage(String msg) { out.println(msg); }

    public boolean isWordMaster() { return wordMaster; }
    public void setWordMaster(boolean wordMaster) { this.wordMaster = wordMaster; }
    
    public ClientHandler getHandler() { return handler; }
    public void setHandler(ClientHandler handler) { this.handler = handler; }
}
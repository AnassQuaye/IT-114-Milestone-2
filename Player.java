import java.io.PrintWriter;

public class Player {
    private String name;
    private int score = 0;
    private PrintWriter out;

    public Player(String name, PrintWriter out) {
        this.name = name;
        this.out = out;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
    public void addPoint() { this.score++; }
    public void sendMessage(String msg) { out.println(msg); }
}
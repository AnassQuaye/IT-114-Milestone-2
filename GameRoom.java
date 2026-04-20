import java.util.List;

public class GameRoom extends Thread {
    private List<Player> players;
    private boolean gameRunning = true;

    public GameRoom(List<Player> players) {
        this.players = players;
    }

    @Override
    public void run() {
        broadcast("SYSTEM: Match Found! Game starting...");
        
        while (gameRunning) {
            for (int i = 15; i >= 0; i--) {
                broadcast("TIMER:" + i);
                try { Thread.sleep(1000); } catch (InterruptedException e) { break; }
            }
            
            broadcast("SYSTEM: Round Over! Calculating next Word Master...");
            
            gameRunning = false; // End for now for testing
        }
    }

    private void broadcast(String msg) {
        for (Player p : players) {
            p.sendMessage(msg);
        }
    }
}

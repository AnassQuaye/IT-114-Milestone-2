import java.util.List;

public class GameRoom extends Thread {
    private List<Player> players;
    private String targetWord = "";

    public GameRoom(List<Player> players) {
        this.players = players;
        
        for (Player p : players) {
            if (p.getHandler() != null) {
                p.getHandler().setGameRoom(this);
            }
        }
    }

    @Override
    public void run() {
        Player master = players.get(0);
        
        master.setWordMaster(true);
        
        broadcast("SYSTEM: Match Found!");
        
        master.sendMessage("STATE:YOU_ARE_MASTER|Please type the challenge word now:");
        
        for (int i = 1; i < players.size(); i++) {
            players.get(i).sendMessage("STATE:WAITING|Wait for " + master.getName() + " to pick a word...");
        }

        while (targetWord.isEmpty()) {
            try { Thread.sleep(500); } catch (InterruptedException e) {}
        }

        broadcast("SYSTEM: Word set! The word is: " + targetWord);
        for (int i = 15; i >= 0; i--) {
            broadcast("TIMER:" + i);
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
        
        broadcast("SYSTEM: Round Over!");
    }

    public synchronized void setTargetWord(String word) {
        this.targetWord = word;
    }

    private void broadcast(String msg) {
        for (Player p : players) {
            p.sendMessage(msg);
        }
    }
}
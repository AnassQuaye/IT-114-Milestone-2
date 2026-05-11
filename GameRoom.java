import java.util.List;

public class GameRoom extends Thread {
    private List<Player> players;
    private String targetWord = "";
    private volatile boolean roundWon = false;

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

        broadcast("SYSTEM: Word set! Start guessing!");
        
        for (int i = 15; i >= 0; i--) {
            if (roundWon) {
                break;
            }
            broadcast("TIMER:" + i);
            try { Thread.sleep(1000); } catch (InterruptedException e) {}
        }
        
        if (!roundWon) {
            broadcast("SYSTEM: Round Over! Time ran out. The word was: " + targetWord);
        }
        
        master.setWordMaster(false);
    }

    public synchronized void setTargetWord(String word) {
        this.targetWord = word;
    }

    public synchronized String getTargetWord() {
        return this.targetWord;
    }

    public synchronized void checkGuess(Player player, String guess) {
        if (roundWon) return;

        if (!player.isWordMaster() && !targetWord.isEmpty() && guess.equalsIgnoreCase(targetWord)) {
            roundWon = true;
            player.addPoint();
            broadcast("SYSTEM: *** " + player.getName() + " guessed correctly and won the round! ***");
            broadcast("SYSTEM: " + player.getName() + "'s Total Score: " + player.getScore());
        } else {
            String role = player.isWordMaster() ? " (Master)" : "";
            broadcast(player.getName() + role + ": " + guess);
        }
    }

    private void broadcast(String msg) {
        for (Player p : players) {
            p.sendMessage(msg);
        }
    }
}
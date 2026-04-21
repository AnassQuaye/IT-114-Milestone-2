import java.util.List;
import java.util.Scanner;

public class GameRoom extends Thread {
    private List<Player> players;
    private String targetWord = "";
    private boolean wordChosen = false;

    public GameRoom(List<Player> players) {
        this.players = players;
    }

    @Override
    public void run() {
        Player master = players.get(0);
        broadcast("SYSTEM: Match Found!");

        master.sendMessage("State: You are in-charged| Type your challenge word in:");

        for(int i = 1; i < players.size(); i++){
            players.get(i).sendMessage("State:Waiting|Please type the challenge word now:");
        }

        while(targetWord.isEmpty()){
            try {Thread.sleep(500);} catch (InterruptedException e) 
            {}
        }

        broadcast("Sytem: word set! The word is: " + targetWord);


        for (int i = 15; i >= 0; i--) {
                broadcast("TIMER:" + i);
                try { Thread.sleep(1000); } catch (InterruptedException e) { break; }
            }
            
            broadcast("SYSTEM: Round Over! Calculating next Word Master...");
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

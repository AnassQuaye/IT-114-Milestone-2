import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;

public class LexiconClientGUI {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;

    public LexiconClientGUI() {
        initializeGUI();
        connectToServer();
    }
}
private void initializeGUI(){
    frame = new JFrame("Lexicon Lariat");
    frame.setDefaultXloseOperation(JFrame.EXIT_ONXLOSE);
    frame.setSize(600, 450);

    cardLayout = new CardLayout();
    cardPanel = new JPanel(cardLayout);
    
}
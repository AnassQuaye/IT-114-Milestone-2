import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;

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

    private void initializeGUI() {
        frame = new JFrame("Lexicon Lariat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 450);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // --- MATCHMAKING / LOGIN PANEL ---
        JPanel loginPanel = new JPanel(new BorderLayout());
        loginPanel.setBackground(new Color(43, 43, 43)); // Sleek dark theme

        // 1. Decoration: Main Logo / Icon
        JLabel logoLabel = new JLabel("Welcome to Lexicon Lariat", SwingConstants.CENTER);
        logoLabel.setForeground(Color.WHITE);
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        
        try {
            // Replace "logo.png" with the path to your actual image file
            ImageIcon logoIcon = new ImageIcon("src/resources/logo.png");
            // Scale the image if necessary
            Image scaledImage = logoIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            logoLabel.setIcon(new ImageIcon(scaledImage));
            logoLabel.setHorizontalTextPosition(JLabel.CENTER);
            logoLabel.setVerticalTextPosition(JLabel.BOTTOM);
        } catch (Exception e) {
            System.out.println("Decoration image not found, using text fallback.");
        }

        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        JTextField nameField = new JTextField(15);
        
        // 2. Decoration: Button Icon
        JButton joinButton = new JButton("Join Matchmaking");
        try {
            ImageIcon searchIcon = new ImageIcon("src/resources/search_icon.png");
            joinButton.setIcon(searchIcon);
        } catch (Exception e) {}

        inputPanel.add(new JLabel("<html><font color='white'>Enter Your Name:</font></html>"));
        inputPanel.add(nameField);
        inputPanel.add(joinButton);

        loginPanel.add(logoLabel, BorderLayout.CENTER);
        loginPanel.add(inputPanel, BorderLayout.SOUTH);

        // --- GAME ROOM PANEL ---
        JPanel gamePanel = new JPanel(new BorderLayout());
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(chatArea);

        JPanel actionPanel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        
        // 3. Decoration: Send Button Icon
        sendButton = new JButton("Send");
        try {
            ImageIcon sendIcon = new ImageIcon("src/resources/send_icon.png");
            Image scaledImg = sendIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            sendButton.setIcon(sendIcon);
        } catch (Exception e) {}
        
        actionPanel.add(inputField, BorderLayout.CENTER);
        actionPanel.add(sendButton, BorderLayout.EAST);

        gamePanel.add(scrollPane, BorderLayout.CENTER);
        gamePanel.add(actionPanel, BorderLayout.SOUTH);

        // Add both screens to the CardLayout
        cardPanel.add(loginPanel, "LOGIN");
        cardPanel.add(gamePanel, "GAME");

        frame.add(cardPanel);
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);

        // --- ACTION LISTENERS ---
        joinButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty() && out != null) {
                out.println(name);
                // Switch the UI from the Login screen to the Game screen
                cardLayout.show(cardPanel, "GAME");
                inputField.requestFocus();
            }
        });

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage()); 
    }

    private void sendMessage() {
        String text = inputField.getText().trim();
        if (!text.isEmpty() && out != null) {
            out.println(text);
            inputField.setText("");
        }
    }

    private void connectToServer() {
        // Run networking on a separate thread to prevent freezing the GUI
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 8765);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String serverMsg;
                while ((serverMsg = in.readLine()) != null) {
                    // Update GUI safely from the background network thread
                    String finalMsg = serverMsg;
                    SwingUtilities.invokeLater(() -> processServerMessage(finalMsg));
                }
            } catch (IOException e) {
                SwingUtilities.invokeLater(() -> chatArea.append("\n[ERROR] Could not connect to Lexicon Server.\n"));
            }
        }).start();
    }

    private void processServerMessage(String msg) {
        // If the server confirms a match, ensure we are on the game screen
        if (msg.contains("SYSTEM: Match Found!")) {
            cardLayout.show(cardPanel, "GAME");
        }
        
        chatArea.append(msg + "\n");
        // Auto-scroll the text area to the bottom
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    public static void main(String[] args) {
        // Initialize GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(LexiconClientGUI::new);
    }
}
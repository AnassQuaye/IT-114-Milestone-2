Lexicon Lariat
Lexicon Lariat is a multiplayer word-guessing game built using Java Sockets and Swing. It features a centralized server that handles matchmaking, real-time game logic, and a 15-second countdown timer shared across all participants.

Features
Matchmaking System: Automatically groups three players together to start a match.

Role-Based Gameplay: One player is designated as the "Word Master" to choose the secret word, while others act as guessers.

Real-Time Interaction: Features a synchronized timer and live chat updates for all players.

Graphical User Interface: A clean Swing-based UI with a login screen and game lobby.

System Architecture
The project is divided into five core components:

LexiconServer: The entry point that listens on port 8765 and manages the global waiting queue.

LexiconClientGUI: The client-side application that renders the UI and handles user input.

GameRoom: A dedicated thread that manages the state of a single match, including word validation and the countdown.

ClientHandler: A per-player thread on the server that facilitates communication between the client and the game logic.

Player: A data model storing the player's name, score, and connection status.

How to Run
1. Start the Server
Compile and run LexiconServer.java. The server will begin listening for connections.

Bash
javac LexiconServer.java
java LexiconServer
2. Start the Clients
Compile and run LexiconClientGUI.java. You will need to run at least three separate client instances to trigger a match.

Bash
javac LexiconClientGUI.java
java LexiconClientGUI
Rules of the Game
Joining: Enter your name and click "Join Matchmaking" to enter the queue.

The Master: Once three players join, the first player to have joined is assigned as the Word Master.

Setting the Word: The Master types a secret word into the chat. This word becomes the target for the round.

The Guessers: The other two players must type their guesses into the chat before the 15-second timer expires.

Winning: If a guesser identifies the word correctly, they earn a point and the round ends immediately.

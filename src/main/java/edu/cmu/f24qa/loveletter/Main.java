package edu.cmu.f24qa.loveletter;

/**
 * Entry point for the Love Letter game application. This class initializes
 * the game and starts the gameplay sequence.
 */
public class Main {

    /**
     * The main method to launch the Love Letter game. It sets up players and starts the game.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        final Game g = new Game();
        g.setPlayers();
        g.gameLoop();
    }
}

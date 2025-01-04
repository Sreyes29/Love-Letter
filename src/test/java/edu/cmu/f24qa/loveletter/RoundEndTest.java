package edu.cmu.f24qa.loveletter;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;
import java.io.PrintStream;

class GameRoundEndTest {

    private Game mockGame;
    private Deck mockDeck;
    private PlayerList mockPlayerList;
    private Field cardsField;
    private PrintStream mockPrintStream;
    private Scanner mockScanner;

    @BeforeEach
    void setUp() throws Exception {
        // Mock dependencies
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
    }

    /**
     * Tests that the round ends when the deck is empty.
     */
    @Test
    void testRoundEndsWhenDeckIsEmpty() throws Exception {
        for (int numOfPlayers = 2; numOfPlayers <= 4; numOfPlayers++) {
            setupGame(numOfPlayers);

            simulateGameUntilLastCard();

            assertEquals(1, getDeckCardCount(), "The deck should start with 1 card remaining.");
            assertFalse(this.mockGame.checkRoundEnd(), "The round should not end with 1 card left in the deck.");

            // Final turn to exhaust the deck
            Player currentPlayer = this.mockPlayerList.getCurrentPlayer();
            boolean turnCompleted = this.mockGame.playTurn(currentPlayer);

            // Assertions for the final turn
            assertTrue(turnCompleted, "The player's turn should complete successfully.");
            assertEquals(0, getDeckCardCount(), "The deck should be empty after the player's turn.");
            assertTrue(this.mockGame.checkRoundEnd(), "The round should end immediately when the deck is empty.");
        }
    }

    /**
     * Sets up the game with the specified number of players.
     */
    private void setupGame(int numOfPlayers) throws Exception {
        // Initialize the deck and reflectively access the cards field
        this.mockDeck = new Deck();
        this.cardsField = this.mockDeck.getClass().getDeclaredField("cards");
        this.cardsField.setAccessible(true);

        // Create a player list with the specified number of players
        this.mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
        for (int i = 0; i < numOfPlayers; i++) {
            this.mockPlayerList.addPlayer("Player " + i);
        }

        // Create a spied Game instance with mocked dependencies
        this.mockGame = Mockito.spy(new Game(this.mockPlayerList, this.mockDeck, mockScanner, mockPrintStream));

        // Mock the getCard method to always return the first card
        Mockito.doAnswer(invocation -> {
            Player player = invocation.getArgument(0);
            return player.removeHand(0);
        }).when(this.mockGame).getCard(Mockito.any(Player.class));

        // Mock playCard to do nothing
        Mockito.doNothing().when(this.mockGame).playCard(Mockito.any(Card.class), Mockito.any(Player.class));

        // Initialize the game round
        this.mockGame.initializeRound();
    }

    /**
     * Simulates the game until only one card remains in the deck.
     */
    private void simulateGameUntilLastCard() throws Exception {
        while (getDeckCardCount() > 1) {
            Player currentPlayer = this.mockPlayerList.getCurrentPlayer();
            boolean turnCompleted = this.mockGame.playTurn(currentPlayer);
            assertTrue(turnCompleted, "The player's turn should complete successfully.");
        }
    }

    /**
     * Retrieves the current number of cards in the deck.
     */
    @SuppressWarnings("unchecked")
    private int getDeckCardCount() throws Exception {
        return ((List<Card>) this.cardsField.get(this.mockDeck)).size();
    }
}
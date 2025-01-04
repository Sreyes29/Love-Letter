package edu.cmu.f24qa.loveletter;

import static org.mockito.ArgumentMatchers.any;

import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RoundEndWhenOnePlayerTest {
    
    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    public Deck mockDeck;

    public RoundEndWhenOnePlayerTest() {
        this.mockDeck = Mockito.spy(new Deck());
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
        this.mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
    }

    @BeforeEach
    public void setup() {
        this.mockDeck = Mockito.spy(new Deck());
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
        this.mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
        this.mockPlayerList.addPlayer("Alice");
        this.mockPlayerList.addPlayer("Bob");
        this.mockPlayerList.addPlayer("Charlie");
    }

    /**
     * Test that the round ends when one player is left
     */
    @Test
    @SuppressWarnings("allcheckers")
    public void testRoundEndsWhenOnePlayerLeft() {
        // Create Mocks
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        // Bob and Charlie are eliminated, hence only Alice has a card
        mockPlayerList.getPlayer("Alice").addCardToHand(Card.GUARD);

        // Define Mock Behavior
        Mockito.doNothing().when(mockGame).turnLogic(any(), any());
        Mockito.doNothing().when(mockPlayerList).reset();
        Mockito.doNothing().when(mockPlayerList).dealCards(mockDeck);

        // Act
        mockGame.playRound();

        // Verify
        Assertions.assertEquals(true, mockGame.checkRoundEnd());
        Mockito.verify(mockPrintStream).println("Alice has won this round!");
    }
}

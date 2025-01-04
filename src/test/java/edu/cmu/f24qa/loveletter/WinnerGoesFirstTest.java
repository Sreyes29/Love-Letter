package edu.cmu.f24qa.loveletter;

import static org.mockito.ArgumentMatchers.any;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class WinnerGoesFirstTest {

    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    public Deck mockDeck;

    public WinnerGoesFirstTest() {
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

    /***
     * Test that the winner of the previous round goes first in the next round.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testWinnerGoesFirst() {
        // Create Mocks
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        Player winner = mockPlayerList.getPlayer("Bob");
        Player end = new Player("Charlie");

        // Define Mock Behavior
        Mockito.when(mockGame.checkRoundEnd()).thenReturn(true, false, true);
        Mockito.when(mockPlayerList.getRoundWinners()).thenReturn(Arrays.asList(winner), Arrays.asList(end));
        Mockito.doNothing().when(mockGame).turnLogic(any(), any());

        // Act
        mockGame.playRound();
        mockGame.playRound();

        // Verify
        Mockito.verify(mockPrintStream, Mockito.times(1)).println("Bob has won this round!");
        Mockito.verify(mockPrintStream, Mockito.times(1)).println("\nBob's turn:");
    }

}

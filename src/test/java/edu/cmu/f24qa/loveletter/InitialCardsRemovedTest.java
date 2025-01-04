package edu.cmu.f24qa.loveletter;

import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class InitialCardsRemovedTest {

    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    public Deck mockDeck;

    public InitialCardsRemovedTest() {
        this.mockDeck = Mockito.spy(new Deck());
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
        this.mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
        this.mockPlayerList.addPlayer("Alice");
        this.mockPlayerList.addPlayer("Bob");
    }

    @BeforeEach
    public void setup() {
        this.mockDeck = Mockito.spy(new Deck());
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
        this.mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
        this.mockPlayerList.addPlayer("Alice");
        this.mockPlayerList.addPlayer("Bob");
    }
    
    /**
     * Tests that one card is removed and unrevealed for a 2 player game.
     */
    @Test
    public void testInitialCardsRemoved2Players() {
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));

        Mockito.doNothing().when(mockPlayerList).dealCards(mockDeck);

        mockGame.initializeRound();

        Mockito.verify(mockDeck, Mockito.times(4)).draw();
        Mockito.verify(mockPrintStream, Mockito.times(3)).println(Mockito.anyString());
    }

    /**
     * Tests that one card is removed and three additional cards are removed and revealed for a 3 player game.
     */
    @Test
    public void testInitialCardsRemoved3Players() {
        this.mockPlayerList.addPlayer("Charlie");
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));

        Mockito.doNothing().when(mockPlayerList).dealCards(mockDeck);

        mockGame.initializeRound();

        Mockito.verify(mockDeck, Mockito.times(1)).draw();
        Mockito.verify(mockPrintStream, Mockito.times(2)).println(Mockito.anyString());
    }

    /**
     * Tests that one card is removed and three additional cards are removed and revealed for a 4 player game.
     */
    @Test
    public void testInitialCardsRemoved4Players() {
        this.mockPlayerList.addPlayer("Charlie");
        this.mockPlayerList.addPlayer("David");
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));

        Mockito.doNothing().when(mockPlayerList).dealCards(mockDeck);

        mockGame.initializeRound();

        Mockito.verify(mockDeck, Mockito.times(1)).draw();
        Mockito.verify(mockPrintStream, Mockito.times(2)).println(Mockito.anyString());
    }
}

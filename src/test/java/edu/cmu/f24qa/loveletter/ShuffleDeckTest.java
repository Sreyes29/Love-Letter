package edu.cmu.f24qa.loveletter;

import java.io.PrintStream;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ShuffleDeckTest {

    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    public Deck mockDeck;

    @BeforeEach
    public void setup() {
        this.mockDeck = Mockito.spy(new Deck());
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
        this.mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
    }

    /**
     * Tests that the deck is shuffled correctly by ensuring the order of cards changes
     * after each round, and for 2, 3, and 4 player games.
     */
    @Test
    public void testShuffleDeck() {
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        List<Card> previousOrder = null;

        for (int numOfPlayers = 2; numOfPlayers <= 4; numOfPlayers++) {
            this.mockPlayerList.addPlayer("Player" + numOfPlayers);
            for (int i = 0; i < 10; i++) {
                mockGame.initializeRound();

                List<Card> currentOrder = new ArrayList<>();
                while (mockDeck.hasMoreCards()) {
                    currentOrder.add(mockDeck.draw());
                }
                
                assertNotEquals(previousOrder, currentOrder, "Deck order did not change after shuffle iteration " + i);
                previousOrder = currentOrder;
            }
        }
        
        Mockito.verify(mockDeck, Mockito.times(30)).shuffle();
    }
}
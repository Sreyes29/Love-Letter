package edu.cmu.f24qa.loveletter;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RoundWinnerTest {
    
    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    public Deck mockDeck;

    public RoundWinnerTest() {
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
    }

    /**
     * Tests that the player with the largest hand wins when there is no tie.
     */
    @Test
    public void testRoundWinnerNoTie() {
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        Player alice = mockPlayerList.getPlayer("Alice");
        Player bob = mockPlayerList.getPlayer("Bob");
        alice.addCardToHand(Card.PRINCE);
        bob.addCardToHand(Card.GUARD);

        List<Player> winners = mockGame.endRound();

        Mockito.verify(mockPrintStream).println(alice.getName() + " has won this round!");
        Assertions.assertEquals(1, winners.size());
        Assertions.assertEquals(Arrays.asList(alice), winners);
    }

    /**
     * Tests that the player with the higher discard pile sum wins when there is a tie.
     */
    @Test
    public void testRoundWinnerWithTie() {
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        Player alice = mockPlayerList.getPlayer("Alice");
        Player bob = mockPlayerList.getPlayer("Bob");
        alice.addCardToHand(Card.GUARD);
        bob.addCardToHand(Card.GUARD);
        alice.addDiscarded(Card.PRIEST);
        bob.addDiscarded(Card.HANDMAIDEN);

        List<Player> winners = mockGame.endRound();

        Mockito.verify(mockPrintStream).println(bob.getName() + " has won this round!");
        Assertions.assertEquals(1, winners.size());
        Assertions.assertEquals(Arrays.asList(bob), winners);
    }

    /**
     * Tests that multiple players win when there is a tie for the largest sum of discard piles.
     */
    @Test
    public void testMultipleRoundWinners() {
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        Player alice = mockPlayerList.getPlayer("Alice");
        Player bob = mockPlayerList.getPlayer("Bob");
        alice.addCardToHand(Card.GUARD);
        bob.addCardToHand(Card.GUARD);
        alice.addDiscarded(Card.BARON);
        bob.addDiscarded(Card.BARON);

        List<Player> winners = mockGame.endRound();

        Mockito.verify(mockPrintStream).println(alice.getName() + " has won this round!");
        Mockito.verify(mockPrintStream).println(bob.getName() + " has won this round!");
        Assertions.assertEquals(2, winners.size());
        Assertions.assertTrue(winners.containsAll(Arrays.asList(alice, bob)));
    }
}

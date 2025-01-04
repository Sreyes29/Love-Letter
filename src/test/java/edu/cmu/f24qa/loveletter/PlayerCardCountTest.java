package edu.cmu.f24qa.loveletter;

import static org.mockito.ArgumentMatchers.any;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PlayerCardCountTest {
    
    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    public Deck mockDeck;

    public PlayerCardCountTest() {
        this.mockDeck = Mockito.spy(new Deck());
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
        this.mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
        this.mockPlayerList.addPlayer("Alice");
        this.mockPlayerList.addPlayer("Bob");
        this.mockPlayerList.addPlayer("Jack");
        this.mockPlayerList.addPlayer("Ben");
    }

    @BeforeEach
    public void setup() {
        this.mockDeck = Mockito.spy(new Deck());
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
        this.mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
        this.mockPlayerList.addPlayer("Alice");
        this.mockPlayerList.addPlayer("Bob");
        this.mockPlayerList.addPlayer("Jack");
        this.mockPlayerList.addPlayer("Ben");
    }

    /**
     * Tests that each player has one card in their hand when the round starts.
     */
    @Test
    public void testPlayersHaveOneCardWhenRoundStarts() {
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));

        mockGame.initializeRound();
        
        // Checking state when round starts
        PlayerList players = mockGame.getPlayers();
        for (Player player : players.getPlayers()) {
            Hand hand = player.getHand();
            // Getting the cards in each player's hand
            List<Card> cards = hand.getCards();
            Assertions.assertEquals(1, cards.size());
        }
    }

    /**
     * Tests that each player has one card in their hand when the round ends.
     */
    @Test
    public void testPlayersHaveOneCardWhenRoundEnds() {
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        PlayerList players = mockGame.getPlayers();
        
        Mockito.when(mockScanner.nextLine()).thenReturn("0");
        Mockito.doNothing().when(mockGame).playCard(any(), any());
        Mockito.when(mockGame.checkRoundEnd()).thenReturn(false, true);

        mockGame.playRound();

        // Checking state after round ends
        for (Player player : players.getPlayers()) {
            Hand hand = player.getHand();
            // Getting the cards in each player's hand
            List<Card> cards = hand.getCards();
            System.out.println(cards.size());
            // If the player is not eliminated, they should have 1 card in hand
            if (player.hasCardsInHand()) {
                Assertions.assertEquals(1, cards.size());
            }
        }
    }
}

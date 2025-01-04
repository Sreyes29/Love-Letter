package edu.cmu.f24qa.loveletter;

import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import java.util.LinkedList;
import java.util.List;

public class DiscardedCardsDisplayedTest {
    
    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    public Deck mockDeck;

    public DiscardedCardsDisplayedTest() {
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
    }

    /**
     * Tests that the print method in DiscardPile prints the cards in the discard pile
     * in order and numbered to make the order clear.
     */
    @Test
    public void testDiscardedCardsDisplayed() {
        DiscardPile mockDiscardPile = Mockito.spy(new DiscardPile());
        mockDiscardPile.add(Card.BARON);
        mockDiscardPile.add(Card.COUNTESS);
        mockDiscardPile.add(Card.PRINCE);

        mockDiscardPile.print(mockPrintStream);

        InOrder inOrder = Mockito.inOrder(mockPrintStream);
        inOrder.verify(mockPrintStream).print("1. ");
        inOrder.verify(mockPrintStream).println(Card.BARON);
        inOrder.verify(mockPrintStream).print("2. ");
        inOrder.verify(mockPrintStream).println(Card.COUNTESS);
        inOrder.verify(mockPrintStream).print("3. ");
        inOrder.verify(mockPrintStream).println(Card.PRINCE);
    }

    /**
     * Tests that the printDiscarded method in Player calls the print method in DiscardPile.
     */
    @Test
    public void testPlayerPrintDiscardedCallsDiscardPrint() {
        Hand mockHand = Mockito.mock(Hand.class);
        DiscardPile mockDiscardPile = Mockito.mock(DiscardPile.class);
        Player player = new Player("Alice", mockHand, mockDiscardPile);

        player.printDiscarded(mockPrintStream);

        Mockito.doNothing().when(mockDiscardPile).print(mockPrintStream);

        Mockito.verify(mockDiscardPile).print(mockPrintStream);
    }

    /**
     * Tests that the printUsedPiles method in PlayerList calls the printDiscarded method
     * for every Player in PlayerList.
     */
    @Test
    public void testPrintUsedPilesCallsPrintDiscarded() {
        List<Player> listOfPlayers = new LinkedList<>();
        Player mockPlayer1 = Mockito.mock(Player.class);
        Player mockPlayer2 = Mockito.mock(Player.class);
        listOfPlayers.add(mockPlayer1);
        listOfPlayers.add(mockPlayer2);

        Mockito.doNothing().when(mockPrintStream).println(Mockito.anyString());
        Mockito.when(mockPlayer1.getName()).thenReturn("Alice");
        Mockito.when(mockPlayer2.getName()).thenReturn("Bob");
        for (Player p : listOfPlayers) {
            Mockito.doNothing().when(p).printDiscarded(mockPrintStream);
        }

        PlayerList playerList = new PlayerList(listOfPlayers, mockPrintStream);

        playerList.printUsedPiles();

        for (Player p : listOfPlayers) {
            Mockito.verify(p).printDiscarded(mockPrintStream);
        }
    }

    /**
     * Tests that the playTurn method in Game calls the printUsedPiles method in PlayerList,
     * verifying that the discard piles are printed every turn.
     */
    @Test
    public void testPrintUsedPilesEveryTurn() {
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        Player mockPlayer = Mockito.mock(Player.class);
        Hand mockHand = Mockito.mock(Hand.class);

        Mockito.when(mockPlayer.getHand()).thenReturn(mockHand);
        Mockito.when(mockHand.hasCards()).thenReturn(true);
        Mockito.when(mockPlayer.getName()).thenReturn("Alice");
        Mockito.doNothing().when(mockPrintStream).println(Mockito.anyString());
        Mockito.doNothing().when(mockGame).turnLogic(mockPlayer, mockHand);
        Mockito.doNothing().when(mockPlayerList).printUsedPiles();

        mockGame.playTurn(mockPlayer);

        Mockito.verify(mockPlayerList).printUsedPiles();
    }

}

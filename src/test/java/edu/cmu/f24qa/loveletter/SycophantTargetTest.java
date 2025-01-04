package edu.cmu.f24qa.loveletter;

import static org.mockito.ArgumentMatchers.any;

import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SycophantTargetTest {
    
    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    public Deck mockDeck;

    public SycophantTargetTest() {
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
     * Tests that the Sycophant target is set to null when the Sycophant card is not played.
     */
    @Test
    void testSycophantTargetNullWhenNotPlayed() {
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream, new Player("Alice")));
        Player mockPlayer = Mockito.mock(Player.class);
        Card mockCard = Mockito.spy(Card.GUARD);

        Mockito.doReturn(false).when(mockGame).checkRoyalCountess(any(), any());
        Mockito.when(mockPlayerList.checkAvailablePlayers(mockPlayer)).thenReturn(true);
        Mockito.doNothing().when(mockCard).executeAction(mockGame, mockPlayer);
        Mockito.doNothing().when(mockPlayer).addDiscarded(mockCard);

        mockGame.playCard(mockCard, mockPlayer);

        Assertions.assertNull(mockGame.getSycophantTarget());
    }
}


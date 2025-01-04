package edu.cmu.f24qa.loveletter;

import static org.mockito.ArgumentMatchers.any;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TokensToWinTest {
    
    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    public Deck mockDeck;

    public TokensToWinTest() {
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

    /***
     * Test the total tokens to win for 2 players, which is 7.
     */
    @Test
    @SuppressWarnings("allcheckers")
    public void testTotalTokensToWin2Players() {
        // Create Mocks
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        Player winner = mockPlayerList.getPlayer("Alice");

        // Define Mock Behavior
        Mockito.when(mockGame.checkRoundEnd()).thenReturn(true);
        Mockito.when(mockPlayerList.getRoundWinners()).thenReturn(Arrays.asList(winner));
        Mockito.doNothing().when(mockGame).turnLogic(any(), any());

        // Act
        mockGame.gameLoop();

        // Verify
        Assertions.assertTrue(winner.getTokens() == 7);
    }

    /***
     * Test the total tokens to win for 3 players, which is 5.
     */
    @Test
    @SuppressWarnings("allcheckers")
    public void testTotalTokensToWin3Players() {
        // Create Mocks
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        Player winner = mockPlayerList.getPlayer("Alice");

        // Define Mock Behavior
        Mockito.when(mockGame.checkRoundEnd()).thenReturn(true);
        Mockito.when(mockPlayerList.getRoundWinners()).thenReturn(Arrays.asList(winner));
        Mockito.doNothing().when(mockGame).turnLogic(any(), any());

        // Act
        mockPlayerList.addPlayer("Charlie");
        mockGame.gameLoop();

        // Verify
        Assertions.assertTrue(winner.getTokens() == 5);
    }

    /***
     * Test the total tokens to win for 4 players, which is 4.
     */
    @Test
    @SuppressWarnings("allcheckers")
    public void testTotalTokensToWin4Players() {
        // Create Mocks
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        Player winner = mockPlayerList.getPlayer("Alice");

        // Define Mock Behavior
        Mockito.when(mockGame.checkRoundEnd()).thenReturn(true);
        Mockito.when(mockPlayerList.getRoundWinners()).thenReturn(Arrays.asList(winner));
        Mockito.doNothing().when(mockGame).turnLogic(any(), any());

        // Act
        mockPlayerList.addPlayer("Charlie");
        mockPlayerList.addPlayer("David");
        mockGame.gameLoop();

        // Verify
        Assertions.assertTrue(winner.getTokens() == 4);
    }


    /***
     * Test getting enough tokens to win in the middle of a round.
     */
    @Test
    public void testGetTokensToWinInRound() {
        final int threshold = 7;

        Player player1 = mockPlayerList.getPlayer("Alice");
        Player player2 = mockPlayerList.getPlayer("Bob");

        for (int i = 0; i < threshold; i++) {
            player1.addToken();
        }
        System.out.println(mockPlayerList.getPlayers());

        Mockito.when(mockPlayerList.getCurrentPlayer())
            .thenReturn(player1)
            .thenReturn(player2);

        Game spyGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
        
        Mockito.doReturn(true).when(spyGame).initializeRound();
        // Avoid Infinite Loop
        Mockito.doReturn(false, false, true).when(spyGame).checkRoundEnd();
        Mockito.doReturn(true).when(spyGame).playTurn(any(Player.class));
        Mockito.doReturn(null).when(spyGame).endRound();
        
        spyGame.playRound();

        Mockito.verify(spyGame, Mockito.times(1)).playTurn(player1);
        Mockito.verify(spyGame, Mockito.never()).playTurn(player2);
    }
}

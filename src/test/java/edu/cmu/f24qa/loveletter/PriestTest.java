package edu.cmu.f24qa.loveletter;

import edu.cmu.f24qa.loveletter.cardactions.PriestAction;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

/*
 * PriestTest tests the Priest card action in the Love Letter game.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PriestTest {
    /* 
     * Playing a Priest card should allow an opponent to be selected
     */
    @Test
    public void testPriestChoosingOpponent() {
        Scanner mockScanner = Mockito.mock(Scanner.class);
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
        PlayerList mockPlayerList = Mockito.mock(PlayerList.class);
        Player mockUser = Mockito.mock(Player.class);
        Game mockGame = Mockito.mock(Game.class);
        PriestAction spyPriestAction = Mockito.spy(new PriestAction());
        Player spyOpponent = Mockito.spy(new Player("Opponent"));

        Mockito.when(mockGame.getScanner()).thenReturn(mockScanner);
        Mockito.when(mockGame.getPlayers()).thenReturn(mockPlayerList);
        Mockito.when(mockGame.getPrinter()).thenReturn(mockPrintStream);

        Mockito.doReturn(spyOpponent).when(spyPriestAction).getOpponent(mockGame, mockUser, 0);
                                                        
        Card opponentCard = Card.GUARD;
        Mockito.doReturn(opponentCard).when(spyOpponent).checkTopCard(0);
        
        // Behavior of mocks
        spyPriestAction.execute(mockGame, mockUser);

        // Verify the interaction with the opponent's hand
        verify(spyOpponent, times(1)).checkTopCard(0);

        // Assert that a card was indeed revealed
        assertNotNull(opponentCard, "A card should have been revealed from the opponent's hand.");
        
        // Verify that the correct message was printed
        verify(mockPrintStream).println(spyOpponent.getName() + " shows you a " + opponentCard);
    }
}
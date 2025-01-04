package edu.cmu.f24qa.loveletter;

import edu.cmu.f24qa.loveletter.cardactions.CardAction;
import edu.cmu.f24qa.loveletter.cardactions.GuardAction;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests for Opponents Logic
 */
public class OpponentTest {

    /**
     * Test the getOpponent method
     */
    @Test
    void getOpponentTest() {
        // Mock the Scanner and PlayerList
        Scanner mockScanner = Mockito.mock(Scanner.class);
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
        PlayerList mockPlayerList = Mockito.mock(PlayerList.class);
        Player mockUser = Mockito.mock(Player.class);
        Player mockOpponent = Mockito.mock(Player.class);
        Game mockGame = Mockito.mock(Game.class);

        // Define the behavior of the mocks
        Mockito.when(mockGame.getScanner()).thenReturn(mockScanner);
        Mockito.when(mockGame.getPlayers()).thenReturn(mockPlayerList);
        Mockito.when(mockGame.getPrinter()).thenReturn(mockPrintStream);
        
        Mockito.when(mockScanner.nextLine()).thenReturn(
            "NonExistentPlayer", "NonExistentPlayer2", "ValidPlayer"
        );
        Mockito.when(mockPlayerList.getPlayer("NonExistentPlayer")).thenReturn(null);
        Mockito.when(mockPlayerList.getPlayer("NonExistentPlayer2")).thenReturn(null);
        Mockito.when(mockPlayerList.getPlayer("ValidPlayer")).thenReturn(mockOpponent);
        Mockito.when(mockOpponent.hasCardsInHand()).thenReturn(true);

        // Call the method under test
        CardAction cardAction = new GuardAction();
        Player result = cardAction.getOpponent(mockGame, mockUser, 0);

        // Verify the interactions and the result
        // The method should ask for the target 3 times
        Mockito.verify(mockScanner, Mockito.times(3)).nextLine();
        // The method should try to get the player 3 times
        Mockito.verify(mockPlayerList, Mockito.times(3)).getPlayer(Mockito.anyString());
        // The method should print the error message 2 times
        Mockito.verify(mockPrintStream, Mockito.times(2)).println("Invalid target");
        // The method should return the valid player
        Assertions.assertEquals(mockOpponent, result);
    }

}

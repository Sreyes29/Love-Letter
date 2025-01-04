package edu.cmu.f24qa.loveletter;

import edu.cmu.f24qa.loveletter.cardactions.GuardAction;
import java.io.PrintStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

/*
 * GuardTest tests the Guard card action in the Love Letter game.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GuardTest {

    /* 
     * Playing a Guard card should allow an opponent to be selected
     */
    @Test
    public void testGuardChoosingOpponent() {
        // create mocks
        PlayerList mockPlayerList = Mockito.mock(PlayerList.class);
        Player mockUser = Mockito.mock(Player.class);
        Player mockOpponent = Mockito.mock(Player.class);
        Game mockGame = Mockito.mock(Game.class);
        GuardAction mockGuardAction = Mockito.spy(new GuardAction());
        Scanner mockScanner = Mockito.mock(Scanner.class);  
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class); 

        // define behavior of mocks
        Mockito.when(mockGame.getScanner()).thenReturn(mockScanner);
        Mockito.when(mockGame.getPlayers()).thenReturn(mockPlayerList);
        Mockito.when(mockGame.getPrinter()).thenReturn(mockPrintStream);

        Mockito.doReturn(mockOpponent).when(mockGuardAction).getOpponent(mockGame, mockUser, 0);

        Mockito.doNothing().when(mockGuardAction).guardLogic(mockGame, mockOpponent, mockUser);

        // act
        mockGuardAction.execute(mockGame, mockUser);

        // verify
        Mockito.verify(mockGuardAction, Mockito.times(1)).getOpponent(mockGame, mockUser, 0);
        
    }
}

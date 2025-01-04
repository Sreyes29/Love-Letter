package edu.cmu.f24qa.loveletter;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import edu.cmu.f24qa.loveletter.cardactions.CardAction;
import edu.cmu.f24qa.loveletter.cardactions.PrincessAction;

public class PrincessTest {
    
    /**
     * GuardTest tests the Princess card action in the Love Letter game.
     */
    @Test
    void testPrincessCard() {
        // Mocking required classess
        Player mockPlayer = mock(Player.class);
        Game mockGame = mock(Game.class);
        CardAction princessCardAction = mock(PrincessAction.class);

        // Calling method under test
        PrincessAction princessAction = new PrincessAction();
        princessAction.execute(mockGame, mockPlayer);

        // Verify that when princess card is played no opponent is selected 
        verify(princessCardAction, times(0))
            .getOpponent(mockGame, mockPlayer, 0);
        // Verify that when princess card is played the player is eliminated
        verify(mockPlayer, times(1)).eliminate();
    }
}

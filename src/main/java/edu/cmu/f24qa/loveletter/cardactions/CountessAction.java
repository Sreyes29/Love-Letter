package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;

/**
 * Represents the action associated with the Countess card in the Love Letter game.
 * The player must discard the Countess card if they have the King or Prince in their hand.
 */
public class CountessAction extends CardAction {

    /**
     * Executes the action associated with Countess Card.
     *
     * @param game
     *          the game in which the action is executed
     *
     * @param user
     *          the player executing the action
     */
    @Override
    public void execute(Game game, final Player user) {
        // Do nothing
    }
    
}

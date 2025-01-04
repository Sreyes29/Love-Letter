package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;

/**
 * Represents the action associated with the Count card in the Love Letter game.
 * The Count card only comes into play at the end of a round.
 */
public class CountAction extends CardAction {

    /**
     * Executes the action associated with Count Card.
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

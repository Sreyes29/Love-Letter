package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;

/**
 * Represents the action associated with the Princess card in the Love Letter game.
 * The player is eliminated if they discard the Princess card.
 */
public class PrincessAction extends CardAction {

    /**
     * Executes the action associated with Princess Card.
     *
     * @param game
     *          the game in which the action is executed
     *
     * @param user
     *          the player executing the action
     */
    @Override
    public void execute(Game game, final Player user) {
        user.eliminate();
    }
}

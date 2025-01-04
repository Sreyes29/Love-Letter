package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;

/**
 * Represents the action associated with the Assassin card in the Love Letter game.
 * The player must discard their Assassin if they are targeted with the Guard card.
 */
public class AssassinAction extends CardAction {

    /**
     * Executes the action associated with Assassin Card.
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

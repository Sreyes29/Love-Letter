package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;

/**
 * Represents the action associated with the Sycophant card in the Love Letter game.
 * The target of the Sycophant card will be targeted next turn.
 */
public class SycophantAction extends CardAction {

    /** 
     * Sycophant does allow the player to target themselves.
     */
    private final static int VERSION = 1;

    /**
     * Executes the action associated with Sycophant Card.
     *
     * @param game
     *          the game in which the action is executed
     *
     * @param user
     *          the player executing the action
     */
    @Override
    public void execute(final Game game, final Player user) {
        final Player opponent = getOpponent(game, user, VERSION);

        game.setSycophantTarget(opponent);
    }
}
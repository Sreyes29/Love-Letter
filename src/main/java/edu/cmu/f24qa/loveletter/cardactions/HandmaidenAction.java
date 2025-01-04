package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;
import java.io.PrintStream;

/**
 * Represents an action associated with the Handmaiden card in the Love Letter game.
 * The action is to protect the player until their next turn.
 * The player cannot be targeted by other players until their next turn.
 */
public class HandmaidenAction extends CardAction {

    /**
     * Executes the action associated with Handmaiden Card.
     *
     * @param game
     *          the game in which the action is executed
     *
     * @param user
     *          the player executing the action
     */
    @Override
    public void execute(final Game game, final Player user) {
        final PrintStream out = game.getPrinter();
        if (!user.isProtected()) {
            user.switchProtection();
        }
        out.println("You are now protected until your next turn");
    }
}

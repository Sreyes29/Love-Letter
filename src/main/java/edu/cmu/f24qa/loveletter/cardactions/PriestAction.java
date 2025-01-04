package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Card;
import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;
import java.io.PrintStream;

/**
 * Represents the action associated with the Priest card in the Love Letter game.
 * The player gets to see the card of another player.
 */
public class PriestAction extends CardAction {

    /** 
     * Priest doesn't allow the player to target themselves.
     */
    private final static int VERSION = 0;

    /**
     * Executes the action associated with Priest Card.
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
        final Player opponent = getOpponent(game, user, VERSION);

        final Card opponentCard = opponent.checkTopCard(0);
        out.println(opponent.getName() + " shows you a " + opponentCard);
    }
}

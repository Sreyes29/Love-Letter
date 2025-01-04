package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Card;
import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;

/**
 * Represents the action associated with the King card in the Love Letter game.
 * The player trades hands with another player.
 */
public class KingAction extends CardAction {

    /** 
     * King doesn't allow the player to target themselves.
     */
    private final static int VERSION = 0;

    /**
     * Executes the action associated with King Card.
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

        kingLogic(user, opponent);
    }

    /**
     * Trades hands with another player.
     *
     * @param user
     *          the player executing the action
     *
     * @param opponent
     *          the opponent player
     */
    public void kingLogic(final Player user, final Player opponent) {
        final Card userCard = user.removeHand(0);
        final Card opponentCard = opponent.removeHand(0);

        user.addCardToHand(opponentCard);
        opponent.addCardToHand(userCard);
    }
}

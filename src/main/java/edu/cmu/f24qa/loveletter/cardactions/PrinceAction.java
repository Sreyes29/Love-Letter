package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Card;
import edu.cmu.f24qa.loveletter.Deck;
import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;

/**
 * Represents the action associated with the Prince card in the Love Letter game.
 * The player chooses another player to discard their hand and draw a new card.
 * If the discarded card is the Princess, the player is eliminated.
 */
public class PrinceAction extends CardAction {

    /** 
     * Prince does allow the player to target themselves.
     */
    private final static int VERSION = 1;

    /**
     * Executes the action associated with Prince Card.
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
        final Deck deck = game.getDeck();
        // Opponent loses if they have a Princess
        if (opponent.checkTopCard(0) == Card.PRINCESS) {
            opponent.eliminate();
        }
        else {
            opponent.eliminate();
            final Card drawnCard = deck.draw();
            opponent.addCardToHand(drawnCard);
        }
    }

}

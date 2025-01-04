package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Card;
import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;
import java.io.PrintStream;

/**
 * Represents the action associated with the Baron card in the Love Letter game.
 * The Baron action allows the player to compare their card with an opponent's card.
 * The player with the higher card value wins the comparison, and the player with the
 * lower card value is eliminated. If the card values are the same, the player with the
 * higher value in their used pile wins the comparison.
 */
public class BaronAction extends CardAction {

    /**
     * Baron doesn't allow the player to target themselves.
     */
    private static final int VERSION = 0;

    /**
     * Executes the action associated with Baron Card.
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
        
        baronLogic(user, opponent, out);
    }
    
    /**
     * Compares the card of the user with the card of the opponent.
     * The player with the higher card value wins the comparison, and the player with the
     * lower card value is eliminated. If the card values are the same, the player with the
     * higher value in their used pile wins the comparison.
     *
     * @param user
     *          the player executing the action
     *
     * @param opponent
     *          the opponent player
     */
    public void baronLogic(final Player user, final Player opponent, final PrintStream out) {
        final Card userCard = user.checkTopCard(0);
        final Card opponentCard = opponent.checkTopCard(0);

        final int cardComparison = Integer.compare(userCard.getValue(), opponentCard.getValue());
        if (cardComparison > 0) {
            out.println("You have won the comparison!");
            opponent.eliminate();
        } else if (cardComparison < 0) {
            out.println("You have lost the comparison");
            user.eliminate();
        } else {
            if (userCard.getName().equals(Card.BARON.getName())
                || userCard.getName().equals(Card.KING.getName())
                || userCard.getName().equals(Card.COUNTESS.getName())
                || userCard.getName().equals(Card.PRINCESS.getName())) {
                out.println("Game State is Corrupted. Please restart the game.");
                return;
            }
            out.println("You have the same card! It's a tie!");
        }
    }
}

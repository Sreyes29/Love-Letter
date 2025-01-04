package edu.cmu.f24qa.loveletter.cardactions;

import java.io.PrintStream;
import java.util.Scanner;

import edu.cmu.f24qa.loveletter.Card;
import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;

/**
 * Represents the action associated with the Cardinal card in the Love Letter game.
 * The player gets to swap the cards of two players. The player also gets to see the card of one of the players.
 */
public class CardinalAction extends CardAction {

    /**
     * Cardinal does allow the player to target themselves.
     */
    private final static int VERSION = 1;

    /**
     * Executes the action associated with Cardinal Card.
     *
     * @param game
     *          the game in which the action is executed
     *
     * @param user
     *          the player executing the action
     */
    @Override
    public void execute(final Game game, final Player user) {
        final Scanner in = game.getScanner();
        final PrintStream out = game.getPrinter();
        final Player opponent1 = getOpponent(game, user, VERSION);
        Player opponent2 = getOpponent(game, user, VERSION);
        while (opponent1.equals(opponent2)) {
            out.println("You cannot target the same player twice.");
            opponent2 = getOpponent(game, user, VERSION);
        }

        cardinalLogic(opponent1, opponent2, in, out);
    }
    
    /**
     * The player gets to swap the cards of two players. The player also gets to see the card of one of the players.
     *
     * @param user
     *          the player executing the action
     *
     * @param opponent
     *          the opponent player
     */
    public void cardinalLogic(final Player opponent1, final Player opponent2, final Scanner in, final PrintStream out) {
        final Card userCard = opponent1.removeHand(0);
        final Card opponentCard = opponent2.removeHand(0);

        opponent1.addCardToHand(opponentCard);
        opponent2.addCardToHand(userCard);

        out.println("Whose card would you like to see (0 for " + opponent1.getName() + ", 1 for " + opponent2.getName() + "): ");
        int choice = in.nextInt();
        while (choice != 0 && choice != 1) {
            out.println("Invalid choice. Please enter 0 or 1.");
            out.println("Whose card would you like to see (0 for " + opponent1.getName() + ", 1 for " + opponent2.getName() + "): ");
            choice = in.nextInt();
        }
        if (choice == 0) {
            out.println(opponent1.checkTopCard(0));
        } else {
            out.println(opponent2.checkTopCard(0));
        }
    }
}

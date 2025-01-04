package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Card;
import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;
import edu.cmu.f24qa.loveletter.PlayerList;
import java.io.PrintStream;
import java.util.Scanner;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents the action associated with the Baroness card in the Love Letter game.
 * The player gets to see the card of 1 or 2 players.
 */
public class BaronessAction extends CardAction {

    /** 
     * Baroness doesn't allow the player to target themselves.
     */
    private static final int VERSION = 0;

    /**
     * Executes the action associated with Baroness Card.
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
        final Player opponent2 = getSecondOpponent(game, user, opponent);
        final Card opponentCard = opponent.checkTopCard(0);
        out.println(opponent.getName() + " shows you a " + opponentCard);
        if (opponent2 != null) {
            final Card opponentCard2 = opponent2.checkTopCard(0);
            out.println(opponent2.getName() + " shows you a " + opponentCard2);
        }
    }

    /**
     * Gets the second opponent for the Baroness card.
     *
     * @param game
     *          the game in which the action is executed
     *
     * @param user
     *          the player executing the action
     *
     * @param opponent1
     *          the first opponent
     *
     * @return the second opponent
     */
    @Nullable
    public Player getSecondOpponent(final Game game, final Player user, final Player opponent1) {
        final PrintStream out = game.getPrinter();
        final Scanner in = game.getScanner();
        final PlayerList players = game.getPlayers();
        out.println("You can pick a second player to see their card. Input empty to skip.");
        out.print("Who would you like to target: ");
        String opponentName = in.nextLine();
        Player opponent2 = players.getPlayer(opponentName);
        while (opponentName.equals(opponent1.getName()) || opponent2 == null) {
            if (opponentName.isEmpty()) {
                break;
            }
            out.println("Invalid player. Please enter a valid player name.");
            out.print("Who would you like to target: ");
            opponentName = in.nextLine();
            opponent2 = players.getPlayer(opponentName);
        }
        return opponent2;
    }
}

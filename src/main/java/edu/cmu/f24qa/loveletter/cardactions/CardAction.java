package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Player;
import edu.cmu.f24qa.loveletter.PlayerList;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Represents an action associated with a card in the Love Letter game.
 */
public abstract class CardAction {

    /**
     * Resolves the flag for the getOpponent method. 
     * If version is 0, the player can't target themselves.
     * If version is 1, the player can target themselves.
     *
     * @param version
     *            the version of the card
     * @param opponent
     *            the opponent player
     * @param user
     *            the user player
     * 
     * @return true if the opponent is invalid, false otherwise
     */
    private boolean isInvalidOpponent(final int version, 
                                      @Nullable final Player opponent, 
                                      @NonNull final Player user) {
        final boolean out;
        if (version == 0) {
            out = opponent == null || opponent.equals(user)
            || opponent.isProtected() || !opponent.hasCardsInHand();
        } else {
            out = opponent == null || opponent.isProtected() || !opponent.hasCardsInHand();
        }
        return out;
    }

    /**
     * Useful method for obtaining a chosen target from the player list.
     *
     * @param in
     *            the input stream
     * @param playerList
     *            the list of players
     * @param user
     *            the player choosing an opponent
     * @param sycophantTarget
     *            the sycophant target
     * @param version
     *            0 if the player can't target themselves, 1 otherwise
     * 
     * @return the chosen target player
     */
    public Player getOpponent(final Game game, final Player user, final int version) {
        final Scanner in = game.getScanner();
        final PrintStream out = game.getPrinter();
        final PlayerList players = game.getPlayers();
        final Player sycophantTarget = game.getSycophantTarget();
        Player opponent;
        if (sycophantTarget != null) {
            game.clearSycophantTarget();
            opponent = sycophantTarget;
        } else {
            out.print("Who would you like to target: ");
            String opponentName = in.nextLine();
            opponent = players.getPlayer(opponentName);
            boolean isInvalidOpponent = isInvalidOpponent(version, opponent, user);
            while (isInvalidOpponent || opponent == null) {
                out.println("Invalid target");
                out.print("Who would you like to target: ");
                opponentName = in.nextLine();
                opponent = players.getPlayer(opponentName);
                isInvalidOpponent = isInvalidOpponent(version, opponent, user);
            }
        }
        return opponent;
    }

    /**
     * Executes the action associated with the card.
     *
     * @param game
     *          the game in which the action is executed
     *
     * @param user
     *          the player executing the action
     */
    public abstract void execute(Game game, Player user);
}
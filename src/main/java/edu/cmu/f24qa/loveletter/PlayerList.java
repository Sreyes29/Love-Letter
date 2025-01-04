package edu.cmu.f24qa.loveletter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Manages a list of players in the Love Letter game, allowing for adding players, 
 * tracking the current player, resetting states, and determining round and game winners.
 */
public class PlayerList {

    /**
     * The list of players.
     */
    private final List<Player> players;

    /**
     * The number of tokens needed to win the game.
     */
    private int winThreshold = 5;

    /**
     * The output stream.
     */
    private final PrintStream out;

    /**
     * Constructs a new {@code PlayerList}.
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public PlayerList(final PrintStream out) {
        this.players = new LinkedList<>();
        this.out = out;
    }

    /**
     * Constructs a new {@code PlayerList} with the given list of players and output stream.
     * 
     * @param players
     *           the list of players
     * @param out
     *         the output stream
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public PlayerList(final List<Player> players, final PrintStream out) {
        this.players = players;
        this.out = out;
    }

    /**
     * Update the number of tokens needed to win the game based on the number of players.
     */
    public void updateWinThreshold() {
        final int numPlayers = players.size();
        final boolean twoPlayers = numPlayers == 2;
        final boolean threePlayers = numPlayers == 3;
        final boolean fourOrMorePlayers = numPlayers >= 4;
        if (twoPlayers) {
            winThreshold = 7;
        } else if (threePlayers) {
            winThreshold = 5;
        } else if (fourOrMorePlayers) {
            winThreshold = 4;
        }
    }

    /**
     * Adds a new Player object with the given name to the PlayerList.
     *
     * @param name
     *            the given player name
     *
     * @return true if the player is not already in the list and can be added, false if not
     */
    public boolean addPlayer(final String name) {
        boolean flag = true;
        for (Player p : players) {
            final String playerName = p.getName();
            if (playerName.equalsIgnoreCase(name)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            players.add(new Player(name));
        }
        updateWinThreshold();
        return flag;
    }

    /**
     * Gets the first player in the list and adds them to end of the list.
     *
     * @return the first player in the list
     */
    public Player getCurrentPlayer() {
        final Player current = players.remove(0);
        players.add(current);
        return current;
    }

    /**
     * Set the winner of the round as the first player in the list.
     *
     * @param winner the winner of the round
     */
    public void setRoundWinnerFirst(final Player winner) {
        while (!players.get(0).getName().equals(winner.getName())) {
            players.add(players.remove(0));
        }
    }

    /**
     * Resets all players within the list.
     */
    public void reset() {
        for (Player p : players) {
            p.clearHand();
            p.clearDiscarded();
        }
    }

    /**
     * Prints the used pile of each Player in the list.
     */
    public void printUsedPiles() {
        for (Player p : players) {
            out.println("\n" + p.getName());
            p.printDiscarded(out);
        }
    }

    /**
     * Prints each Player in the list.
     */
    public void print() {
        out.println();
        for (Player p : players) {
            out.println(p);
        }
        out.println();
    }

    /**
     * Checks the list for a single Player with remaining cards.
     *
     * @return true if there is a winner, false if not
     */
    public boolean checkForRoundWinner() {
        int count = 0;
        for (Player p : players) {
            if (p.hasCardsInHand()) {
                count++;
            }
        }
        return count == 1;
    }

    /**
     * Returns the maximum card value among all players.
     *
     * @return the maximum card value
     */
    public int getMaxCardValue() {
        int maxCardValue = -1;
        // Iterate through all players to find the maximum card value
        for (Player p : players) {
            if (!p.hasCardsInHand()) {
                continue;
            }
            final Hand hand = p.getHand();
            final DiscardPile discardPile = p.getDiscarded();
            final int numCounts = discardPile.numCounts();
            for (Card card : hand.getCards()) {
                maxCardValue = Math.max(maxCardValue, card.getValue() + numCounts);
            }
        }
        return maxCardValue;
    }

    /**
     * Returns the winner of the round.
     *
     * @return the round winners
     */
    public List<Player> getRoundWinners() {
        final List<Player> winners = new ArrayList<>();
        final int maxCardValue = getMaxCardValue();
        // Add all players with the maximum card value to the winners list
        for (Player p : players) {
            if (!p.hasCardsInHand()) {
                continue;
            }
            final Hand hand = p.getHand();
            final DiscardPile discardPile = p.getDiscarded();
            final int numCounts = discardPile.numCounts();
            for (Card card : hand.getCards()) {
                if (card.getValue() + numCounts == maxCardValue) {
                    winners.add(p);
                }
            }
        }
        return winners;
    }

    /**
     * Returns the winner of the game.
     *
     * @return the game winner
     */
    public @Nullable Player getGameWinner() {
        Player winner = null;
        for (Player p : players) {
            if (p.getTokens() == winThreshold) {
                winner = p;
                break;
            }
        }
        return winner;
    }

    /**
     * Deals a card to each Player in the list.
     *
     * @param deck
     *            the deck of cards
     */
    public void dealCards(final Deck deck) {
        for (Player p : players) {
            p.addCardToHand(deck.draw());
        }
    }

    /**
     * Gets the player with the given name.
     *
     * @param name
     *                    the name of the desired player
     *
     * @return the player with the given name or null if there is no such player
     */
    public @Nullable Player getPlayer(final String name) {
        Player player = null;
        for (Player p : players) {
            final String playerName = p.getName();
            if (playerName.equalsIgnoreCase(name)) {
                player = p;
                break;
            }
        }
        return player;
    }

    /**
     * Returns all players with the highest total used pile value.
     *
     * @return all players with the highest total used pile value
     */
    public List<Player> compareUsedPiles(final List<Player> roundWinners) {
        final List<Player> winners = new ArrayList<>();
        // Store the sum of each player's used pile
        final List<Integer> discardedSum = new ArrayList<>();
        for (Player p : roundWinners) {
            final DiscardPile discardPile = p.getDiscarded();
            discardedSum.add(discardPile.getSum());
        }
        final Integer maximum = Collections.max(discardedSum);
        // Add all players with the maximum used pile value to the winners list
        for (Player p : roundWinners) {
            final DiscardPile discardPile = p.getDiscarded();
            if (discardPile.getSum().equals(maximum)) {
                winners.add(p);
            }
        }
        return winners;
    }

    /**
     * Check if there is any available player to target.
     *
     * @param player
     *           the player to target
     *
     * @return true if there is any available player to target, false if not
     */
    public boolean checkAvailablePlayers(final Player player) {
        boolean flag = false;
        for (Player p : players) {
            if (!p.equals(player) && p.hasCardsInHand() && !p.isProtected()) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * Returns the list of players.
     *
     * @return the list of players
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public List<Player> getPlayers() {
        return players;
    }

}

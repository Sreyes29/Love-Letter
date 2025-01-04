package edu.cmu.f24qa.loveletter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a player's hand in the Love Letter game, holding the cards
 * currently possessed by the player. The hand allows peeking, adding,
 * removing, and searching for specific types of cards.
 */
public class Hand {
    /** 
     * The cards in the hand.
     */
    private final List<Card> cards;

    /**
     * Constructs an empty {@code Hand}.
     */
    public Hand() {
        this.cards = new ArrayList<>();
    }

    /**
     * Constructs a {@code Hand} with the specified list of cards.
     *
     * @param cards
     *          the list of cards in the hand
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Hand(final List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Peeks the card held by the player.
     *
     * @param idx
     *            the index of the Card to peek
     * @return the card held by the player
     */
    public Card peek(final int idx) {
        return this.cards.get(idx);
    }

    /**
     * Adds a card to the hand.
     *
     * @param card
     *          the card to add
     */
    public void add(final Card card) {
        this.cards.add(card);
    }

    /**
     * Removes the card at the given index from the hand.
     *
     * @param idx
     *            the index of the card
     * @return the card at the given index
     */
    public Card remove(final int idx) {
        return this.cards.remove(idx);
    }

    /**
     * Finds the position of a royal card in the hand.
     *
     * @return the position of a royal card, -1 if no royal card is in hand
     */
    public int royaltyPos() {
        int ret = -1;
        for (Card card : cards) {
            if ("Prince".equals(card.getName()) || "King".equals(card.getName())) {
                ret = cards.indexOf(card);
                break;
            }
        }
        return ret;
    }

    /**
     * Finds the position of a countess in the hand.
     *
     * @return the position of a countess, -1 if no countess is in hand
     */
    public int countessPos() {
        int ret = -1;
        for (Card card : cards) {
            final boolean isCountess = "Countess".equals(card.getName());
            if (isCountess) {
                ret = cards.indexOf(card);
                break;
            }
        }
        return ret;
    }

    /**
     * Checks if there are any cards in the hand.
     *
     * @return {@code true} if the hand has cards; {@code false} otherwise
     */
    public boolean hasCards() {
        return !this.cards.isEmpty();
    }

    /**
     * Clears all cards from the hand.
     */
    public void clear() {
        this.cards.clear();
    }

    /**
     * Prints each card in the hand to the console, each on a new line.
     */
    public void print(final PrintStream out) {
        for (Card card : this.cards) {
            out.println(card);
        }
    }

    /**
     * Returns the list of cards in the hand.
     *
     * @return the list of cards in the hand
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public List<Card> getCards() {
        return this.cards;
    }
}

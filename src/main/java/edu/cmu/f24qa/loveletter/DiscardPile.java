package edu.cmu.f24qa.loveletter;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Represents a discard pile in the Love Letter game, where cards are placed once they are used.
 * The discard pile holds all discarded cards for a player during a round.
 */
public class DiscardPile {
    /** 
     * The cards in the discard pile.
     */
    private final List<Card> cards;

    /**
     * Constructs an empty {@code DiscardPile}.
     */
    public DiscardPile() {
        this.cards = new ArrayList<>();
    }

    /**
     * Constructs a {@code DiscardPile} with the given list of cards.
     *
     * @param cards
     *            the list of cards to add to the discard pile
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public DiscardPile(final List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Adds a card to the discard pile.
     *
     * @param card
     *          the card to add
     */
    public void add(final Card card) {
        this.cards.add(card);
    }

    /**
     * Returns the value of the last card added to the discard pile.
     *
     * @return the value of the last card in the discard pile, or 0 if the pile is empty
     */
    public int value() {
        int ret = 0;
        for (Card card : this.cards) {
            ret = card.getValue();
        }
        return ret;
    }

    /**
     * Clears all cards from the discard pile, making it empty.
     */
    public void clear() {
        this.cards.clear();
    }

    /**
     * Prints all cards in the discard pile to the console.
     * Each card is printed on a new line.
     */
    public void print(final PrintStream out) {
        int counter = 1;
        for (Card card : this.cards) {
            out.print(counter + ". ");
            out.println(card);
            counter += 1;
        }
    }

    /**
     * Returns the list of cards in the discard pile.
     *
     * @return the list of cards in the discard pile
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public List<Card> getCards() {
        return this.cards;
    }

    /**
     * Returns whether the discard pile contains the Constable card.
     *
     * @return {@code true} if the discard pile contains the Constable card, {@code false} otherwise
     */
    public boolean containConstable() {
        return this.cards.contains(Card.CONSTABLE);
    }

    /**
     * Returns the number of Count cards in the discard pile.
     * 
     * @return the number of Count cards in the discard pile
     */
    public int numCounts() {
        return Collections.frequency(cards, Card.COUNT);
    }

    /**
     * Returns the sum of all card values in the discard pile.
     *
     * @return the sum of all card values
     */
    public Integer getSum() {
        int sum = 0;
        for (Card card : this.cards) {
            sum += card.getValue();
        }
        return sum;
    }
}

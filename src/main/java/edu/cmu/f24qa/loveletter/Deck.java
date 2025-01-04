package edu.cmu.f24qa.loveletter;

import java.util.Collections;
import java.util.List;
import java.util.Stack;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Represents a deck of cards in the Love Letter game. The deck is composed of various card types 
 * and provides methods for building, shuffling, and drawing cards.
 */
public class Deck {
    /** 
     * The cards in the deck.
    */
    private final List<Card> cards;

    /**
     * Constructs an empty {@code Deck}.
     */
    public Deck() {
        this.cards = new Stack<>();
    }

    /**
     * Constructs a {@code Deck} with the specified list of cards.
     *
     * @param cards
     *          the list of cards in the deck
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Deck(final List<Card> cards) {
        this.cards = cards;
    }

    /**
     * Builds the deck with a predefined set of cards according to the Love Letter game rules.
     * Adds multiple instances of each card to the deck as required by the game.
     */
    public void build() {
        this.cards.clear();
        for (int i = 0; i < 5; i++) {
            cards.add(Card.GUARD);
        }

        for (int i = 0; i < 2; i++) {
            cards.add(Card.PRIEST);
            cards.add(Card.BARON);
            cards.add(Card.HANDMAIDEN);
            cards.add(Card.PRINCE);
        }

        cards.add(Card.KING);
        cards.add(Card.COUNTESS);
        cards.add(Card.PRINCESS);
    }

    /**
     * Randomize the given deck of cards.
     *
     * @param cards
     *         the list of cards to shuffle
     * @return the shuffled list of cards
     */
    public List<Card> shuffle(final List<Card> cards) {
        final List<Card> shuffled = new Stack<>();
        shuffled.addAll(cards);
        Collections.shuffle(shuffled);
        return shuffled;
    }

    /**
     * Shuffles the deck to randomize the order of cards.
     */
    public void shuffle() {
        final List<Card> newCards = shuffle(cards);
        this.cards.clear();
        this.cards.addAll(newCards);
    }

    /**
     * Draws a card from the top of the deck.
     *
     * @return the card at the top of the deck
     */
    public Card draw() {
        return cards.remove(cards.size() - 1);
    }

    /**
     * Checks if there are more cards remaining in the deck.
     *
     * @return {@code true} if there are cards left in the deck; {@code false} otherwise
     */
    public boolean hasMoreCards() {
        return !cards.isEmpty();
    }

    /**
     * Returns the list of cards in the deck.
     *
     * @return the list of cards in the deck
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public List<Card> getCards() {
        return cards;
    }
}

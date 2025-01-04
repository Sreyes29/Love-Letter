package edu.cmu.f24qa.loveletter;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.PrintStream;

/**
 * Represents a player in the Love Letter game, managing the player's name, hand, discard pile, 
 * protection status, and tokens won. A player can perform actions such as discarding their hand 
 * and toggling protection status.
 */
public class Player {
    /**
     * The name of the player.
     */
    private final String name;

    /**
     * The player's hand of cards.
     */
    private final Hand hand;

    /**
     * The pile of discarded cards.
     */
    private final DiscardPile discarded;

    /**
     * True if the player is protected by a handmaiden, false if not.
     */
    private boolean protect;

    /**
     * The number of blocks the player has won.
     */
    private int tokens;

    /**
     * Constructs a new {@code Player} with the specified name.
     *
     * @param name
     *          the name of the player
     */
    public Player(final String name) {
        this.name = name;
        this.hand = new Hand();
        this.discarded = new DiscardPile();
        this.protect = false;
        this.tokens = 0;
    }

    /**
     * Constructs a new {@code Player} with the specified name, hand, and discard pile.
     * 
     * @param name
     *         the name of the player
     * @param hand
     *        the player's hand
     * @param discarded
     *       the player's discard pile
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Player(final String name, final Hand hand, final DiscardPile discarded) {
        this.name = name;
        this.hand = hand;
        this.discarded = discarded;
        this.protect = false;
        this.tokens = 0;
    }

    /**
     * Adds a token to the player's score, representing a round win.
     */
    public void addToken() {
        this.tokens++;
    }

    /**
     * Eliminates the player from the round by discarding their hand.
     * The first card in the hand is moved to the discard pile.
     */
    public void eliminate() {
        if (this.discarded.containConstable()) {
            this.addToken();
        }
        this.discarded.add(this.hand.remove(0));
    }

    /**
     * Switches the user's level of protection.
     */
    public void switchProtection() {
        this.protect = !this.protect;
    }

    /**
     * Returns the player's current hand.
     *
     * @return the hand of the player
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public Hand getHand() {
        return this.hand;
    }

    /**
     * Returns the player's discard pile.
     *
     * @return the discard pile of the player
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public DiscardPile getDiscarded() {
        return this.discarded;
    }

    /**
     * Checks to see if the user is protected by a handmaiden.
     *
     * @return true, if the player is protected, false if not
     */
    public boolean isProtected() {
        return this.protect;
    }

    /**
     * Returns the number of tokens (rounds won) by the player.
     *
     * @return the player's token count
     */
    public int getTokens() {
        return this.tokens;
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns a string representation of the player, including the player's name and token count.
     *
     * @return a string in the format "{name} ({tokens} tokens)"
     */
    @Override
    public String toString() {
        return this.name + " (" + this.tokens + " tokens)";
    }

    /**
     * Adds a card to the discard pile.
     *
     * @param card
     *          the card to add
     */
    public void addDiscarded(final Card card) {
        this.discarded.add(card);
    }

    /**
     * Returns the value of the last card added to the discard pile.
     *
     * @return the value of the last card in the discard pile, or 0 if the pile is empty
     */
    public int getDiscardedValue() {
        return this.discarded.value();
    }

    /**
     * Clears all cards from the discard pile, making it empty.
     */
    public void clearDiscarded() {
        this.discarded.clear();
    }

    /**
     * Prints all cards in the discard pile to the console.
     * Each card is printed on a new line.
     */
    public void printDiscarded(final PrintStream out) {
        this.discarded.print(out);
    }

    /**
     * Peeks the card held by the player.
     *
     * @param idx
     *            the index of the Card to peek
     * @return the card held by the player
     */
    public Card checkTopCard(final int idx) {
        return this.hand.peek(idx);
    }

    /**
     * Adds a card to the hand.
     *
     * @param card
     *          the card to add
     */
    public void addCardToHand(final Card card) {
        this.hand.add(card);
    }

    /**
     * Removes the card at the given index from the hand.
     *
     * @param idx
     *            the index of the card
     * @return the card at the given index
     */
    public Card removeHand(final int idx) {
        return this.hand.remove(idx);
    }

    /**
     * Finds the position of a royal card in the hand.
     *
     * @return the position of a royal card, -1 if no royal card is in hand
     */
    public int getHandRoyaltyPos() {
        return this.hand.royaltyPos();
    }

    /**
     * Finds the position of a countess in the hand.
     *
     * @return the position of a countess, -1 if no countess is in hand
     */
    public int getHandCountessPos() {
        return this.hand.countessPos();
    }

    /**
     * Checks if there are any cards in the hand.
     *
     * @return {@code true} if the hand has cards; {@code false} otherwise
     */
    public boolean hasCardsInHand() {
        return this.hand.hasCards();
    }

    /**
     * Clears all cards from the hand.
     */
    public void clearHand() {
        this.hand.clear();
    }

    /**
     * Prints each card in the hand to the console, each on a new line.
     */
    public void printHand(final PrintStream out) {
        this.hand.print(out);
    }
}

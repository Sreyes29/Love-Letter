package edu.cmu.f24qa.loveletter;

import edu.cmu.f24qa.loveletter.cardactions.*;

/**
 * Represents a card in the Love Letter game, where each card has a name and a specific value.
 * Each card in this enumeration represents a unique card in the game with its associated
 * name and value.
 */
public enum Card {
    GUARD("Guard", 1, new GuardAction()),
    PRIEST("Priest", 2, new PriestAction()),
    BARON("Baron", 3, new BaronAction()),
    HANDMAIDEN("Handmaiden", 4, new HandmaidenAction()),
    PRINCE("Prince", 5, new PrinceAction()),
    KING("King", 6, new KingAction()),
    COUNTESS("Countess", 7, new CountessAction()),
    PRINCESS("Princess", 8, new PrincessAction()),
    CARDINAL("Cardinal", 2, new CardinalAction()),
    BARONESS("Baroness", 3, new BaronessAction()),
    SYCOPHANT("Sycophant", 4, new SycophantAction()),
    COUNT("Count", 5, new CountAction()),
    ASSASSIN("Assassin", 0, new AssassinAction()),
    CONSTABLE("Constable", 6, new ConstableAction()),
    QUEEN("Queen", 7, new QueenAction());

    /**
     * The name of the card.
     */
    private final String name;

    /**
     * The value associated with the card.
     */
    private final int value;

    /**
     * The action associated with the card.
     */
    private final CardAction action;

    /**
     * All possible card names. (Redundant)
     */
    public static final String[] CARD_NAMES = {
        "guard",
        "priest",
        "baron",
        "handmaiden",
        "prince",
        "king",
        "countess",
        "princess"
    };

    /**
     * Constructor for a card object.
     *
     * @param name
     *            the name of the card
     * @param value
     *            the value of the card
     */
    Card(final String name, final int value, final CardAction action) {
        this.name = name;
        this.value = value;
        this.action = action;
    }

    /**
     * Gets the value of the card.
     *
     * @return the value of the card
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Gets the name of the card.
     *
     * @return the name of the card
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the action associated with the card.
     *
     * @return the action associated with the card
     */
    public CardAction getAction() {
        return this.action;
    }

    /**
     * Executes the action associated with the card.
     *
     * @param game
     *            the game in which the action is executed
     */
    public void executeAction(final Game game, final Player user) {
        this.action.execute(game, user);
    }

    /**
     * Returns a string representation of the card, including its name and value.
     *
     * @return a string in the format "{name} ({value})"
     */
    @Override
    public String toString() {
        return this.name + " (" + value + ")";
    }
}

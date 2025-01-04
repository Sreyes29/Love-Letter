package edu.cmu.f24qa.loveletter.cardactions;

import edu.cmu.f24qa.loveletter.Card;
import edu.cmu.f24qa.loveletter.Deck;
import edu.cmu.f24qa.loveletter.Game;
import edu.cmu.f24qa.loveletter.Hand;
import edu.cmu.f24qa.loveletter.Player;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

/**
 * Represents the action associated with the Guard card in the Love Letter game.
 * The player executing the action guesses the card held by the opponent.
 * If the guess is correct, the opponent is eliminated from the game.
 * If the guess is incorrect, nothing happens.
 */
public class GuardAction extends CardAction {

    /** 
     * Guard doesn't allow the player to target themselves.
     */
    private final static int VERSION = 0;

    /**
     * Executes the action associated with Guard Card.
     *
     * @param game
     *          the game in which the action is executed
     *
     * @param user
     *          the player executing the action
     */
    @Override
    public void execute(final Game game, final Player user) {
        final Player opponent = getOpponent(game, user, VERSION);

        guardLogic(game, opponent, user);
    }

    /**
     * Guesses the card held by the opponent.
     * If the opponent has an assassin card, the player is eliminated from the game.
     * Otherwise, if the guess is correct, the opponent is eliminated from the game.
     * If the guess is incorrect, nothing happens.
     *
     * @param in
     *          the scanner object to read input
     *
     * @param opponent
     *          the opponent player
     */
    public void guardLogic(final Game game, final Player opponent, final Player user) {
        final Scanner in = game.getScanner();
        final PrintStream out = game.getPrinter();
        final Deck deck = game.getDeck();
        final List<Card> deckCards = deck.getCards();
        
        out.print("Which card would you like to guess: ");
        String cardName = in.nextLine();
        while (cardName.equals(Card.GUARD.getName())) {
            out.println("You cannot guess this card");
            out.print("Which card would you like to guess: ");
            cardName = in.nextLine();
        }

        final Card opponentCard = opponent.checkTopCard(0);
        final Hand opponentHand = opponent.getHand();
        final String opponentName = opponentCard.getName();
        final Boolean hasAssassin = hasAssassin(opponentHand.getCards());
        
        // If the opponent has assassin the player is eliminated
        if (hasAssassin) {
            out.println("The opponent has an assassin. You have been eliminated.");
            user.eliminate();
            // Opponent discards assassin and draws a new card
            opponent.eliminate();
            // If the deck is empty they draw from the removed pile
            if (deckCards.isEmpty()) {
                opponentHand.add(game.drawFromRemoved());
            }
            // Otherwise they draw from the deck
            else {
                opponentHand.add(deck.draw());
            }
        }
        else if (opponentName.equals(cardName)) {
            out.println("You have guessed correctly!");
            opponent.eliminate();
        } else {
            out.println("You have guessed incorrectly");
        }
    }

    /**
     * Checks if the opponent has an assassin card.
     *
     * @param opponentCards
     *          the list of cards held by the opponent
     *
     * @return true if the opponent has an assassin card, false otherwise
     */
    public boolean hasAssassin(final List<Card> opponentCards) {
        final String opponentCard = opponentCards.get(0).getName();
        return opponentCard.equals(Card.ASSASSIN.getName());
    }
    
}

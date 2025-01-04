package edu.cmu.f24qa.loveletter;

import edu.umd.cs.findbugs.annotations.Nullable;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Manages the main gameplay mechanics for the Love Letter game. The Game class
 * controls the flow of each round, including managing players, turns, and 
 * the interactions between players' hands and the deck.
 */
public class Game {
    /**
     * The list of players.
     */
    private final PlayerList players;

    /**
     * The deck of cards.
     */
    private final Deck deck;

    /**
     * The input stream.
     */
    private Scanner in = new Scanner(System.in, StandardCharsets.UTF_8);

    /**
     * The output stream.
     */
    private PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);  

    /**
     * The initial cards that are removed from the game.
     */
    protected List<Card> removed;

    /**
     * The player that the sycophant is targeting.
     */
    @Nullable
    private Player sycophantTarget;

    /**
     * Constructs a new Game instance without any parameters.
     */
    public Game() {
        this.players = new PlayerList(this.out);
        this.deck = new Deck();
        this.removed = new ArrayList<>();
        this.sycophantTarget = null;
    }

    /**
     * Constructs a new Game instance with the given player list, deck, printer and scanner.
     *
     * @param players
     *            the player list
     * @param deck
     *            the deck of cards
     * @param in
     *            the input stream
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Game(
        final PlayerList players, 
        final Deck deck, 
        final Scanner in, 
        final PrintStream out) {
        this.players = players;
        this.deck = deck;
        this.in = in;
        this.out = out;
        this.removed = new ArrayList<>();
        this.sycophantTarget = null;
    }

    /**
     * Constructs a new Game instance with the given player list, deck, 
     * scanner, printer, and removed pile.
     *
     * @param players
     *            the player list
     * @param deck
     *            the deck of cards
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @param sycophantTarget
     *            the sycophant target
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Game(
        final PlayerList players, 
        final Deck deck, 
        final Scanner in, 
        final PrintStream out,
        final Player sycophantTarget) {
        this.players = players;
        this.deck = deck;
        this.in = in;
        this.out = out;
        this.removed = new ArrayList<>();
        this.sycophantTarget = sycophantTarget;
    }

    /**
     * Constructs a new Game instance with the given player list, deck, 
     * scanner, printer, and removed pile.
     *
     * @param players
     *            the player list
     * @param deck
     *            the deck of cards
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @param removed
     *            the removed pile
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public Game(
        final PlayerList players, 
        final Deck deck, 
        final Scanner in, 
        final PrintStream out,
        final List<Card> removed) {
        this.players = players;
        this.deck = deck;
        this.in = in;
        this.out = out;
        this.removed = removed;
    }

    /**
     * Returns the input stream.
     *
     * @return the Scanner in
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public Scanner getScanner() {
        return in;
    }

    /**
     * Returns the player that the sycophant is targeting.
     *
     * @return the sycophant target
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    @Nullable
    public Player getSycophantTarget() {
        return sycophantTarget;
    }

    /**
     * Sets the player that the sycophant is targeting.
     *
     * @param target
     *            the player to target
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    @Nullable
    public void setSycophantTarget(final Player target) {
        this.sycophantTarget = target;
    }

    /**
     * Clears the sycophantTarget, setting it to null.
     *
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public void clearSycophantTarget() {
        this.sycophantTarget = null;
    }

    /**
     * Returns the output stream.
     *
     * @return the PrintStream out
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public PrintStream getPrinter() {
        return out;
    }

    /**
     * Returns the player list.
     *
     * @return the player list
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
    public PlayerList getPlayers() {
        return players;
    }

    /**
     * Returns the deck.
     *
     * @return deck
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Initializes the player list by prompting for player names.
     * Continuously prompts the user to enter a player name, adding each new player to the game.
     * Inputting an empty line signals the end of player entry.
     */
    public void setPlayers() {
        out.print("Enter player name (empty when done): ");
        String name = in.nextLine();
        while (!name.isEmpty()) {
            this.players.addPlayer(name);
            out.print("Enter player name (empty when done): ");
            name = in.nextLine();
        }
    }

    /**
     * The main game loop that repeatedly executes rounds until the game ends.
     * In each iteration, it plays a round and checks if the game has ended.
     * Once the game is over, it calls the endGame() method to finalize the game.
     *
     * @return true if the game loop completes successfully.
     */
    public boolean gameLoop() {
        while (!this.checkGameEnd()) {
            this.playRound();
        }
        this.endGame();
        return true;
    }
    
    /**
     * The round loop that manages the progression of a single round of the game.
     * Initializes the round, then repeatedly allows each player to take their turn
     * until the round ends.
     *
     * @return true if the round completes successfully.
     */
    public boolean playRound() {
        boolean gameEndInRound = false;
        this.initializeRound();
        while (!checkRoundEnd()) {
            final Player currentPlayer = players.getCurrentPlayer();
            this.playTurn(currentPlayer);
            if (this.checkGameEnd()){
                gameEndInRound = true;
                break;
            }
        }
        if (!gameEndInRound){
            this.endRound();
        }
        return true;
    }

    /**
     * Removes the cards from the game that are not used in a round.
     */
    private void removeCards() {
        out.println("Removing the top card from the game...");
        removed.add(deck.draw());
        out.println("Top card removed, not revealed.");
        final int minSize = 2;
        if (players.getPlayers().size() == minSize) {
            out.println("Removing and revealing the next three cards from the game...");
            removed.add(deck.draw());
            removed.add(deck.draw());
            removed.add(deck.draw());
        }
    }

    /**
     * Prints the cards that have been removed from the game.
     * Only prints the cards that have been revealed, not the top card.
     */
    private void printRemoved() {
        out.println("The following cards have been removed and revealed from the game:");
        boolean flag = false;
        for (Card card : removed) {
            if (flag) {
                out.println(card);
            }
            flag = true;
        }
    }

    /**
     * Initializes the round.
     *
     * @return true once the round is initialized
     */
    public boolean initializeRound() {
        players.reset();
        setDeck();
        removeCards();
        players.dealCards(deck);
        return true;
    }

    /**
     * Check if the round has ended.
     *
     * @return true if a player has won the round or the deck is empty
     */
    public boolean checkRoundEnd() {
        return players.checkForRoundWinner() || !deck.hasMoreCards();
    }

    /**
     * The logic for a player's turn.
     *
     * @param currentPlayer the current player
     * @param hand the current player's hand
     */
    public void turnLogic(final Player currentPlayer, final Hand hand) {
        if (currentPlayer.isProtected()) {
            currentPlayer.switchProtection();
        }

        hand.add(deck.draw());

        final int royaltyPos = hand.royaltyPos();
        final int countessPos = hand.countessPos();
        final boolean noCountess = countessPos == -1;
        final boolean noRoyalty = royaltyPos == -1;
        if (noCountess || noRoyalty) {
            playCard(getCard(currentPlayer), currentPlayer);
        } else {
            playCard(hand.remove(countessPos), currentPlayer);
        }
    }
    
    /**
     * Plays a turn for the current player.
     *
     * @param currentPlayer the current player
     * 
     * @return true if a player has played their turn or false if they cannot
     */
    public boolean playTurn(final Player currentPlayer) {
        final Hand hand = currentPlayer.getHand();
        boolean hasPlayed = false;
        if (hand.hasCards()) {
            players.printUsedPiles();
            printRemoved();
            out.println("\n" + currentPlayer.getName() + "'s turn:");

            turnLogic(currentPlayer, hand);
            hasPlayed = true;
        }
        return hasPlayed;
    }
    
    /**
     * Ends the round and prints the winners.
     *
     * @return the winners of the round
     */
    public List<Player> endRound() {
        // Getting players with the maximum card value
        final List<Player> roundWinners = players.getRoundWinners();
        final List<Player> winners;
        final boolean multipleWinners = roundWinners.size() > 1;
        
        // There is a tie, so we compare sum of used piles
        if (multipleWinners) {
            winners = players.compareUsedPiles(roundWinners);
        }
        else {
            winners = roundWinners;
        }
        for (Player winner : winners) {
            winner.addToken();
            out.println(winner.getName() + " has won this round!");
        }
        players.setRoundWinnerFirst(winners.get(0));
        players.print();
        return winners;
    }

    /**
     * Check if the game has ended.
     *
     * @return true if a player has won the game
     */
    public boolean checkGameEnd() {
        return players.getGameWinner() != null;
    }

    /**
     * Ends the game and prints the winner.
     *
     * @return true after the game ends
     */
    public boolean endGame() {
        final Player gameWinner = players.getGameWinner();
        out.println(gameWinner + " has won the game and the heart of the princess!");
        return true;
    }

    /**
     * Builds and shuffles a new deck for the game round.
     */
    protected void setDeck() {
        this.deck.build();
        this.deck.shuffle();
    }

    /**
     * Checks if the player has a royal card and the Countess.
     * 
     * @param card
     * @param user
     * @return true if the player has a royal card and the Countess, false otherwise
     */
    public boolean checkRoyalCountess(final Card card, final Player user) {
        return (card.getName().equals(Card.PRINCE.getName()) || card.getName().equals(Card.KING.getName())) &&
               user.checkTopCard(0).getName().equals(Card.COUNTESS.getName());
    }

    /**
     * Checks if the card target an opponent.
     *
     * @param card
     * @return true if the card target an opponent, false otherwise
     */
    public boolean checkTargetAction(final Card card) {
        return card.getName().equals(Card.GUARD.getName())
            || card.getName().equals(Card.PRIEST.getName())
            || card.getName().equals(Card.BARON.getName())
            || card.getName().equals(Card.KING.getName())
            || card.getName().equals(Card.BARONESS.getName())
            || card.getName().equals(Card.CARDINAL.getName())
            || card.getName().equals(Card.QUEEN.getName());
    }

    /**
     * Plays a card from the user's hand.
     *
     * @param card
     *            the played card
     * @param user
     *            the player of the card
     */
    public void playCard(final Card card, final Player user) {
        final boolean royalFlag = checkRoyalCountess(card, user);
        if (royalFlag) {
            out.println("You must play the Countess!");
            user.addCardToHand(card);
            playCard(user.removeHand(0), user);
        } else if (!this.players.checkAvailablePlayers(user) && this.checkTargetAction(card)) { 
            user.addDiscarded(card);
            out.println("No other players to target. Your card is discarded.");
        } else {
            user.addDiscarded(card);
            card.executeAction(this, user);
        }
        // Reset sycophant target if the player did not play a sycophant
        if (!card.getName().equals(Card.SYCOPHANT.getName())) {
            clearSycophantTarget();
        }
    }

    /**
     * Allows for the user to pick a card from their hand to play.
     *
     * @param user
     *            the current player
     *
     * @return the chosen card
     */
    protected Card getCard(final Player user) {
        user.printHand(out);
        out.println();
        out.print("Which card would you like to play (0 for first, 1 for second): ");
        final String cardPosition = in.nextLine();
        final int idx = Integer.parseInt(cardPosition);
        return user.removeHand(idx);
    }

    /**
     * Draws a card from the removed pile.
     *
     * @return the card drawn from the removed pile
     */
    public Card drawFromRemoved() {
        return removed.remove(0);
    }
}

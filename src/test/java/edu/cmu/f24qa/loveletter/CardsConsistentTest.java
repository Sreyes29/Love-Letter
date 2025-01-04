package edu.cmu.f24qa.loveletter;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CardsConsistentTest {

    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    public Deck mockDeck;

    public CardsConsistentTest() {
        this.mockDeck = Mockito.spy(new Deck());
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
        this.mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
        this.mockPlayerList.addPlayer("Alice");
        this.mockPlayerList.addPlayer("Bob");
    }

    @BeforeEach
    public void setup() {
        this.mockDeck = Mockito.spy(new Deck());
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
        this.mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
        this.mockPlayerList.addPlayer("Alice");
        this.mockPlayerList.addPlayer("Bob");
    }

    /**
     * Returns the counts of each type of card in the game.
     *
     * @param game the game instance
     * @return an array of card counts
     */
    public int[] getCardCounts(Game game) {
        int[] cardCounts = new int[8];
        // get all the cards in the game
        List<Card> cards = game.getDeck().getCards();
        // Need to add the cards that are removed at the beginning
        PlayerList players = game.getPlayers();
        for (Player p : players.getPlayers()) {
            Hand hand = p.getHand();
            for (Card card : hand.getCards()) {
                cards.add(card);
            }
            DiscardPile discardPile = p.getDiscarded();
            for (Card card : discardPile.getCards()) {
                cards.add(card);
            }
        }

        for (Card card : cards) {
            switch (card) {
                case GUARD:
                    cardCounts[0]++;
                    break;
                case PRIEST:
                    cardCounts[1]++;
                    break;
                case BARON:
                    cardCounts[2]++;
                    break;
                case HANDMAIDEN:
                    cardCounts[3]++;
                    break;
                case PRINCE:
                    cardCounts[4]++;
                    break;
                case KING:
                    cardCounts[5]++;
                    break;
                case COUNTESS:
                    cardCounts[6]++;
                    break;
                case PRINCESS:
                    cardCounts[7]++;
                    break;
                default:
                    break;
            }
        }
        return cardCounts;
    }

    /**
     * Tests that the initial deck is set up correctly with the right number of each type of card.
     */
    @Test
    public void testInitialDeckCorrect() {
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));

        mockGame.setDeck();

        Assertions.assertEquals(16, mockDeck.getCards().size());
        int[] cardCounts = getCardCounts(mockGame);
        Assertions.assertArrayEquals(new int[]{5, 2, 2, 2, 2, 1, 1, 1}, cardCounts);
    }
}
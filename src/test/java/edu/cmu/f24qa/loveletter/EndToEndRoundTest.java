package edu.cmu.f24qa.loveletter;

import org.junit.jupiter.api.Test;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import java.nio.charset.StandardCharsets;
import org.mockito.Mockito;

public class EndToEndRoundTest {
    
    @Test
    public void testEndToEndGame() {
        // Create Mocks 
        Scanner mockScanner = Mockito.mock(Scanner.class);
        PrintStream out = Mockito.spy(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        PlayerList playerList = new PlayerList(out);
        playerList.addPlayer("p1");
        playerList.addPlayer("p2");
        playerList.addPlayer("p3");
        playerList.addPlayer("p4");
        List<Card> cards = new Stack<>();
        cards.addAll(Arrays.asList(
            Card.GUARD, Card.GUARD, Card.PRIEST, Card.COUNTESS,
            Card.HANDMAIDEN, Card.BARON, Card.BARON, Card.PRINCE,
            Card.KING, Card.GUARD, Card.PRIEST, Card.HANDMAIDEN,
            Card.GUARD, Card.PRINCESS, Card.GUARD, Card.PRINCE
        ));
        Deck deck = new Deck(cards);

        Game game = Mockito.spy(new Game(playerList, deck, mockScanner, out));
    
        // Define Mock Behaviors
        Mockito.doNothing().when(game).setDeck();
        Mockito.when(mockScanner.nextLine()).thenReturn(
            // Initial Deck (bottom to top): Guard, Guard, Priest, Countess, Handmaiden, Baron, Baron, Prince, King, Guard, Priest, Handmaiden, Guard, Princess, Guard, Prince
            // Deal Cards, Discarded Card: Prince, p1: Guard, p2: Princess, p3: Guard, p4: Handmaiden
            // Deck: Guard, Guard, Priest, Countess, Handmaiden, Baron, Baron, Prince, King, Guard, Priest
            // p1's turn, draws priest, plays Guard, selects p2 and guesses Baron, wrong guess
            "0", "p2", "Baron",
            // p2's turn, draws guard, plays Guard, selects p3 and guesses Priest, wrong guess
            "1", "p3", "Priest",
            // p3's turn, draws king, plays Guard, selects p4 and guesses Princess, wrong guess
            "0", "p4", "Princess",
            // p4's turn, draws prince, plays Handmaiden
            "0",
            // Turn Loop ended with cards p1: Priest, p2: Princess, p3: King, p4: Prince
            // p1's turn, draws baron, plays priest, selects p4 (invalid), selects p3 (king is revealed)
            "0", "p4", "p3",
            // p2's turn, draws baron, plays baron, selects p1, p1 is eliminated
            "1", "p1",
            // p3's turn, draws handmaiden, plays handmaiden
            "1",
            // p4's turn, draws countess, forced to play countess
            // Turn Loop ended with cards p2: Baron, p3: Handmaiden, p4: Prince
            // p2's turn, draws priest, plays priest, selects p3 (invalid), selects p4 (prince is revealed)
            "1", "p3", "p4",
            // p3's turn, draws guard, plays guard, selects p4, guesses prince, correct guess, p4 is eliminated
            "1", "p4", "Prince",
            // Turn Loop ended with cards p2: Princess, p3: king
            // p2's turn, draws guard, selects p1 (invalid), selects p2 (invalid), selects p3, wrong guess
            "1", "p1", "p2", "p3", "Prince"
            // deck is empty, game ends
            // p2 wins, gains a token (princess > king)
        );

        // Run round
        game.playRound();

        // Verify
        Assertions.assertEquals(playerList.getPlayer("p2").getHand().peek(0), Card.PRINCESS);
        Assertions.assertEquals(playerList.getPlayer("p3").getHand().peek(0), Card.KING);
        Assertions.assertFalse(playerList.getPlayer("p1").hasCardsInHand());
        Assertions.assertFalse(playerList.getPlayer("p4").hasCardsInHand());
        Mockito.verify(out, Mockito.times(1)).println("p2 has won this round!");
    }
}

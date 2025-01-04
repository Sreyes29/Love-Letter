package edu.cmu.f24qa.loveletter;

import org.junit.jupiter.api.Test;
import java.io.PrintStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import java.nio.charset.StandardCharsets;
import org.mockito.Mockito;

public class EndToEndRoundFileTest {

    @Test
    public void testEndToEndRound() {
        File file = new File("src/test/java/edu/cmu/f24qa/loveletter/endtoendinput/endtoendround.txt");
        try {
            // Setup
            Scanner in = new Scanner(file);
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
            
            Game game = Mockito.spy(new Game(playerList, deck, in, out));
    
            Mockito.doNothing().when(game).setDeck();
    
            // Run round
            game.playRound();

            // Verify
            Assertions.assertEquals(playerList.getPlayer("p2").getHand().peek(0), Card.PRINCESS);
            Assertions.assertEquals(playerList.getPlayer("p3").getHand().peek(0), Card.KING);
            Assertions.assertFalse(playerList.getPlayer("p1").hasCardsInHand());
            Assertions.assertFalse(playerList.getPlayer("p4").hasCardsInHand());
            Mockito.verify(out, Mockito.times(1)).println("p2 has won this round!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

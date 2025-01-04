package edu.cmu.f24qa.loveletter;

import static org.mockito.ArgumentMatchers.anyList;

import java.io.File;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EndToEndGameFileTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testEndToEndGame() {
        File file = new File("src/test/java/edu/cmu/f24qa/loveletter/endtoendinput/endtoendgame.txt");
        Scanner in;
        try {
            in = new Scanner(file);
        
            // Create Mocks 
            PrintStream mockPrintStream = Mockito.spy(new PrintStream(System.out, true, StandardCharsets.UTF_8));
            Deck mockDeck = Mockito.spy(new Deck());
            PlayerList mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
            Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, in, mockPrintStream));

            // Cards for Each Round
            List<Card> cards1 = new Stack<>();
            List<Card> cards2 = new Stack<>();
            List<Card> cards3 = new Stack<>();
            List<Card> cards4 = new Stack<>();
            List<Card> cards5 = new Stack<>();
            List<Card> cards6 = new Stack<>();
            List<Card> cards7 = new Stack<>();

            // Add Cards for Each Round
            cards1.addAll(List.of(
                Card.GUARD, Card.GUARD, Card.PRIEST, Card.COUNTESS,
                Card.HANDMAIDEN, Card.BARON, Card.BARON, Card.PRINCE,
                Card.KING, Card.GUARD, Card.PRIEST, Card.HANDMAIDEN,
                Card.GUARD, Card.PRINCESS, Card.GUARD, Card.PRINCE
            ));

            cards2.addAll(List.of(
                Card.GUARD, Card.PRIEST, Card.PRINCESS, Card.GUARD,
                Card.PRINCE, Card.HANDMAIDEN, Card.GUARD, Card.COUNTESS,
                Card.PRIEST, Card.GUARD, Card.BARON, Card.PRINCE,
                Card.KING, Card.HANDMAIDEN, Card.BARON, Card.GUARD
            ));

            cards3.addAll(List.of(
                Card.BARON, Card.PRINCE, Card.HANDMAIDEN, Card.GUARD,
                Card.GUARD, Card.HANDMAIDEN, Card.GUARD, Card.PRIEST,
                Card.PRINCESS, Card.KING, Card.PRIEST, Card.BARON,
                Card.PRINCE, Card.GUARD, Card.GUARD, Card.COUNTESS
            ));

            cards4.addAll(List.of(
                Card.PRIEST, Card.BARON, Card.GUARD, Card.GUARD,
                Card.KING, Card.HANDMAIDEN, Card.GUARD, Card.PRINCESS,
                Card.HANDMAIDEN, Card.PRINCE, Card.PRINCE, Card.GUARD,
                Card.GUARD, Card.COUNTESS, Card.BARON, Card.PRIEST
            ));

            cards5.addAll(List.of(
                Card.PRIEST, Card.COUNTESS, Card.PRIEST, Card.GUARD,
                Card.PRINCE, Card.HANDMAIDEN, Card.PRINCESS, Card.GUARD,
                Card.HANDMAIDEN, Card.PRINCE, Card.BARON, Card.KING,
                Card.GUARD, Card.BARON, Card.GUARD, Card.GUARD
            ));

            cards6.addAll(List.of(
                Card.PRINCE, Card.PRIEST, Card.GUARD, Card.PRIEST,
                Card.BARON, Card.BARON, Card.GUARD, Card.PRINCE,
                Card.GUARD, Card.KING, Card.PRINCESS, Card.GUARD,
                Card.GUARD, Card.HANDMAIDEN, Card.HANDMAIDEN, Card.COUNTESS
            ));

            cards7.addAll(List.of(
                Card.PRINCE, Card.PRINCESS, Card.BARON, Card.GUARD,
                Card.HANDMAIDEN, Card.GUARD, Card.KING, Card.COUNTESS,
                Card.GUARD, Card.GUARD, Card.HANDMAIDEN, Card.BARON,
                Card.PRIEST, Card.GUARD, Card.PRIEST, Card.PRINCE
            ));

            // Define Mock Behaviors
            Mockito.when(mockDeck.shuffle(anyList())).thenReturn(cards1, cards2, cards3, cards4, cards5, cards6, cards7);

            // Act
            mockGame.setPlayers();
            mockGame.gameLoop();

            // Assert
            Mockito.verify(mockPrintStream).println("p2 (4 tokens) has won the game and the heart of the princess!");
            Assertions.assertEquals(0, mockPlayerList.getPlayer("p1").getTokens());
            Assertions.assertEquals(4, mockPlayerList.getPlayer("p2").getTokens());
            Assertions.assertEquals(1, mockPlayerList.getPlayer("p3").getTokens());
            Assertions.assertEquals(2, mockPlayerList.getPlayer("p4").getTokens());

        } catch (Exception e) {
            e.printStackTrace();
            assert false;
        }
    }
}
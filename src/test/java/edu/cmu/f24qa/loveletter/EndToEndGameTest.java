package edu.cmu.f24qa.loveletter;

import static org.mockito.ArgumentMatchers.anyList;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EndToEndGameTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testEndToEndGame() {
        // Create Mocks 
        Scanner mockScanner = Mockito.mock(Scanner.class);
        PrintStream mockPrintStream = Mockito.spy(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        Deck mockDeck = Mockito.spy(new Deck());
        PlayerList mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));
        Game mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));

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
        Mockito.when(mockScanner.nextLine()).thenReturn(
            // ===== Set Players =====
            "p1", "p2", "p3", "p4", "",

            // ===== Round 1 =====
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
            // Turn Loop ended with cards p2: Baron, p3: Handmaiden
            // p2's turn, draws guard, plays guard, selects p3, guesses handmaiden, correct guess, p3 is eliminated
            "1", "p3", "Handmaiden",
            // Turn Loop ended with cards p2: Baron
            // p2 wins the round
            
            // ===== Round 2 =====
            // Initial Deck (bottom to top): Guard, Priest, Princess, Guard, Prince, Handmaiden, Guard, Countess, Priest, Guard, Baron, Prince, King, Handmaiden, Baron, Guard
            // Deal Cards, Discarded Card: Guard, p2: Baron, p3: Handmaiden, p4: King, p1: Prince
            // Deck: Guard, Priest, Princess, Guard, Prince, Handmaiden, Guard, Countess, Priest, Guard, Baron
            // p2's turn, draws Baron, plays Baron, selects p1, p2 is eliminated
            "1", "p1",
            // p3's turn, draws Guard, plays Guard, selects p4 and guesses Princess, wrong guess
            "1", "p4", "Princess",
            // p4's turn, draws Priest, plays Priest, selects p2 (invalid), selects "aaaa" (invalid), selects p1 (Prince is revealed)
            "1", "2", "aaaa", "p1",
            // p1's turn, draws Countess, forced to play Countess
            // Turn loop ended with cards p3: Handmaiden, p4: King, p1: Prince
            // p3's turn, draws Guard, plays Guard, selects p1 and guesses Prince, p1 is eliminated
            "1", "p1", "Prince",
            // p4's turn, draws Handmaiden, plays Handmaiden
            "1",
            // Turn loop ended with cards p3: Handmaiden, p4: King
            // p3's turn, draws Prince, plays Handmaiden
            "0",
            // p4's turn, draws Guard, plays Guard, no target
            "1",
            // Turn loop ended with cards p3: Prince, p4: King
            // p3's turn, draws Princess, plays Prince, selects p4, p4 draws Priest
            "0", "p4",
            // p4's turn, draws Guard, selects p3 and guesses Princess, p3 is eliminated
            "1", "p3", "Princess",
            // p4 wins the round

            // ===== Round 3 =====
            // Initial Deck (bottom to top): Baron, Prince, Handmaiden, Guard, Guard, Handmaiden, Guard, Priest, Princess, King, Priest, Baron, Prince, Guard, Guard, Countess
            // Deal Cards, Discarded Card: Countess, p4: Guard, p1: Guard, p2: Prince, p3: Baron
            // Deck: Baron, Prince, Handmaiden, Guard, Guard, Handmaiden, Guard, Priest, Princess, King, Priest
            // p4's turn, draws Priest, plays Guard, selects p3 and guesses Guard (invalid), then guesses Baron, p3 is eliminated
            "0", "p3", "Guard", "Baron",
            // p1's turn, draws King, plays Guard, selects p4 and guesses Baron, wrong guess
            "0", "p4", "Baron",
            // p2's turn, draws Princess, plays Prince, selects p4, p4 draws Priest
            "0", "p4",
            // Turn loop ended with cards p4: Priest, p1: King, p2: Princess
            // p4's turn, draws Guard, plays Guard, selects p2 and guesses Baron, wrong guess
            "1", "p2", "Baron",
            // p1's turn, draws Handmaiden, plays Handmaiden
            "1",
            // p2's turn, draws Guard, plays Guard, select p4 and guesses Priest, p4 is eliminated
            "1", "p4", "Priest",
            // Turn loop ended with cards p1: King, p2: Princess
            // p1's turn, draws Guard, plays Guard, select p2 and guesses Prince, wrong guess
            "1", "p2", "Prince",
            // p2's turn, draws Handmaiden, plays Handmaiden
            "1",
            // Turn loop ended with cards p1: King, p2: Princess
            // p1's turn, draws Prince, plays Prince, selects self, p1 draws Baron
            "1", "p1",
            // p2 wins the round (Princess > Baron)

            // ===== Round 4 =====
            // Initial Deck (bottom to top): Priest, Baron, Guard, Guard, King, Handmaiden, Guard, Princess, Handmaiden, Prince, Prince, Guard, Guard, Countess, Baron, Priest
            // Deal Cards, Discarded Card: Priest, p2: Baron, p3: Countess, p4: Guard, p1: Guard
            // Deck: Priest, Baron, Guard, Guard, King, Handmaiden, Guard, Princess, Handmaiden, Prince, Prince
            // p2's turn, draws Prince, plays Baron, selects p1, p1 is eliminated
            "0", "p1",
            // p3's turn, draws Prince, forced to play Countess
            // p4's turn, draws Handmaiden, plays Guard, selects p3 and guesses Prince, p3 is eliminated
            "0", "p3", "Prince",
            // Turn loop ended with cards p2: Prince, p4: Handmaiden
            // p2's turn, draws Princess, plays Prince, selects p4, p4 draws Guard
            "0", "p4",
            // p4's turn, draws Handmaiden, plays Guard, selects p2 and guesses Baron, wrong guess
            "0", "p2", "Baron",
            // Turn loop ended with cards p2: Princess, p4: Handmaiden
            // p2's turn, draws King, plays King, p2 and p4 hand are swapped
            "1", "p4",
            // p4's turn, draws Guard, selects p2 and guesses Handmaiden, p2 is eliminated
            "1", "p2", "Handmaiden",
            // p4 wins the round

            // ===== Round 5 =====
            // Initial Deck (bottom to top): Priest, Countess, Priest, Handmaiden, Prince, Handmaiden, Princess, Guard, Handmaiden, Prince, Baron, King, Guard, Baron, Guard, Guard
            // Deal Cards, Discarded Card: Guard, p4: Guard, p1: Baron, p2: Guard, p3: King
            // Deck: Priest, Countess, Priest, Handmaiden, Prince, Handmaiden, Princess, Guard, Handmaiden, Prince, Baron
            // p4's turn, draws Baron, plays Guard, selects p1 and guesses Prince, wrong guess
            "0", "p1", "Prince",
            // p1's turn, draws Prince, plays Baron, selects p4, p4 is eliminated
            "0", "p4",
            // p2's turn, draws Handmaiden, plays Guard, selects p1 and guesses Princess, wrong guess
            "0", "p1", "Princess",
            // p3's turn, draws Guard, plays Guard, selects p2 and guesses Handmaiden, p2 is eliminated
            "1", "p2", "Handmaiden",
            // Turn loop ended with cards p1: Prince, p3: King
            // p1's turn, draws Princess, plays Plays Prince, selects p3, p3 draws Prince
            "0", "p3",
            // p3's turn, draws Handmaiden, plays Prince, selects p1, p1 is eliminated
            "1", "p1",
            // p3 wins the round

            // ===== Round 6 =====
            // Initial Deck (bottom to top): Prince, Priest, Guard, Priest, Baron, Baron, Guard, Prince, Guard, King, Princess, Guard, Guard, Handmaiden, Handmaiden, Countess
            // Deal Cards, Discarded Card: Countess, p3: Handmaiden, p4: Handmaiden, p1: Guard, p2: Guard
            // Deck: Prince, Priest, Guard, Priest, Baron, Baron, Guard, Prince, Guard, King, Princess
            // p3’s turn, draws Princess, plays Handmaiden
            "0",
            // p4’s turn, draws King, plays Handmaiden
            "0",
            // p1’s turn, draws Guard, plays Guard, selects p2, guesses Baron, wrong guess
            "1", "p2", "Baron",
            // p2’s turn, draws Prince, plays Prince on themself, discards Guard, draws Guard
            "1", "p2",
            // Turn Loop Ended with cards p3: Princess, p4: King, p1: Guard, p2: Guard
            // Deck: Prince, Priest, Guard, Priest, Baron, Baron
            // p3’s turn, draws Baron, plays Baron, selects p1, p1 is eliminated
            "1", "p1",
            // p4’s turn, draws Baron, plays Baron, selects p3, p4 is eliminated
            "1", "p3",
            // p2’s turn, draws Priest, plays Guard, selects p3 and guesses Princess, p3 is eliminated
            "0", "p3", "Princess",
            // p2 wins the round
            
            // ===== Round 7 =====
            // Initial Deck (bottom to top): Prince, Princess, Baron, Guard, Handmaiden, Guard, King, Countess, Guard, Guard, Handmaiden, Baron, Priest, Guard, Priest, Prince
            // Deal Cards, Discarded Card: Prince, p2: Priest, p3: Guard, p4: Priest, p1: Baron
            // Deck: Prince, Princess, Baron, Guard, Handmaiden, Guard, King, Countess, Guard, Guard, Handmaiden
            // p2's turn, draws Handmaiden, plays Priest, selects p1 (Baron is revealed)
            "0", "p1",
            // p3's turn, draws Guard, plays Guard, selects p4 and guesses Baron, wrong guess
            "1", "p4", "Baron",
            // p4's turn, draws Guard, plays Priest, selects p3 (Guard is revealed)
            "0", "p3",
            // p1's turn, draws Countess, plays Baron, selects p4, p4 is eliminated
            "0", "p4",
            // Turn Loop Ended With p2: Handmaiden, p3: Guard, p1: Countess
            // p2's turn, draws King, plays Handmaiden
            "0",
            // p3's turn, draws Guard, plays Guard, selects p1 and guesses Countess, p1 is eliminated
            "1", "p1", "Countess",
            // Turn Loop Ended With p2: King, p3: Guard
            // p2's turn, draws Handmaiden, plays Handmaiden
            "1",
            // p3's turn, draws Guard, plays Guard, no target
            "1",
            // Turn Loop Ended With p2: King, p3: Guard
            // p2's turn, draws Baron, plays Baron, selects p3, p3 is eliminated
            "1", "p3"
            // p2 wins the round
        );

        // Act
        mockGame.setPlayers();
        mockGame.gameLoop();

        // Assert
        Mockito.verify(mockPrintStream).println("p2 (4 tokens) has won the game and the heart of the princess!");
        Assertions.assertEquals(0, mockPlayerList.getPlayer("p1").getTokens());
        Assertions.assertEquals(4, mockPlayerList.getPlayer("p2").getTokens());
        Assertions.assertEquals(1, mockPlayerList.getPlayer("p3").getTokens());
        Assertions.assertEquals(2, mockPlayerList.getPlayer("p4").getTokens());

    }
}
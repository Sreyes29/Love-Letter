package edu.cmu.f24qa.loveletter;

import java.util.ArrayList;
import java.util.List;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;

import edu.cmu.f24qa.loveletter.cardactions.SycophantAction;

@SuppressWarnings("allcheckers")
public class BlackboxCardLogicTest {
    public Game mockGame;
    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    public Deck mockDeck;

    List<Card> cardsTargetNonself = List.of(Card.GUARD, Card.PRIEST, Card.BARON, Card.KING, Card.QUEEN);
    List<Card> cardsTargetAnyone = List.of(Card.GUARD, Card.PRIEST, Card.BARON, Card.KING, Card.QUEEN, Card.PRINCE, Card.SYCOPHANT);
    
    public BlackboxCardLogicTest() {
        this.mockDeck = Mockito.mock(Deck.class);
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPlayerList = Mockito.mock(PlayerList.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
        this.mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
    }

    @BeforeEach
    public void setup() {
        this.mockDeck = Mockito.mock(Deck.class);
        this.mockScanner = Mockito.mock(Scanner.class);
        this.mockPlayerList = Mockito.mock(PlayerList.class);
        this.mockPrintStream = Mockito.mock(PrintStream.class);
        this.mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));
    }

    /**************************************************************************
     * SHARED TESTS
     *************************************************************************/

    /**
     * Test the Cards playCard method that has opponent and can't target self.
     * 
     * Type of Test: Black Box
     * Input: Target is Self
     * Expected Output: Invalid target
     */
    @Test
    public void testTargetSelf() {
        for (Card cardIteration : this.cardsTargetNonself) {
            // Create mocks
            Player opponent = Mockito.spy(new Player("opponent"));
            opponent.addCardToHand(Card.GUARD);
            Player user = Mockito.spy(new Player("user"));
            user.addCardToHand(Card.BARON);
            Card mockCard = Mockito.spy(cardIteration);

            // Define behavior of mocks
            Mockito.when(mockScanner.nextLine()).thenReturn("user", "opponent");
            Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
            Mockito.when(mockPlayerList.getPlayer("user")).thenReturn(user);
            Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

            // Act
            mockGame.playCard(mockCard, user);

            // Verify that it is not possible to target oneself
            Mockito.verify(mockCard, Mockito.times(1)).executeAction(mockGame, user);
            Mockito.verify(mockPrintStream, Mockito.times(1)).println("Invalid target");

            // Reset the mock interactions to avoid interference
            Mockito.reset(user, mockPrintStream, mockScanner, mockPlayerList);
        }
    }

    /**
     * Test the Cards playCard method that has opponent.
     * 
     * Type of Test: Black Box
     * Input: Target is Non-Existent Player
     * Expected Output: Invalid target
     */
    @Test
    public void testTargetNonExistentPlayer() {
        for (Card cardIteration : this.cardsTargetAnyone) {
            // Create mocks
            this.mockDeck = Mockito.mock(Deck.class);
            this.mockScanner = Mockito.mock(Scanner.class);
            this.mockPlayerList = Mockito.mock(PlayerList.class);
            this.mockPrintStream = Mockito.mock(PrintStream.class);
            this.mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));

            Player opponent = Mockito.spy(new Player("opponent"));
            opponent.addCardToHand(Card.GUARD);
            Player user = Mockito.spy(new Player("user"));
            user.addCardToHand(Card.BARON);
            Card mockCard = Mockito.spy(cardIteration);

            // Define behavior of mocks
            Mockito.when(mockScanner.nextLine()).thenReturn("NonExistentPlayer", "opponent");
            Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
            Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);
            
            // Act
            mockGame.playCard(mockCard, user);

            // Verify that it is not possible to target a non-existent player
            Mockito.verify(mockCard, Mockito.times(1)).executeAction(mockGame, user);
            Mockito.verify(mockPrintStream, Mockito.times(1)).println("Invalid target");

            // Reset the mock interactions to avoid interference
            Mockito.reset(user, mockPrintStream, mockScanner, mockPlayerList);
        }
    }

    /**
     * Test the Cards playCard method that has opponent.
     * 
     * Type of Test: Black Box
     * Input: Target is Protected
     * Expected Output: Invalid target
     */
    @Test
    public void testTargetProtected() {
        for (Card cardIteration : this.cardsTargetAnyone) {
            // Create mocks
            this.mockDeck = Mockito.mock(Deck.class);
            this.mockScanner = Mockito.mock(Scanner.class);
            this.mockPlayerList = Mockito.mock(PlayerList.class);
            this.mockPrintStream = Mockito.mock(PrintStream.class);
            this.mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));

            Player opponent = Mockito.spy(new Player("opponent"));
            opponent.addCardToHand(Card.GUARD);
            Player user = Mockito.spy(new Player("user"));
            user.addCardToHand(Card.BARON);
            // Opponent is protected by a handmaid
            Player protectedOpponent = Mockito.spy(new Player("protectedOpponent"));
            protectedOpponent.switchProtection();
            protectedOpponent.addCardToHand(Card.GUARD);
            Card mockCard = Mockito.spy(cardIteration);

            // Define behavior of mocks
            Mockito.when(mockScanner.nextLine()).thenReturn("protectedOpponent", "opponent");
            Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
            Mockito.when(mockPlayerList.getPlayer("protectedOpponent")).thenReturn(protectedOpponent);
            Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

            // Act
            mockGame.playCard(mockCard, user);

            // Verify that it is not possible to target a protected player
            Mockito.verify(mockCard, Mockito.times(1)).executeAction(mockGame, user);
            Mockito.verify(mockPrintStream, Mockito.times(1)).println("Invalid target");

            // Reset the mock interactions to avoid interference
            Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
        }
    }

    /**
     * Test the Cards playCard method that has opponent.
     * 
     * Type of Test: Black Box
     * Input: Target is dead
     * Expected Output: Invalid target
     */
    @Test
    public void testTargetDead() {
        for (Card cardIteration : this.cardsTargetAnyone) {
            // Create mocks
            // sycophant messes everything up, so must reset mocks here
            this.mockDeck = Mockito.mock(Deck.class);
            this.mockScanner = Mockito.mock(Scanner.class);
            this.mockPlayerList = Mockito.mock(PlayerList.class);
            this.mockPrintStream = Mockito.mock(PrintStream.class);
            this.mockGame = Mockito.spy(new Game(mockPlayerList, mockDeck, mockScanner, mockPrintStream));

            Player opponent = Mockito.spy(new Player("opponent"));
            opponent.addCardToHand(Card.GUARD);
            Player user = Mockito.spy(new Player("user"));
            user.addCardToHand(Card.BARON);
            // Opponent is dead
            Player deadOpponent = Mockito.spy(new Player("deadOpponent"));
            deadOpponent.addCardToHand(Card.GUARD);
            deadOpponent.eliminate();
            Card mockCard = Mockito.spy(cardIteration);

            // Define behavior of mocks
            Mockito.when(mockScanner.nextLine()).thenReturn("deadOpponent", "opponent");
            Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
            Mockito.when(mockPlayerList.getPlayer("deadOpponent")).thenReturn(deadOpponent);
            Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

            // Act
            mockGame.playCard(mockCard, user);

            // Verify that it is not possible to target a dead player
            Mockito.verify(mockCard, Mockito.times(1)).executeAction(mockGame, user);
            Mockito.verify(mockPrintStream, Mockito.times(1)).println("Invalid target");

            // Reset the mock interactions to avoid interference
            // doesn't seem to actually work at the moment, mocks aren't properly reset
            Mockito.reset(user, opponent, mockGame, mockPrintStream, mockScanner, mockPlayerList);
        }
    }

    /**
     * Test the Cards playCard method that has opponent.
     * 
     * Type of Test: Black Box
     * Input: No target is available
     * Expected Output: Execute is not called
     */
    @Test
    @SuppressWarnings("allcheckers")
    public void testNoTarget() {
        for (Card cardIteration : this.cardsTargetNonself) {
            // Create mocks
            Card mockCard = Mockito.spy(cardIteration);
            PlayerList mockPlayerList = Mockito.spy(new PlayerList(mockPrintStream));

            // Populate the player list with the user
            mockPlayerList.addPlayer("user");
            mockPlayerList.addPlayer("protectedOpponent");
            mockPlayerList.addPlayer("deadOpponent");
            Player user = mockPlayerList.getPlayer("user");
            Player protectedOpponent = mockPlayerList.getPlayer("protectedOpponent");
            Player deadOpponent = mockPlayerList.getPlayer("deadOpponent");
            // Give all users card
            user.addCardToHand(Card.GUARD);
            protectedOpponent.addCardToHand(Card.GUARD);
            deadOpponent.addCardToHand(Card.GUARD);
            // Protect the protected opponent and eliminate the dead opponent
            protectedOpponent.switchProtection();
            deadOpponent.eliminate();

            // Define behavior of mocks
            Mockito.doNothing().when(mockCard).executeAction(mockGame, user);

            // Act
            mockGame.playCard(mockCard, user);

            // Verify that the execute method is not called
            Assertions.assertFalse(mockPlayerList.checkAvailablePlayers(user));
            Mockito.verify(mockCard, Mockito.times(0)).executeAction(mockGame, user);
        }
    }

    /**
     * Test the Cards playCard method that has opponent.
     * 
     * Type of Test: Black Box
     * Input: Target is available
     * Expected Output: Execute is called
     */
    @Test
    public void testPlayCardDiscardsPlayedCard() {
        Player user = new Player("user");
        // create mocks
        Card mockCard = Mockito.spy(Card.GUARD);

        // define behavior of mocks
        Mockito.doNothing().when(mockCard).executeAction(mockGame, user);

        // act
        mockGame.playCard(mockCard, user);

        // verify
        DiscardPile discardPile = user.getDiscarded();
        int top_val = discardPile.value();
        Assertions.assertTrue(top_val == mockCard.getValue());
    }

    /**************************************************************************
     * GUARD TESTS
     *************************************************************************/

    
    /**
     * Tests the Guard playCard method.
     * 
     * Type of test: Black Box
     * Input: user guesses correctly
     * Expected output: opponent is eliminated
     */
    @Test
    void testGuardGuessCorrect() {
        for (Card actualCard : Card.values()) {
            // Can't guess Guard
            if (actualCard == Card.GUARD || actualCard == Card.ASSASSIN) {
                continue;
            }

            // Arrange
            Player mockUser = Mockito.spy(new Player("user"));
            Player mockOpponent = Mockito.spy(new Player("opponent"));

            Mockito.when(mockScanner.nextLine()).thenReturn("opponent", actualCard.getName());
            Mockito.when(mockPlayerList.checkAvailablePlayers(mockUser)).thenReturn(true);
            Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(mockOpponent);

            // Act
            mockUser.addCardToHand(Card.GUARD);
            mockOpponent.addCardToHand(actualCard);
            mockGame.playCard(Card.GUARD, mockUser);

            // Assert
            Assertions.assertFalse(mockOpponent.hasCardsInHand());

            Mockito.reset(mockUser, this.mockPrintStream, this.mockScanner, this.mockPlayerList);
        }
    }

    /**
     * Tests the Guard playCard method.
     * 
     * Type of test: Black Box
     * Input: user guesses incorrectly
     * Expected output: opponent is not eliminated
     */
    @Test
    void testGuardGuessInCorrect() {
        for (Card actualCard : Card.values()) {
            for (Card guessCard : Card.values()) {
                // Can't guess Guard
                if (guessCard == Card.GUARD || actualCard == guessCard) {
                    continue;
                }
                
                // Arrange
                Player mockUser = Mockito.spy(new Player("user"));
                Player mockOpponent = Mockito.spy(new Player("opponent"));

                Mockito.when(mockScanner.nextLine()).thenReturn("opponent", guessCard.getName());
                Mockito.when(mockPlayerList.getPlayer("user")).thenReturn(mockUser);
                Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(mockOpponent);

                // Act
                mockUser.addCardToHand(Card.GUARD);
                mockOpponent.addCardToHand(actualCard);
                mockGame.playCard(Card.GUARD, mockUser);

                // Assert
                Assertions.assertTrue(mockOpponent.hasCardsInHand());
                Mockito.reset(mockUser, this.mockPrintStream, this.mockScanner, this.mockPlayerList);
            }
        }
    }

    /**
     * Tests the Guard playCard method.
     * 
     * Type of test: Black Box
     * Input: user guesses Guard
     * Expected output: user cannot guess Guard
     */
    @Test
    void testGuardGuessGuard() {
        // Arrange
        Player mockUser = Mockito.spy(new Player("user"));
        Player mockOpponent = Mockito.spy(new Player("opponent"));

        Mockito.when(mockScanner.nextLine()).thenReturn("opponent", "Guard", "Princess");
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(mockOpponent);
        Mockito.when(mockPlayerList.checkAvailablePlayers(mockUser)).thenReturn(true);

        // Act
        mockUser.addCardToHand(Card.GUARD);
        mockOpponent.addCardToHand(Card.GUARD);
        mockGame.playCard(Card.GUARD, mockUser);

        // Assert
        Mockito.verify(this.mockPrintStream, Mockito.times(1)).println("You cannot guess this card");
        Mockito.reset(this.mockPrintStream, this.mockScanner, this.mockPlayerList);
    }

    /**
     * Tests the Guard playCard method.
     * 
     * Type of test: Black Box
     * Input: Opponent has Assassin, deck has cards
     * Expected output: User is eliminated, opponent draws a new card from the deck
     */
    @Test
    void testGuardWithAssassinAndNonEmptyDeck() {
        // Set up the game with a real deck
        Deck deck = new Deck();
        mockScanner = Mockito.mock(Scanner.class);
        mockPlayerList = Mockito.mock(PlayerList.class);
        mockPrintStream = Mockito.mock(PrintStream.class);
        mockGame = Mockito.spy(new Game(mockPlayerList, deck, mockScanner, mockPrintStream));
        mockGame.setDeck();

        // Arrange
        Player mockUser = Mockito.spy(new Player("user"));
        Player mockOpponent = Mockito.spy(new Player("opponent"));
        int deckSize = deck.getCards().size();
        List<Card> deckCards = deck.getCards();
        Card topCard = deckCards.get(deckSize - 1);

        Mockito.when(mockScanner.nextLine()).thenReturn("opponent", "Guard", "Princess");
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(mockOpponent);
        Mockito.when(mockPlayerList.checkAvailablePlayers(mockUser)).thenReturn(true);

        // Act
        mockUser.addCardToHand(Card.GUARD);
        mockOpponent.addCardToHand(Card.ASSASSIN);
        mockGame.playCard(Card.GUARD, mockUser);

        // Verify user is eliminated
        Mockito.verify(this.mockPrintStream, Mockito.times(1)).println("The opponent has an assassin. You have been eliminated.");
        Mockito.verify(mockUser, Mockito.times(1)).eliminate();
        // Assert that the opponent draws a new card from the top of deck
        Assertions.assertEquals(deckSize - 1, deck.getCards().size());
        Assertions.assertEquals(topCard, mockOpponent.checkTopCard(0));

    }

    /**
     * Tests the Guard playCard method.
     * 
     * Type of test: Black Box
     * Input: Opponent has Assassin, deck is empty
     * Expected output: User is eliminated, opponent draws a new card from the removed pile
     */
    @Test
    void testGuardWithAssassinAndEmptyDeck() {
        // Set up the game with an empty deck and removed cards
        Deck deck = new Deck();
        List<Card> removedCards = new ArrayList<>(Arrays.asList(Card.BARON, Card.PRIEST));
        mockScanner = Mockito.mock(Scanner.class);
        mockPlayerList = Mockito.mock(PlayerList.class);
        mockPrintStream = Mockito.mock(PrintStream.class);
        mockGame = Mockito.spy(new Game(mockPlayerList, deck, mockScanner, mockPrintStream, removedCards));

        // Arrange
        Player mockUser = Mockito.spy(new Player("user"));
        Player mockOpponent = Mockito.spy(new Player("opponent"));
        
        int removedSize = removedCards.size();
        Card topCard = removedCards.get(0);

        Mockito.when(mockScanner.nextLine()).thenReturn("opponent", "Guard", "Princess");
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(mockOpponent);
        Mockito.when(mockPlayerList.checkAvailablePlayers(mockUser)).thenReturn(true);

        // Act
        mockUser.addCardToHand(Card.GUARD);
        mockOpponent.addCardToHand(Card.ASSASSIN);
        mockGame.playCard(Card.GUARD, mockUser);

        // Verify user is eliminated
        Mockito.verify(this.mockPrintStream, Mockito.times(1)).println("The opponent has an assassin. You have been eliminated.");
        Mockito.verify(mockUser, Mockito.times(1)).eliminate();
        // Assert that the opponent draws a new card from the removed pile
        Assertions.assertEquals(removedSize - 1, mockGame.removed.size());
        Assertions.assertEquals(topCard, mockOpponent.checkTopCard(0));

    }

    /**************************************************************************
     * PRIEST TESTS
     *************************************************************************/

    /**
     * Tests the Priest playCard method.
     * 
     * Type of test: Black Box
     * Input: opponent has any card
     * Expected output: user sees opponent's card
     */
    @Test
    public void testPriestWhenOpponentHasAnyCard() {
        for (Card opponentCard : Card.values()) {
            // Adding card to opponent's hand
            Player opponent = Mockito.spy(new Player("opponent"));
            opponent.addCardToHand(opponentCard);
            Player user = new Player("user");

            // Define behavior of mocks
            Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
            Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
            Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
            Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

            // Act
            mockGame.playCard(Card.PRIEST, user);

            // Verify that the user sees opponent's card
            verify(mockPrintStream).println("opponent shows you a " + opponentCard);
        }
    }

    /**************************************************************************
     * BARON TESTS
     *************************************************************************/

    /**
     * Test the Baron playCard method.
     * 
     * Type of Test: Black Box
     * Input: Opponent has a higher value card than user
     * Expected Output: User is eliminated, opponent is not eliminated
     */
    @Test
    public void testBaronUserLessThanOpponent() {
        for (Card opponentCard : Card.values()) {
            for (Card userCard : Card.values()) {
                if (opponentCard.getValue() > userCard.getValue()) {
                    // Create mocks
                    Player opponent = Mockito.spy(new Player("opponent"));
                    opponent.addCardToHand(opponentCard);
                    Player user = Mockito.spy(new Player("user"));
                    user.addCardToHand(userCard);
                    Card mockCard = Mockito.spy(Card.BARON);

                    // Define behavior of mocks
                    Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
                    Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
                    Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
                    Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

                    // Act
                    mockGame.playCard(mockCard, user);

                    // Verify that the user is eliminated and the opponent is not
                    Mockito.verify(mockCard, Mockito.times(1)).executeAction(mockGame, user);
                    Mockito.verify(user, Mockito.times(1)).eliminate();
                    Mockito.verify(opponent, Mockito.times(0)).eliminate();
                    Assertions.assertFalse(user.hasCardsInHand());
                    Assertions.assertTrue(opponent.hasCardsInHand());

                    // Reset the mock interactions to avoid interference
                    Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
                }
            }
        }
    }

    /**
     * Test the Baron playCard method.
     * 
     * Type of Test: Black Box
     * Input: Opponent and user have the same value card
     * Expected Output: Both the user and the opponent are not eliminated
     */
    @Test
    public void testBaronUserEqual() {
        for (Card cardIteration : Card.values()) {
            if (cardIteration == Card.BARON || cardIteration == Card.KING || cardIteration == Card.COUNTESS || cardIteration == Card.PRINCESS) {
                continue;
            }
            // Create mocks
            Player opponent = Mockito.spy(new Player("opponent"));
            opponent.addCardToHand(cardIteration);
            Player user = Mockito.spy(new Player("user"));
            user.addCardToHand(cardIteration);

            // Define behavior of mocks
            Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
            Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
            Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
            Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

            // Act
            mockGame.playCard(Card.BARON, user);

            // Verify that both the user and the opponent is not eliminated
            Mockito.verify(user, Mockito.times(0)).eliminate();
            Mockito.verify(opponent, Mockito.times(0)).eliminate();
            Assertions.assertTrue(user.hasCardsInHand());
            Assertions.assertTrue(opponent.hasCardsInHand());

            // Reset the mock interactions to avoid interference
            Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
        }
    }

    /**
     * Test the Baron playCard method.
     * 
     * Type of Test: Black Box
     * Input: Both opponent and user have a special card
     * Expected Output: This should not happen, game is corrupted
     */
    @Test
    public void testBaronUserEqualSpecial() {
        for (Card cardIteration : Card.values()) {
            if (cardIteration == Card.BARON || cardIteration == Card.KING || cardIteration == Card.COUNTESS || cardIteration == Card.PRINCESS) {
                // Create mocks
                Player opponent = Mockito.spy(new Player("opponent"));
                opponent.addCardToHand(cardIteration);
                Player user = Mockito.spy(new Player("user"));
                user.addCardToHand(cardIteration);
                Card mockCard = Mockito.spy(Card.BARON);

                // Define behavior of mocks
                Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
                Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
                Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
                Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

                // Act
                mockGame.playCard(mockCard, user);

                // Verify that this game state is not possible
                Mockito.verify(mockCard, Mockito.times(1)).executeAction(mockGame, user);
                Mockito.verify(mockPrintStream, Mockito.times(1)).println("Game State is Corrupted. Please restart the game.");

                // Reset the mock interactions to avoid interference
                Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
            }
        }
    }

    /**
     * Test the Baron playCard method.
     * 
     * Type of Test: Black Box
     * Input: User has a higher value card than opponent
     * Expected Output: User is not eliminated, opponent is eliminated
     */
    @Test
    public void testBaronUserGreaterThanOpponent() {
        for (Card opponentCard : Card.values()) {
            for (Card userCard : Card.values()) {
                if (opponentCard.getValue() < userCard.getValue()) {
                    // Create mocks
                    Player opponent = Mockito.spy(new Player("opponent"));
                    opponent.addCardToHand(opponentCard);
                    Player user = Mockito.spy(new Player("user"));
                    user.addCardToHand(userCard);
                    Card mockCard = Mockito.spy(Card.BARON);

                    // Define behavior of mocks
                    Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
                    Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
                    Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
                    Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

                    // Act
                    mockGame.playCard(mockCard, user);

                    // Verify that the user is eliminated and the opponent is not
                    Mockito.verify(mockCard, Mockito.times(1)).executeAction(mockGame, user);
                    Mockito.verify(user, Mockito.times(0)).eliminate();
                    Mockito.verify(opponent, Mockito.times(1)).eliminate();
                    Assertions.assertTrue(user.hasCardsInHand());
                    Assertions.assertFalse(opponent.hasCardsInHand());

                    // Reset the mock interactions to avoid interference
                    Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
                }
            }
        }
    }

    /**************************************************************************
     * HANDMAIDEN TESTS
     *************************************************************************/

    /**
     * Tests the Handmaiden playCard method.
     * 
     * Type of test: Black Box
     * Input: user is not protected
     * Expected output: user is protected
     */
    @Test
    public void testHandmaidenProtectsSelf() {
        // define behavior of mocks
        Mockito.doNothing().when(mockPrintStream).println(Mockito.anyString());

        // act
        Player user = new Player("user");
        mockGame.playCard(Card.HANDMAIDEN, user);

        // verify
        Assertions.assertTrue(user.isProtected());
    }

    /**
     * Tests the Handmaiden playCard method with protected user.
     * 
     * Type of test: Black Box
     * Input: user is protected
     * Expected output: user is protected
     */
    @Test
    public void testHandmaidenProtectsSelfOriginallyProtected() {
        // define behavior of mocks
        Mockito.doNothing().when(mockPrintStream).println(Mockito.anyString());

        // act
        Player user = new Player("user");
        user.switchProtection();
        mockGame.playCard(Card.HANDMAIDEN, user);

        // verify
        Assertions.assertTrue(user.isProtected());
    }
    
    /**************************************************************************
     * PRINCE TESTS
     *************************************************************************/

    /**
     * Tests the Prince playCard method.
     * 
     * Type of test: Black Box
     * Input: opponent has a princess card
     * Expected output: opponent is eliminated
     */
    @Test
    public void testPrinceWhenOpponentIsPrincess() {
        // Opponent has a princess card as the top of their hand
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.PRINCESS);

        // Define behavior of mocks
        Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
        Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

        // Act
        Player user = new Player("user");
        user.addCardToHand(Card.BARON);  // User has non-countess card
        mockGame.playCard(Card.PRINCE, user);

        // Verify that the opponent is eliminated
        Mockito.verify(opponent, Mockito.times(1)).eliminate();
        Assertions.assertFalse(opponent.hasCardsInHand());
    }

    /**
     * Tests the Prince playCard method.
     * 
     * Type of test: Black Box
     * Input: opponent does not have a princess card
     * Expected output: opponent draws a card and is not eliminated
     */
    @Test
    public void testPrinceWhenOpponentIsNotPrincess() {
        // Opponent does not have a princess card in their hand
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.BARON);

        // Define behavior of mocks
        Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
        Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

        // Act
        Player user = new Player("user");
        user.addCardToHand(Card.BARON);  // User has non-countess card
        mockGame.playCard(Card.PRINCE, user);

        // Verify that a new card is drawn from the deck
        Mockito.verify(opponent, Mockito.times(1)).eliminate();
        Mockito.verify(mockDeck, Mockito.times(1)).draw();
    }

    /**************************************************************************
     * KING TESTS
     *************************************************************************/

    /**
     * Tests the King playCard method.
     * 
     * Type of test: Black Box
     * Input: user plays King, opponent has a card
     * Expected output: user and opponent swap hands
     */
    @Test
    void testKingSwappedHands() {
        for (Card opponentCard : Card.values()) {
            for (Card userCard : Card.values()) {
                if  (opponentCard != userCard && userCard != Card.COUNTESS) {
                    // Create mocks
                    Player opponent = Mockito.spy(new Player("opponent"));
                    opponent.addCardToHand(opponentCard);
                    Player user = Mockito.spy(new Player("user"));
                    user.addCardToHand(userCard);

                    // Define behavior of mocks
                    Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
                    Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
                    Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

                    // Act
                    mockGame.playCard(Card.KING, user);

                    // Verify that the hands are swapped
                    Mockito.verify(opponent, Mockito.times(1)).addCardToHand(userCard);
                    Mockito.verify(user, Mockito.times(1)).addCardToHand(opponentCard);
                    
                    // Reset the mock interactions to avoid interference
                    Mockito.reset(user, mockPrintStream, mockScanner, mockPlayerList);
                }
            }
        }
    }

    /**************************************************************************
     * COUNTESS TESTS
     *************************************************************************/

    /**
     * Tests the Countess playCard method.
     * 
     * Type of test: Black Box
     * Input: user has Countess, user played Prince
     * Expected output: Prince is not played, Countess is played
     */
    @Test
    public void testCountessPlayPrince() {
        Player user = new Player("user");

        // create mocks
        Card mockPrince = Mockito.spy(Card.PRINCE);
        user.addCardToHand(Card.COUNTESS);

        // define behavior of mocks
        Mockito.doNothing().when(mockPrince).executeAction(mockGame, user);
        Mockito.doNothing().when(mockGame).playCard(Card.COUNTESS, user);

        // act
        mockGame.playCard(mockPrince, user);

        // verify
        Mockito.verify(mockPrince, Mockito.times(0)).executeAction(mockGame, user);
        Mockito.verify(mockGame, Mockito.times(1)).playCard(Card.COUNTESS, user);
        Assertions.assertTrue(user.checkTopCard(0) == mockPrince);
    }

    /**
     * Tests the Countess playCard method.
     * 
     * Type of test: Black Box
     * Input: user has Countess, user played King
     * Expected output: King is not played, Countess is played
     */
    @Test
    public void testCountessPlayKing() {
        Player user = new Player("user");

        // create mocks
        Card mockKing = Mockito.spy(Card.KING);
        user.addCardToHand(Card.COUNTESS);

        // define behavior of mocks
        Mockito.doNothing().when(mockKing).executeAction(mockGame, user);
        Mockito.doNothing().when(mockGame).playCard(Card.COUNTESS, user);

        // act
        mockGame.playCard(mockKing, user);

        // verify
        Mockito.verify(mockKing, Mockito.times(0)).executeAction(mockGame, user);
        Mockito.verify(mockGame, Mockito.times(1)).playCard(Card.COUNTESS, user);
        Assertions.assertTrue(user.checkTopCard(0) == mockKing);
    }

    /**
     * Tests the Countess playCard method.
     * 
     * Type of test: Black Box
     * Input: user has Countess, user played non-King/Prince card
     * Expected output: non-King/Prince card effect is played
     */
    @Test
    public void testCountessPlayOtherCard() {
        Player user = new Player("user");
        // create mocks
        user.addCardToHand(Card.COUNTESS);

        for (Card card : Card.values()) {
            if (card != Card.COUNTESS && card != Card.PRINCE && card != Card.KING) {
                Card mockCard = Mockito.spy(card);

                // define behavior of mocks
                Mockito.doNothing().when(mockCard).executeAction(mockGame, user);
                Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);

                // act
                mockGame.playCard(mockCard, user);

                // verify
                Mockito.verify(mockCard, Mockito.times(1)).executeAction(mockGame, user);
                Assertions.assertTrue(user.checkTopCard(0) == Card.COUNTESS);
            }
        }
    }

    /**
     * Tests the Countess playCard method.
     * 
     * Type of test: Black Box
     * Input: user plays Countess
     * Expected output: n/a
     */
    @Test
    public void playCountess() {
        // create mocks
        Card mockCountess = Mockito.spy(Card.COUNTESS);
        
        // define behavior of mocks
        // none

        // act
        Player user = new Player("user");
        mockGame.playCard(mockCountess, user);

        // verify
        Mockito.verify(mockCountess).executeAction(mockGame, user);

    }

    /**************************************************************************
     * PRINCESS TESTS
     *************************************************************************/

    @Test
    public void testPrincessEliminateSelf() {
        // act
        Player user = new Player("user");
        user.addCardToHand(Card.PRINCESS);
        mockGame.playCard(Card.PRINCESS, user);

        // verify
        Assertions.assertFalse(user.hasCardsInHand());
    }

    /**************************************************************************
     * BARONESS TESTS
     *************************************************************************/

    /**
     * Tests the Baroness playCard method.
     * 
     * Type of test: Black Box
     * Input: user plays Baroness, chooses 1 opponent
     * Expected output: user sees opponent's card
     */
    @Test
    public void testBaronessSee1OpponentCard() {
        for (Card opponentCard : Card.values()) {
            // Adding card to opponent's hand
            Player opponent = Mockito.spy(new Player("opponent"));
            opponent.addCardToHand(opponentCard);
            Player user = new Player("user");

            // Define behavior of mocks
            Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
            Mockito.when(mockScanner.nextLine()).thenReturn("opponent", "invalid", "");
            Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
            Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);
            Mockito.when(mockPlayerList.getPlayer("invalid")).thenReturn(null);

            // Act
            mockGame.playCard(Card.BARONESS, user);

            // Verify that the user sees opponent's card
            verify(mockPrintStream).println("opponent shows you a " + opponentCard);
        }
    }

    /**
     * Tests the Baroness playCard method.
     * 
     * Type of test: Black Box
     * Input: user plays Baroness, chooses 2 opponents
     * Expected output: user sees both opponent's card
     */
    @Test
    public void testBaronessSee2OpponentCard() {
        for (Card opponentCard : Card.values()) {
            for (Card opponentCard2 : Card.values()) {
                // Adding card to opponent's hand
                Player opponent = Mockito.spy(new Player("opponent"));
                opponent.addCardToHand(opponentCard);
                Player opponent2 = Mockito.spy(new Player("opponent2"));
                opponent2.addCardToHand(opponentCard2);
                Player user = new Player("user");

                // Define behavior of mocks
                // Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
                Mockito.when(mockScanner.nextLine()).thenReturn("opponent", "opponent", "opponent2");
                Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
                Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);
                Mockito.when(mockPlayerList.getPlayer("opponent2")).thenReturn(opponent2);

                // Act
                mockGame.playCard(Card.BARONESS, user);

                // Verify that the user sees both opponent's card
                verify(mockPrintStream).println("opponent shows you a " + opponentCard);
                verify(mockPrintStream).println("opponent2 shows you a " + opponentCard2);

                // Reset the mock interactions to avoid interference
                Mockito.reset(mockPrintStream, mockScanner, mockPlayerList);
            }
        }
    }

    /**************************************************************************
     * SYCOPHANT TESTS
     *************************************************************************/

    /**
     * Tests the Sycophant playCard method.
     * 
     * Type of test: Black Box
     * Input: User plays Sycophant
     * Expected output: opponent is targeted next turn
     */
    @Test
    public void testSycophantChoosingOpponent() {
        // create mocks
        Scanner mockScanner = Mockito.mock(Scanner.class);
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);
        PlayerList mockPlayerList = Mockito.mock(PlayerList.class);
        Player mockUser = Mockito.mock(Player.class);
        Game game = Mockito.spy(Game.class);
        SycophantAction sycophantAction = Mockito.spy(new SycophantAction());
        Player mockOpponent = Mockito.mock(Player.class);
        
        // define behavior of mocks
        Mockito.when(game.getScanner()).thenReturn(mockScanner);
        Mockito.when(game.getPlayers()).thenReturn(mockPlayerList);
        Mockito.when(game.getPrinter()).thenReturn(mockPrintStream);
        Mockito.doReturn(mockOpponent).when(sycophantAction).getOpponent(game, mockUser, 1);

        // act                                                
        sycophantAction.execute(game, mockUser);

        // verify
        Mockito.verify(game, Mockito.times(1)).setSycophantTarget(mockOpponent);
        Assertions.assertEquals(mockOpponent, game.getSycophantTarget());
    }

    /**************************************************************************
     * CARDINAL TESTS
     *************************************************************************/

    /**
     * Tests the Cardinal playCard method.
     * 
     * Type of test: Black Box
     * Input: user plays Cardinal, picks 2 opponents that have a card
     * Expected output: 2 opponents swap hands
     */
    @Test
    void testCardinalSwappedHands() {
        for (Card opponent1Card : Card.values()) {
            for (Card opponent2Card : Card.values()) {
                if (opponent1Card == opponent2Card) {
                    continue;
                }
                // Create mocks
                Player user = Mockito.spy(new Player("user"));
                Player opponent1 = Mockito.spy(new Player("opponent1"));
                opponent1.addCardToHand(opponent1Card);
                Player opponent2 = Mockito.spy(new Player("opponent2"));
                opponent2.addCardToHand(opponent2Card);

                // Define behavior of mocks
                Mockito.when(mockScanner.nextLine()).thenReturn("opponent1", "opponent2");
                Mockito.when(mockScanner.nextInt()).thenReturn(1);
                Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
                Mockito.when(mockPlayerList.getPlayer("opponent1")).thenReturn(opponent1);
                Mockito.when(mockPlayerList.getPlayer("opponent2")).thenReturn(opponent2);

                // Act
                mockGame.playCard(Card.CARDINAL, user);

                // Verify that the hands are swappedw
                Mockito.verify(opponent1, Mockito.times(1)).addCardToHand(opponent2Card);
                Mockito.verify(opponent2, Mockito.times(1)).addCardToHand(opponent1Card);
                Mockito.verify(mockPrintStream, Mockito.times(1)).println(opponent2.checkTopCard(0));
                
                // Reset the mock interactions to avoid interference
                Mockito.reset(user, mockPrintStream, mockScanner, mockPlayerList);
            }
        }
    }

    /**
     * Tests the Cardinal playCard method.
     * 
     * Type of test: Black Box
     * Input: user plays Cardinal, chooses same opponent twice
     * Expected output: user reprompted for different second opponent
     */
    @Test
    void testCardinalDoubleOpponent() {
        for (Card opponent1Card : Card.values()) {
            for (Card opponent2Card : Card.values()) {
                if (opponent1Card == opponent2Card) {
                    continue;
                }
                // Create mocks
                Player user = Mockito.spy(new Player("user"));
                Player opponent1 = Mockito.spy(new Player("opponent1"));
                opponent1.addCardToHand(opponent1Card);
                Player opponent2 = Mockito.spy(new Player("opponent2"));
                opponent2.addCardToHand(opponent2Card);

                // Define behavior of mocks
                Mockito.when(mockScanner.nextLine()).thenReturn("opponent1", "opponent1", "opponent2");
                Mockito.when(mockScanner.nextInt()).thenReturn(1);
                Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
                Mockito.when(mockPlayerList.getPlayer("opponent1")).thenReturn(opponent1);
                Mockito.when(mockPlayerList.getPlayer("opponent2")).thenReturn(opponent2);

                // Act
                mockGame.playCard(Card.CARDINAL, user);

                // Verify that the hands are swapped
                Mockito.verify(opponent1, Mockito.times(1)).addCardToHand(opponent2Card);
                Mockito.verify(opponent2, Mockito.times(1)).addCardToHand(opponent1Card);
                Mockito.verify(mockPrintStream, Mockito.times(1)).println(opponent2.checkTopCard(0));
                Mockito.verify(mockPrintStream, Mockito.times(1)).println("You cannot target the same player twice.");
                
                // Reset the mock interactions to avoid interference
                Mockito.reset(user, mockPrintStream, mockScanner, mockPlayerList);
            }
        }
    }

    /**************************************************************************
     * CONSTABLE TESTS
     *************************************************************************/

    /* 
     * Not having a Constable card in discarded pile should not give a token when eliminated
     */
    @Test
    public void testNotGetTokenWhenEliminated() {
        Player spyUser = Mockito.spy(new Player("User"));
        spyUser.addDiscarded(Card.PRIEST);
        spyUser.addDiscarded(Card.GUARD);
        spyUser.addDiscarded(Card.PRINCE);
        spyUser.addDiscarded(Card.BARON);
        spyUser.addCardToHand(Card.CONSTABLE);

        spyUser.eliminate();
        assertEquals(spyUser.getTokens(), 0);
    }

    /* 
     * Having a Constable card in discarded pile should give a token when eliminated
     */
    @Test
    public void testConstableGetTokenWhenEliminated() {
        Player spyUser = Mockito.spy(new Player("User"));
        spyUser.addDiscarded(Card.GUARD);
        spyUser.addDiscarded(Card.CONSTABLE);
        spyUser.addDiscarded(Card.PRINCESS);
        spyUser.addCardToHand(Card.PRINCE);

        spyUser.eliminate();
        assertEquals(spyUser.getTokens(), 1);
    }

    /**************************************************************************
     * QUEEN TESTS
     *************************************************************************/

    /**
     * Test the Queen playCard method.
     * 
     * Type of Test: Black Box
     * Input: Opponent has a higher value card than user
     * Expected Output: Opponent is eliminated, user is not eliminated
     */
    @Test
    public void testQueenUserLessThanOpponent() {
        for (Card opponentCard : Card.values()) {
            for (Card userCard : Card.values()) {
                if (opponentCard.getValue() > userCard.getValue()) {
                    // Create mocks
                    Player opponent = Mockito.spy(new Player("opponent"));
                    opponent.addCardToHand(opponentCard);
                    Player user = Mockito.spy(new Player("user"));
                    user.addCardToHand(userCard);
                    Card mockCard = Mockito.spy(Card.QUEEN);

                    // Define behavior of mocks
                    Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
                    Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
                    Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
                    Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

                    // Act
                    mockGame.playCard(mockCard, user);

                    // Verify that the user is eliminated and the opponent is not
                    Mockito.verify(mockCard, Mockito.times(1)).executeAction(mockGame, user);
                    Mockito.verify(opponent, Mockito.times(1)).eliminate();
                    Mockito.verify(user, Mockito.times(0)).eliminate();
                    Assertions.assertTrue(user.hasCardsInHand());
                    Assertions.assertFalse(opponent.hasCardsInHand());

                    // Reset the mock interactions to avoid interference
                    Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
                }
            }
        }
    }

    /**
     * Test the Queen playCard method.
     * 
     * Type of Test: Black Box
     * Input: Opponent and user have the same value card
     * Expected Output: Both the user and the opponent are not eliminated
     */
    @Test
    public void testQueenUserEqual() {
        for (Card cardIteration : Card.values()) {
            if (cardIteration == Card.BARON 
                || cardIteration == Card.KING 
                || cardIteration == Card.COUNTESS 
                || cardIteration == Card.PRINCESS
                || cardIteration == Card.QUEEN) {
                continue;
            }
            // Create mocks
            Player opponent = Mockito.spy(new Player("opponent"));
            opponent.addCardToHand(cardIteration);
            Player user = Mockito.spy(new Player("user"));
            user.addCardToHand(cardIteration);

            // Define behavior of mocks
            Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
            Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
            Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
            Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

            // Act
            mockGame.playCard(Card.QUEEN, user);

            // Verify that both the user and the opponent is not eliminated
            Mockito.verify(user, Mockito.times(0)).eliminate();
            Mockito.verify(opponent, Mockito.times(0)).eliminate();
            Assertions.assertTrue(user.hasCardsInHand());
            Assertions.assertTrue(opponent.hasCardsInHand());

            // Reset the mock interactions to avoid interference
            Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
        }
    }

    /**
     * Test the Queen playCard method.
     * 
     * Type of Test: Black Box
     * Input: User has a higher value card than opponent
     * Expected Output: Opponent is not eliminated, user is eliminated
     */
    @Test
    public void testQueenUserGreaterThanOpponent() {
        for (Card opponentCard : Card.values()) {
            for (Card userCard : Card.values()) {
                if (opponentCard.getValue() < userCard.getValue()) {
                    // Create mocks
                    Player opponent = Mockito.spy(new Player("opponent"));
                    opponent.addCardToHand(opponentCard);
                    Player user = Mockito.spy(new Player("user"));
                    user.addCardToHand(userCard);
                    Card mockCard = Mockito.spy(Card.QUEEN);

                    // Define behavior of mocks
                    Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
                    Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
                    Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
                    Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

                    // Act
                    mockGame.playCard(mockCard, user);

                    // Verify that the user is eliminated and the opponent is not
                    Mockito.verify(mockCard, Mockito.times(1)).executeAction(mockGame, user);
                    Mockito.verify(opponent, Mockito.times(0)).eliminate();
                    Mockito.verify(user, Mockito.times(1)).eliminate();
                    Assertions.assertFalse(user.hasCardsInHand());
                    Assertions.assertTrue(opponent.hasCardsInHand());

                    // Reset the mock interactions to avoid interference
                    Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
                }
            }
        }
    }

    /**************************************************************************
     * COUNT TESTS
     *************************************************************************/

    /**
     * Test the Count playCard method.
     * 
     * Type of Test: Black Box
     * Input: P1 has 2 Counts in discard, has card in hand of one less than P2
     * Expected P1 wins the comparison
     */
    @Test
    public void testCountInfluenceWinner() {
        List<Card> l1 = new ArrayList<Card>();
        l1.add(Card.HANDMAIDEN);
        List<Card> l2 = new ArrayList<Card>();
        l2.add(Card.PRINCE);

        Hand h1 = new Hand(l1);
        Hand h2 = new Hand(l2);

        List<Card> d1 = new ArrayList<Card>();
        d1.add(Card.COUNT);
        d1.add(Card.COUNT);
        List<Card> d2 = new ArrayList<Card>();
        d2.add(Card.PRINCE);

        DiscardPile dp1 = new DiscardPile(d1);
        DiscardPile dp2 = new DiscardPile(d2);

        Player p1 = new Player("p1", h1, dp1);
        Player p2 = new Player("p2", h2, dp2);

        List<Player> players = new ArrayList<Player>();
        players.add(p1);
        players.add(p2);

        PlayerList pl = new PlayerList(players, mockPrintStream);

        List<Player> winners = pl.getRoundWinners();

        assertEquals(winners.size(), 1);
        assertEquals(winners.get(0), p1);
    }

    /**
     * Tests the Count playCard method.
     * 
     * Type of test: Black Box
     * Input: user plays Count
     * Expected output: n/a
     */
    @Test
    public void playCount() {
        // create mocks
        Card mockCount = Mockito.spy(Card.COUNT);
        
        // define behavior of mocks
        // none

        // act
        Player user = new Player("user");
        mockGame.playCard(mockCount, user);

        // verify
        Mockito.verify(mockCount).executeAction(mockGame, user);

    }
}
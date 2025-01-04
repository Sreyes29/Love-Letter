package edu.cmu.f24qa.loveletter;

import java.io.PrintStream;
import java.util.Scanner;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class WhiteboxCardLogicTest {

    public Game mockGame;
    public Deck mockDeck;
    public Scanner mockScanner;
    public PlayerList mockPlayerList;
    public PrintStream mockPrintStream;
    
    public WhiteboxCardLogicTest() {
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
     * GUARD TESTS
     *************************************************************************/

    /**
     * Tests the Guard playCard method.
     * 
     * Type of test: White Box
     * Input: user targets a null player
     * Expected output: user is reprompted
     */
    @Test
    void testGuardGuessCorrect() {
        // Arrange
        Player mockUser = Mockito.spy(new Player("user"));
        Player mockOpponent = Mockito.spy(new Player("opponent"));
        Card mockGuard = Mockito.spy(Card.GUARD);

        Mockito.when(mockScanner.nextLine()).thenReturn("opponent", "Prince");
        Mockito.when(mockPlayerList.checkAvailablePlayers(mockUser)).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(mockOpponent);
        
        // Act
        mockUser.addCardToHand(Card.GUARD);
        mockOpponent.addCardToHand(Card.PRINCE);
        mockGame.playCard(mockGuard, mockUser);

        // Assert
        Mockito.verify(mockPlayerList, Mockito.times(1)).checkAvailablePlayers(mockUser);
        Mockito.verify(this.mockPrintStream, Mockito.times(1)).print("Who would you like to target: ");
        Mockito.verify(this.mockPrintStream, Mockito.times(1)).print("Which card would you like to guess: ");
        Mockito.verify(this.mockPrintStream, Mockito.times(1)).println("You have guessed correctly!");
        Mockito.verify(mockOpponent, Mockito.times(1)).eliminate();
    }

    /**
     * Tests the Guard playCard method.
     * 
     * Type of test: White Box
     * Input: user guess incorrectly
     * Expected output: nothing happens, opponent is not eliminated
     */
    @Test
    void testGuardGuessInCorrect() {
        // Arrange
        Player mockUser = Mockito.spy(new Player("user"));
        Player mockOpponent = Mockito.spy(new Player("opponent"));
        Card mockGuard = Mockito.spy(Card.GUARD);

        Mockito.when(mockScanner.nextLine()).thenReturn("user", "opponent", "Guard", "Princess");
        Mockito.when(mockPlayerList.checkAvailablePlayers(mockUser)).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("user")).thenReturn(mockUser);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(mockOpponent);
        
        // Act
        mockUser.addCardToHand(Card.GUARD);
        mockOpponent.addCardToHand(Card.PRINCE);
        mockGame.playCard(mockGuard, mockUser);

        // Assert
        Mockito.verify(this.mockPrintStream, Mockito.times(2)).print("Who would you like to target: ");
        Mockito.verify(this.mockPrintStream, Mockito.times(1)).println("Invalid target");
        Mockito.verify(this.mockPrintStream, Mockito.times(2)).print("Which card would you like to guess: ");
        Mockito.verify(this.mockPrintStream, Mockito.times(1)).println("You cannot guess this card");
        Mockito.verify(this.mockPrintStream, Mockito.times(1)).println("You have guessed incorrectly");
        Mockito.verify(mockOpponent, Mockito.times(0)).eliminate();
    }
    
    /**************************************************************************
     * PRIEST TESTS
     *************************************************************************/

     /**
     * Tests the Priest playCard method.
     * 
     * Type of test: White Box
     * Input: opponent is null
     * Expected output: target is initially invalid
     */
    @Test
    public void testPriestWithNullOpponent() {        
        // Having a null opponent
        Player nullOpponent = null;
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.HANDMAIDEN);
        Card mockPriest = Mockito.spy(Card.PRIEST);

        // Define behavior of mocks
        Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
        Mockito.when(mockScanner.nextLine()).thenReturn("nullOpponent", "opponent");
        Mockito.when(mockPlayerList.checkAvailablePlayers(Mockito.any())).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("nullOpponent")).thenReturn(nullOpponent);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

        // Act
        Player user = new Player("user");
        mockGame.playCard(mockPriest, user);

        // Verify that target is invalid initially
        Mockito.verify(mockPrintStream).println("Invalid target");
        Mockito.verify(mockPrintStream, Mockito.times(2)).print("Who would you like to target: ");
        Mockito.verify(mockPrintStream).println("opponent shows you a " + opponent.checkTopCard(0));
    }

    /**
     * Tests the Priest playCard method.
     * 
     * Type of test: White Box
     * Input: opponent is valid
     * Expected output: player sees opponent's card
     */
    @Test
    public void testPriestWithValidOpponent() {        
        // Having a valid opponent
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.HANDMAIDEN);
        Card mockPriest = Mockito.spy(Card.PRIEST);

        // Define behavior of mocks
        Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
        Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
        Mockito.when(mockPlayerList.checkAvailablePlayers(Mockito.any())).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

        // Act
        Player user = new Player("user");
        mockGame.playCard(mockPriest, user);

        // Verify that player sees opponent's card
        Mockito.verify(mockPrintStream, Mockito.times(1)).print("Who would you like to target: ");
        Mockito.verify(mockPrintStream).println("opponent shows you a " + opponent.checkTopCard(0));
    }
    
    /**************************************************************************
     * BARON TESTS
     *************************************************************************/

    /**
     * Tests the Baron playCard method.
     * 
     * Type of test: White Box
     * Input: user targets a null player, then target opponent with a lower value card
     * Expected output: user gets reprompted and opponent gets eliminated
     */
    @Test
    public void testBaronUserGreaterThanOpponent() {
        // Create mocks
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.GUARD);
        Player user = Mockito.spy(new Player("user"));
        user.addCardToHand(Card.BARON);
        Card mockBaron = Mockito.spy(Card.BARON);

        // Define behavior of mocks
        Mockito.when(mockScanner.nextLine()).thenReturn("NonExistentPlayer", "opponent");
        Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

        // Act
        mockGame.playCard(mockBaron, user);

        // Verify that it is not possible to target a non-existent player
        Mockito.verify(mockPrintStream, Mockito.times(1)).println("Invalid target");
        // Verify that the user is not eliminated and the opponent is
        Mockito.verify(user, Mockito.times(0)).eliminate();
        Mockito.verify(opponent, Mockito.times(1)).eliminate();
        // Reset the mock interactions to avoid interference
        Mockito.reset(user, mockPrintStream, mockScanner, mockPlayerList);
    }

    /**
     * Tests the Baron playCard method.
     * 
     * Type of test: White Box
     * Input: user targets an opponent with a lower value card
     * Expected output: user is not eliminated and opponent is eliminated
     */
    @Test
    public void testBaronUserLessThanOpponent() {
        // Create mocks
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.PRINCESS);
        Player user = Mockito.spy(new Player("user"));
        user.addCardToHand(Card.GUARD);
        Card mockBaron = Mockito.spy(Card.BARON);

        // Define behavior of mocks
        Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
        Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
        Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

        // Act
        mockGame.playCard(mockBaron, user);

        // Verify that the user is not eliminated and the opponent is
        Mockito.verify(user, Mockito.times(1)).eliminate();
        Mockito.verify(opponent, Mockito.times(0)).eliminate();
        // Reset the mock interactions to avoid interference
        Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
    }

    /**
     * DEPRECATED: This test is no longer valid as the Baron card has been updated to tie if the user and opponent have the same card.
     * Tests the Baron playCard method.
     * 
     * Type of test: White Box
     * Input: user targets an opponent with a equal value card, but user has a higher value card in discard pile
     * Expected output: user is eliminated and opponent is not
     */
    // @Test
    @Deprecated
    public void testBaronDiscardUserGreaterThanOpponent() {
        // Create mocks
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.GUARD);
        Player user = Mockito.spy(new Player("user"));
        user.addCardToHand(Card.GUARD);

        // Define behavior of mocks
        Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
        Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);
        Mockito.when(opponent.getDiscardedValue()).thenReturn(5);
        Mockito.when(user.getDiscardedValue()).thenReturn(6);

        // Act
        mockGame.playCard(Card.BARON, user);

        // Verify that the user is not eliminated and the opponent is
        Mockito.verify(mockPrintStream, Mockito.times(1)).println("You have the same card!");
        Mockito.verify(user, Mockito.times(1)).eliminate();
        Mockito.verify(opponent, Mockito.times(0)).eliminate();
        // Reset the mock interactions to avoid interference
        Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
    }

    /**
     * DEPRECATED: This test is no longer valid as the Baron card has been updated to tie if the user and opponent have the same card.
     * Tests the Baron playCard method.
     * 
     * Type of test: White Box
     * Input: user targets an opponent with a equal value card, but user has a lower value card in discard pile
     * Expected output: user is not eliminated and opponent is
     */
    // @Test
    @Deprecated
    public void testBaronDiscardUserLessThanOpponent() {
        // Create mocks
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.GUARD);
        Player user = Mockito.spy(new Player("user"));
        user.addCardToHand(Card.GUARD);

        // Define behavior of mocks
        Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
        Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);
        Mockito.when(opponent.getDiscardedValue()).thenReturn(6);
        Mockito.when(user.getDiscardedValue()).thenReturn(5);

        // Act
        mockGame.playCard(Card.BARON, user);

        // Verify that the user is eliminated and the opponent is not
        Mockito.verify(mockPrintStream, Mockito.times(1)).println("You have the same card!");
        Mockito.verify(user, Mockito.times(0)).eliminate();
        Mockito.verify(opponent, Mockito.times(1)).eliminate();
        // Reset the mock interactions to avoid interference
        Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
    }

    /**
     * AFTER BUG FIX TEST TO REACH BRANCH COVERAGE
     * Tests the Baron playCard method.
     * 
     * Type of test: White Box
     * Input: user targets an opponent with a equal value card
     * Expected output: both user and opponent is not eliminated
     */
    @Test
    public void testBaronEqualOpponent() {
        // Create mocks
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.GUARD);
        Player user = Mockito.spy(new Player("user"));
        user.addCardToHand(Card.GUARD);
        Card mockBaron = Mockito.spy(Card.BARON);

        // Define behavior of mocks
        Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
        Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

        // Act
        mockGame.playCard(mockBaron, user);

        // Verify that both user and opponent are not eliminated
        Mockito.verify(mockBaron, Mockito.times(1)).executeAction(mockGame, user);
        Mockito.verify(mockPrintStream, Mockito.times(1)).println("You have the same card! It's a tie!");
        Mockito.verify(user, Mockito.times(0)).eliminate();
        Mockito.verify(opponent, Mockito.times(0)).eliminate();
        // Reset the mock interactions to avoid interference
        Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
    }

    /**
     * AFTER BUG FIX TEST TO REACH BRANCH COVERAGE
     * Tests the Baron playCard method.
     * 
     * Type of test: White Box
     * Input: user targets an opponent with a the same special card
     * Expected output: game state error
     */
    @Test
    public void testBaronEqualOpponentSpecial() {
        // Create mocks
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.PRINCESS);
        Player user = Mockito.spy(new Player("user"));
        user.addCardToHand(Card.PRINCESS);
        Card mockBaron = Mockito.spy(Card.BARON);

        // Define behavior of mocks
        Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
        Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

        // Act
        mockGame.playCard(mockBaron, user);

        // Verify that the user is eliminated and the opponent is not
        Mockito.verify(mockPrintStream, Mockito.times(1)).println("Game State is Corrupted. Please restart the game.");
        // Reset the mock interactions to avoid interference
        Mockito.reset(user, opponent, mockPrintStream, mockScanner, mockPlayerList);
    }

    /**************************************************************************
     * HANDMAIDEN TESTS
     *************************************************************************/

    /**
     * Tests the Handmaiden playCard method.
     * 
     * Type of test: White Box
     * Input: n/a
     * Expected output: user is protected
     */
    @Test
    public void testHandmaiden() {
        // create mocks
        PrintStream mockPrintStream = Mockito.mock(PrintStream.class);

        // define behavior of mocks
        Mockito.when(mockGame.getPrinter()).thenReturn(mockPrintStream);
        Mockito.doNothing().when(mockPrintStream).println(Mockito.anyString());

        // act
        Player user = Mockito.spy(new Player("user"));
        mockGame.playCard(Card.HANDMAIDEN, user);

        // verify statement coverage
        Mockito.verify(mockGame, Mockito.times(1)).getPrinter();
        Mockito.verify(user, Mockito.times(1)).switchProtection();
        Mockito.verify(mockPrintStream, Mockito.times(1)).println("You are now protected until your next turn");
        
        // verify
        Assertions.assertTrue(user.isProtected());
    }

    /**
     * AFTER BUG FIX TEST TO REACH BRANCH COVERAGE
     * Tests the Handmaiden playCard method with protected user.
     * 
     * Type of test: White Box
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
     * Type of test: White Box
     * Input: opponent is null
     * Expected output: target is initially invalid
     */
    @Test
    public void testPrinceWithNullOpponent() {        
        // Having a null opponent
        Player nullOpponent = null;
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.HANDMAIDEN);

        // Define behavior of mocks
        Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
        Mockito.when(mockScanner.nextLine()).thenReturn("nullOpponent", "opponent");
        Mockito.when(mockPlayerList.getPlayer("nullOpponent")).thenReturn(nullOpponent);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);
        Mockito.when(mockDeck.draw()).thenReturn(Card.GUARD);

        // Act
        Player user = new Player("user");
        user.addCardToHand(Card.GUARD);
        mockGame.playCard(Card.PRINCE, user);

        // Verify that target is invalid initially
        Mockito.verify(mockPrintStream).println("Invalid target");
        Mockito.verify(mockPrintStream, Mockito.times(2)).print("Who would you like to target: ");
        Mockito.verify(opponent, Mockito.times(1)).eliminate();
    }

    /**
     * Tests the Prince playCard method.
     * 
     * Type of test: White Box
     * Input: opponent is valid
     * Expected output: opponent discards their card
     */
    @Test
    public void testPrinceWithValidOpponent() {        
        // Having a valid opponent
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.HANDMAIDEN);

        // Define behavior of mocks
        Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
        Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);
        Mockito.when(mockDeck.draw()).thenReturn(Card.GUARD);

        // Act
        Player user = new Player("user");
        user.addCardToHand(Card.GUARD);
        mockGame.playCard(Card.PRINCE, user);

        // Verify that the opponent draws a new card
        Mockito.verify(mockPrintStream, Mockito.times(1)).print("Who would you like to target: ");
        Mockito.verify(opponent, Mockito.times(1)).eliminate();
    }

    /**
     * Tests the Prince playCard method.
     * 
     * Type of test: White Box
     * Input: opponent has a princess card
     * Expected output: opponent is eliminated
     */
    @Test
    public void testPrinceWithOpponentPrincess() {        
        // Opponent has a princess card
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.PRINCESS);

        // Define behavior of mocks
        Mockito.doNothing().when(mockPrintStream).print(Mockito.anyString());
        Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);
        Mockito.when(mockDeck.draw()).thenReturn(Card.GUARD);

        // Act
        Player user = new Player("user");
        user.addCardToHand(Card.GUARD);
        mockGame.playCard(Card.PRINCE, user);

        // Verify that the opponent draws a new card
        Mockito.verify(mockPrintStream, Mockito.times(1)).print("Who would you like to target: ");
        Mockito.verify(opponent, Mockito.times(1)).eliminate();
    }

    /**************************************************************************
     * KING TESTS
     *************************************************************************/

    /**
     * Tests the King playCard method.
     * 
     * Type of test: White Box
     * Input: user targets a null player
     * Expected output: user is reprompted
     */
    @Test
    public void testKingTargetNull() {
        // Create mocks
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.GUARD);
        Player user = Mockito.spy(new Player("user"));
        user.addCardToHand(Card.BARON);
        Card mockKing = Mockito.spy(Card.KING);

        // Define behavior of mocks
        Mockito.when(mockScanner.nextLine()).thenReturn("NonExistentPlayer", "opponent");
        Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

        // Act
        mockGame.playCard(mockKing, user);

        // Verify that it is not possible to target nonexistent player
        Mockito.verify(mockPrintStream, Mockito.times(1)).println("Invalid target");

        // Reset the mock interactions to avoid interference
        Mockito.reset(user, mockPrintStream, mockScanner, mockPlayerList);
    }

    /**
     * Tests the King playCard method.
     * 
     * Type of test: White Box
     * Input: user targets an opponent
     * Expected output: user and opponent swap hands
     */
    @Test
    void testKingSwappedHands() {
        // Create mocks
        Player opponent = Mockito.spy(new Player("opponent"));
        opponent.addCardToHand(Card.GUARD);
        Player user = Mockito.spy(new Player("user"));
        user.addCardToHand(Card.BARON);
        Card mockKing = Mockito.spy(Card.KING);

        // Define behavior of mocks
        Mockito.when(mockScanner.nextLine()).thenReturn("opponent");
        Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
        Mockito.when(mockPlayerList.getPlayer("opponent")).thenReturn(opponent);

        // Act
        mockGame.playCard(mockKing, user);

        // Verify that the hands are swapped
        Mockito.verify(opponent, Mockito.times(1)).addCardToHand(Card.BARON);
        Mockito.verify(user, Mockito.times(1)).addCardToHand(Card.GUARD);

        // Reset the mock interactions to avoid interference
        Mockito.reset(user, mockPrintStream, mockScanner, mockPlayerList);
    }

    /**************************************************************************
     * COUNTESS TESTS
     *************************************************************************/

    /**
     * Tests the Countess playCard method.
     * 
     * Type of test: White Box
     * Input: user plays Countess
     * Expected output: n/a
     */
    @Test
    public void testCountess() {
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

    /**
     * AFTER BUG FIX TEST TO REACH BRANCH COVERAGE
     * Tests the Countess playCard method 
     * 
     * Type of test: White Box
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
    
    /**************************************************************************
     * PRINCESS TESTS
     *************************************************************************/

    /**
     * Tests the Princess playCard method.
     * 
     * Type of test: White Box
     * Input: n/a
     * Expected output: user is eliminated
     */
    @Test
    public void testPrincess() {
        // act
        Player user = Mockito.spy(new Player("user"));
        user.addCardToHand(Card.PRINCESS);
        mockGame.playCard(Card.PRINCESS, user);

        // Verify
        Mockito.verify(user, Mockito.times(1)).eliminate();
        Assertions.assertFalse(user.hasCardsInHand());
    }

    /**************************************************************************
     * CARDINAL TESTS
     *************************************************************************/

     /**
     * Tests the Cardinal playCard method.
     * 
     * Type of test: White Box
     * Input: user plays Cardinal, gives bad input for seeing a hand
     * Expected output: user reprompted for different second opponent
     */
    @Test
    void testCardinalWrongInt() {
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
                Mockito.when(mockScanner.nextInt()).thenReturn(2, 0);
                Mockito.when(mockPlayerList.checkAvailablePlayers(user)).thenReturn(true);
                Mockito.when(mockPlayerList.getPlayer("opponent1")).thenReturn(opponent1);
                Mockito.when(mockPlayerList.getPlayer("opponent2")).thenReturn(opponent2);

                // Act
                mockGame.playCard(Card.CARDINAL, user);

                // Verify that the hands are swapped
                Mockito.verify(opponent1, Mockito.times(1)).addCardToHand(opponent2Card);
                Mockito.verify(opponent2, Mockito.times(1)).addCardToHand(opponent1Card);
                Mockito.verify(mockPrintStream, Mockito.times(1)).println(opponent1.checkTopCard(0));
                Mockito.verify(mockPrintStream, Mockito.times(1)).println("Invalid choice. Please enter 0 or 1.");
                
                // Reset the mock interactions to avoid interference
                Mockito.reset(user, mockPrintStream, mockScanner, mockPlayerList);
            }
        }
    }
}

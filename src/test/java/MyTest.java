import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MyTest {
	//------------------------------Card Class Tests------------------------------------

	@Test
	void cardConstructorTest(){
		Card test = new Card('H', 11);
		assertEquals('H', test.getSuit());
		assertEquals(11, test.getValue());
	}

	@Test
	void cardConstructorSuitTest(){
		Card test = new Card('h', 11);

		//suit should be automatically capitalized
		assertEquals('H', test.getSuit());

		//Changing suits
		test.setSuit('c');

		assertNotEquals('H', test.getSuit());
		assertEquals('C', test.getSuit());
	}

	@Test
	void cardConstructorValueTest(){
		Card test = new Card('h', 15);

		//Value out of range, should return 0
		assertEquals(0, test.getValue());

		test.setValue(11);

		//Placing value within range
		assertEquals(11, test.getValue());
	}

	//------------------------------Deck Class Tests------------------------------------

	@Test
	void deckSizeTest(){
		Deck test = new Deck();
		assertEquals(52, test.size());
	}

	@Test
	void deckSpecificCardTest(){
		Deck test = new Deck();

		boolean specCard1 = false;
		boolean specCard2 = false;
		boolean specCard3 = false;

		for (Card card : test){
			if (card.getSuit() == 'H' && card.getValue() == 11){
				//Specific Card: Q of Hearts
				specCard1 = true;
			}

			if (card.getSuit() == 'C' && card.getValue() == 3){
				//Specific Card: 3 of Clubs
				specCard2 = true;
			}

			if (card.getSuit() == 'D' && card.getValue() == 3){
				//Specific Card: 6 of Diamonds
				specCard3 = true;
			}
		}

		assertTrue(specCard1);
		assertTrue(specCard2);
		assertTrue(specCard3);
	}


	@Test
	void deckAllSuitsTest(){
		Deck test = new Deck();
		
		int cCounter = 0;
		int dCounter = 0;
		int hCounter = 0;
		int sCounter = 0;

		for(Card card : test){
			switch (card.getSuit()){
				case 'C': cCounter++; break;
				case 'D': dCounter++; break;
				case 'H': hCounter++; break;
				case 'S': sCounter++; break;
			}
		}

		assertEquals(13, cCounter);
		assertEquals(13, dCounter);
		assertEquals(13, hCounter);
		assertEquals(13, sCounter);
	}

	@Test
	void deckAllValuesCardTest(){
		Deck test = new Deck();

		//Captures an int array of 15 0s for each of the 2-14 numbers
		int[] valCount = new int [15];

		for (Card card : test){
			int val = card.getValue();
			//Updates the counter of the index by 1 for each value seen
			valCount[val]++;
		}

		for (int i = 2; i <= 14; i++){
			//Each value should only appear in the deck 4 times
			assertEquals(4, valCount[i]);
		}
	}

	@Test
	void newDeckTest(){
		Deck test = new Deck(); 

		//Save current instance of the Deck
		ArrayList<Card> currDeck = new ArrayList<>(test);
		
		test.newDeck();

		//Current Instance and Previous Instance of Deck should be different
		assertNotEquals(currDeck, test);
	}

	//------------------------------Player Class Tests------------------------------------
	public Player player = new Player();

	@Test
	void playerHandSizeTest(){
		assertTrue(player.getHand().isEmpty());
	}

	@Test
	void anteBetTest() {
        player.setAnteBet(10);
        assertEquals(10, player.getAnteBet());
    }

	@Test
	void playBetTest() {
        player.setPlayBet(10);
        assertEquals(10, player.getPlayBet());
    }

	@Test
	void pairPlusBetTest() {
        player.setPairPlusBet(10);
        assertEquals(10, player.getPairPlusBet());
    }

	@Test
	void totalWinnings(){
		player.setTotalWinnings(100);
		assertEquals(100, player.getTotalWinnings());
	}

	//------------------------------Dealer Class Tests------------------------------------
	public Dealer dealer = new Dealer();

	@Test
	void dealerHandSizeTest(){
		ArrayList<Card> test = dealer.dealHand();
		
		assertEquals(3, test.size());
	}

	@Test
	void dealerDeckSizeTest(){
		assertEquals(52, dealer.getDeck().size());

		dealer.dealHand();
		
		//3 of the 52 cards should be removed from the deck
		assertEquals(52 - 3, dealer.getDeck().size());
	}

	@Test
	void dealerCardNotInDeckTest(){
		ArrayList<Card> test = dealer.dealHand();

		//Cards removed from the Deck should not still be in the Deck
		for (Card card : test){
			assertFalse(dealer.getDeck().contains(card));
		}
	}

	@Test
	void dealerUniqueHandsTest(){
		ArrayList<Card> test1 = dealer.dealHand();
		ArrayList<Card> test2 = dealer.dealHand();
				
		assertEquals(test2.size(), test1.size());

		boolean uniqueCard = true;

		for (int i = 0; i < 3; i++){
			if ((test1.get(i).getSuit() == test2.get(i).getSuit()) && 
			test1.get(i).getValue() == test2.get(i).getSuit()){
				uniqueCard = false;
			}
		}

		assertTrue(uniqueCard);
	}

	@Test
	void dealerNewDeckTest(){
		dealer.getDeck().newDeck();

		//Deck should be reshuffed once Deck size < 34
		for (int i = 0; i < 10; i++){
			//Pull 30 cards, making Deck size < 34
			dealer.dealHand();
		}

		//Helper Function to check if reshuffled deck is valid
		assertTrue(dealer.validDeck(dealer.getDeck()));
	}

	//------------------------------ThreeCardLogic Class Tests------------------------------------

	@Test
	void highCardTest(){
		ArrayList<Card> test = new ArrayList<>();
		
		test.add(new Card('C', 10));
		test.add(new Card('H',7));
		test.add(new Card('D',4));

		assertEquals(0, ThreeCardLogic.evalHand(test));

	}

	@Test
	void straightTest(){
		ArrayList<Card> test = new ArrayList<>();
		
		test.add(new Card('C', 3));
		test.add(new Card('H',4));
		test.add(new Card('D',5));

		assertEquals(3, ThreeCardLogic.evalHand(test));
	}

	@Test
	void flushTest(){
		ArrayList<Card> test = new ArrayList<>();
		
		test.add(new Card('H', 3));
		test.add(new Card('H',1));
		test.add(new Card('H',5));

		assertEquals(4, ThreeCardLogic.evalHand(test));
	}

	@Test
	void straightFlushTest(){
		ArrayList<Card> test = new ArrayList<>();

		test.add(new Card('H', 2));
		test.add(new Card('H',3));
		test.add(new Card('H',4));

		assertEquals(1, ThreeCardLogic.evalHand(test));

		ArrayList<Card> trash = new ArrayList<>();

		trash.add(new Card('H', 2));
		trash.add(new Card('H', 1));
		trash.add(new Card('C', 3));

		assertNotEquals(1, ThreeCardLogic.evalHand(trash));
	}

	@Test
	void pairTest(){
		ArrayList<Card> test = new ArrayList<>();
		
		test.add(new Card('C', 3));
		test.add(new Card('H',3));
		test.add(new Card('D',5));

		assertEquals(5, ThreeCardLogic.evalHand(test));
	}

	@Test
	void ThreesTest(){
		ArrayList<Card> test = new ArrayList<>();
		
		test.add(new Card('C', 4));
		test.add(new Card('H',4));
		test.add(new Card('D',4));

		assertEquals(2, ThreeCardLogic.evalHand(test));
	}

	@Test
	void faceCardsTest(){
		ArrayList<Card> test1 = new ArrayList<>();
		
		//Straight Flush with Face Cards
		test1.add(new Card('H', 11));
		test1.add(new Card('H',12));
		test1.add(new Card('H',13));

		assertEquals(1, ThreeCardLogic.evalHand(test1));

		ArrayList<Card> test2 = new ArrayList<>();
		
		//Threes of Queens
		test2.add(new Card('H', 12));
		test2.add(new Card('S',12));
		test2.add(new Card('D',12));

		assertEquals(2, ThreeCardLogic.evalHand(test2));
	}

	@Test
	void evalPPTenBetTest(){
		ArrayList<Card> test = new ArrayList<>();

		test.add(new Card('C', 3));
		test.add(new Card('H',4));
		test.add(new Card('D',5));

		//Bet set to 10 for initial comparison
		assertEquals(60, ThreeCardLogic.evalPPWinnings(test, 10));
	}

	/*
	 * All PP Winnings Bets at 5 for consistency testing
	*/

	public static int bet = 5;
	
	@Test
	void evalPPHighCardTest(){
		ArrayList<Card> test = new ArrayList<>();

		test.add(new Card('C', 10));
		test.add(new Card('H',7));
		test.add(new Card('D',4));

		assertEquals(0, ThreeCardLogic.evalPPWinnings(test, bet));
	}
	
	@Test
	void evalPPStraightTest(){
		ArrayList<Card> test = new ArrayList<>();

		test.add(new Card('C', 3));
		test.add(new Card('H',4));
		test.add(new Card('D',5));

		assertEquals(30, ThreeCardLogic.evalPPWinnings(test, bet));
	}

	@Test
	void evalPPFlushTest(){
		ArrayList<Card> test = new ArrayList<>();
	
		test.add(new Card('H', 3));
		test.add(new Card('H',1));
		test.add(new Card('H',5));

		assertEquals(15, ThreeCardLogic.evalPPWinnings(test, bet));
	}
	
	@Test
	void evalPPStraightFlushTest(){
		ArrayList<Card> test = new ArrayList<>();

		test.add(new Card('H', 2));
		test.add(new Card('H',3));
		test.add(new Card('H',4));

		assertEquals(200, ThreeCardLogic.evalPPWinnings(test, bet));
	}

	@Test
	void evalPPPairTest(){
		ArrayList<Card> test = new ArrayList<>();

		test.add(new Card('C', 3));
		test.add(new Card('H',3));
		test.add(new Card('D',5));

		assertEquals(5, ThreeCardLogic.evalPPWinnings(test, bet));
	}

	@Test
	void evalPPThreesTest(){
		ArrayList<Card> test = new ArrayList<>();

		test.add(new Card('C', 4));
		test.add(new Card('H',4));
		test.add(new Card('D',4));

		assertEquals(150, ThreeCardLogic.evalPPWinnings(test, bet));
	}

	@Test
	void evalPPfaceCardsTest(){
		ArrayList<Card> test1 = new ArrayList<>();
		
		//Straight Flush with Face Cards
		test1.add(new Card('H', 11));
		test1.add(new Card('H',12));
		test1.add(new Card('H',13));

		assertEquals(200, ThreeCardLogic.evalPPWinnings(test1, bet));

		ArrayList<Card> test2 = new ArrayList<>();
		
		//Threes of Queens
		test2.add(new Card('H', 12));
		test2.add(new Card('S',12));
		test2.add(new Card('D',12));

		assertEquals(150, ThreeCardLogic.evalPPWinnings(test2, bet));
	}


	public ArrayList<Card> dealerHand;
    public ArrayList<Card> playerHand;

	@Test
	void compareHandsDealerWinsTest(){
		dealerHand = new ArrayList<>();
		playerHand = new ArrayList<>();

		//Dealer: Straight Flush (best hand)
        dealerHand.add(new Card('H', 2));
        dealerHand.add(new Card('H', 3));
        dealerHand.add(new Card('H', 4));

        //Player: Flush (lower hand)
        playerHand.add(new Card('S', 4));
        playerHand.add(new Card('S', 7));
        playerHand.add(new Card('S', 9));

        assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand));
	}

	@Test
	void compareHandsPlayerWinsTest(){
		dealerHand = new ArrayList<>();
		playerHand = new ArrayList<>();

		//Dealer: Pair (lower hand)
        dealerHand.add(new Card('H', 5));
        dealerHand.add(new Card('C', 5));
        dealerHand.add(new Card('D', 2));

        //Player: Three of a Kind (best hand)
        playerHand.add(new Card('S', 4));
        playerHand.add(new Card('H', 4));
        playerHand.add(new Card('D', 4));

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand));
	}

	@Test
	void compareHandsSameHandDealerTest(){
		dealerHand = new ArrayList<>();
		playerHand = new ArrayList<>();

		//Dealer: High Card (King High)
        dealerHand.add(new Card('C', 13));
        dealerHand.add(new Card('S', 8));
        dealerHand.add(new Card('H',5));

        //Player: High Card (Queen High)
        playerHand.add(new Card('D', 12));
        playerHand.add(new Card('C', 8));
        playerHand.add(new Card('S', 5));

        assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand));
	}

	@Test
	void compareHandsSameHandPlayerTest(){
		dealerHand = new ArrayList<>();
		playerHand = new ArrayList<>();

		//Dealer: High Card (Queen High)
        dealerHand.add(new Card('D', 12));
        dealerHand.add(new Card('C', 8));
        dealerHand.add(new Card('S', 5));

        //Player: High Card (Queen High)
        playerHand.add(new Card('C', 13));
        playerHand.add(new Card('S', 8));
        playerHand.add(new Card('H',5));

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand));
	}
	
	@Test
	void compareHandsBothDrawTest(){
		dealerHand = new ArrayList<>();
		playerHand = new ArrayList<>();

		//Dealer: High Card
        dealerHand.add(new Card('C', 14));
        dealerHand.add(new Card('S', 9));
        dealerHand.add(new Card('H',7));

        //Player: High Card
        playerHand.add(new Card('D', 14));
        playerHand.add(new Card('C', 9));
        playerHand.add(new Card('S', 7));

        assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand));
	}

}

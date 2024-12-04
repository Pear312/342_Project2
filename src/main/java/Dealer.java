import java.util.ArrayList;

public class Dealer {
    
    private Deck theDeck;
    private ArrayList<Card> dealersHand;

    /**
     * A Dealer Default Constructor that makes a new deck and new hand 
     * after every constructor call, no params in this one
    */
    public Dealer(){
        theDeck = new Deck();

        dealersHand = new ArrayList<>();
    }

    /**
     * Deal Hand removes the top 3 Cards of the Deck and adds it to an Array List
     * @return an Array List of 3 Cards
     */
    public ArrayList<Card> dealHand(){
        ArrayList<Card> hand = new ArrayList<>();

        if (!validDeck(theDeck)){
            theDeck.newDeck();
        }

        for (int i = 0; i < 3; i++){
            //Since the deck is randomized, ask dealer to remove top card 3 times
            hand.add(theDeck.remove(0));
        }

        return hand;
    }

    /**
     * Helper Function to determine if the Deck has enough cards to be played
     * Deck must be greater than 34 cards to be considered valid 
     * @param theDeck
     * @return a boolean if the deck is of valid size or not
     */
    public boolean validDeck(Deck theDeck){
        return theDeck.size() > 34;
        //True if size > 34
        //False if size <= 34
    }

    /**
     * A getter function to get the Dealer's deck
     * @return the Deck 
     */
    public Deck getDeck(){
        return theDeck;
    }

    /**
     * A getter function to get the Dealer's hand
     * @return the Dealer's Hand
     */
    public ArrayList<Card> getDealerHand() { return dealersHand; }

    /**
     * A getter function to set the Dealer's hand
     * @param hand
     */
    public void setDealerHand(ArrayList<Card> hand) { this.dealersHand = hand; }
}
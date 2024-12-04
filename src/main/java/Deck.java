import java.util.ArrayList;
import java.util.Random;

public class Deck extends ArrayList<Card>{

    /**
     * A Default Deck constructor that randomizes a spread of 52 
     * playable cards as long as they are valid and have no repeats
     */
    public Deck(){ 
        //Initialize random variables
        Random rand = new Random();
        
        char[] suits = {'C', 'D', 'S', 'H'};

        while (this.size() < 52){
            //Continue to add cards to deck until max deck size is reached
            char randSuit = suits[rand.nextInt(suits.length)];
            int randValue = 2 + rand.nextInt(13);

            if (validCard(randSuit, randValue)){
                //Only adds a card that randomizer chooses as long as 
                //it isn't already in the deck
                this.add(new Card(randSuit, randValue));
            }
        }
    }   

    /**
     * A function that will "reshuffle" the deck when manually called for or the current size
     * of the deck is too small and needs to make a new set of cards in a different order
     * @return a new Deck of Cards
     */
    public Deck newDeck(){
        //Clears the remaining cards in the deck
        this.clear();

        Random rand = new Random();
        char[] suits = {'C', 'D', 'S', 'H'};

        while (this.size() < 52){
            //Continue to add cards to deck until max deck size is reached
            char randSuit = suits[rand.nextInt(suits.length)];
            int randValue = 2 + rand.nextInt(13);

            if (validCard(randSuit, randValue)){
                //Only adds a card that randomizer chooses as long as 
                //it isn't already in the deck
                this.add(new Card(randSuit, randValue));
            }
        }

        return this;
    }

    /**
     * Helper Functon for both Deck functions to verify if a card is already 
     * in the deck or not in order to avoid duplicate cards in the deck
     * @param suit is the instructed suit given by the randomizer
     * @param value is the instructed value given by the randomizer
     * @return a boolean if the card is valid or not
     */
    public final boolean validCard(char suit, int value){
        //Check if card already in deck

        for (Card card : this){
            if (card.getSuit() == suit && card.getValue() == value){
                //Condition passes if both the suit and value is in the function
                return false;
            }
        }

        return true;
    }
}

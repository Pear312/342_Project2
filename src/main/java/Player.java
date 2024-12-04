import java.lang.reflect.Array;
import java.util.ArrayList;

public class Player {
    
    private ArrayList<Card> hand;
    private int anteBet;
    private int playBet;
    private int pairPlusBet;
    private int totalWinnings;
 
    /*
     * A Default Constructor that initialzes all of the players assets
     * By default, player will always have an empty head and 0 bets and winnings
     */
    public Player(){
        hand = new ArrayList<>();

        anteBet = 0;
        playBet = 0;
        pairPlusBet = 0;
        totalWinnings = 0;
    }

    /**
     * A getter function to return the Player's current hand
     * @return Player's hand of cards
     */
    public ArrayList<Card> getHand(){
        return this.hand;
    }

    /**
     * A setter function for the Player's current hand
     * @param hand
     */
    public void setHand(ArrayList<Card> hand) { this.hand = hand;}

    /**
     * A Setter Function for the Player's current Ante Bet
     * @param anteBet
     */
    public void setAnteBet(int anteBet){
       this.anteBet = anteBet;
    }

    /**
     * A getter function to return the Player's current Ante Bet
     * @return Player's bet
     */
    public int getAnteBet(){
        return this.anteBet;
    }

    /**
     * A Setter Function for the Player's current Play Bet
     * @param playBet
     */
    public void setPlayBet(int playBet){
        this.playBet = playBet;
    }

    /**
     * A getter function to return the Player's current Play Bet
     * @return Player's bet
     */
    public int getPlayBet(){
        return this.playBet;
    }

    /**
     * A Setter Function for the Player's current Pair Plus Bet
     * @param pairPlusBet
     */
    public void setPairPlusBet(int pairPlusBet){
        this.pairPlusBet = pairPlusBet;
    }

    /**
     * A getter function to return the Player's current Pair Plus Bet
     * @return Player's bet
     */
    public int getPairPlusBet(){
        return this.pairPlusBet;
    }

    /**
     * A Setter Function for the Player's current Total Winnings
     * @param totalWinnings
     */
    public void setTotalWinnings(int totalWinnings){
        this.totalWinnings = totalWinnings;
    }

    /**
     * A getter function to return the Player's current overall Winnings
     * @return Player's Winnings
     */
    public int getTotalWinnings(){
        return this.totalWinnings;
    }
}
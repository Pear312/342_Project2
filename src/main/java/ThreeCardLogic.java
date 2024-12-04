import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ThreeCardLogic {

    /**
     * Helper Function to check if hand is a Straight Flush
     * @param hand
     * @return a boolean whether or not hand fits the combo
     */
    private static boolean isStraightFlush(ArrayList<Card> hand){ 
        return isStraight(hand) && isFlush(hand);
    }

     /**
     * Helper Function to check if hand is a Three
     * @param hand
     * @return a boolean whether or not hand fits the combo
     */
    private static boolean isThrees(ArrayList<Card> hand){ 
        //If at minimum, hand can't be a pair, then it can be three of a kind       
        if (!isPair(hand)){
            return false;
        }

        if (hand.get(0).getValue() == hand.get(1).getValue()){
            if (hand.get(1).getValue() == hand.get(2).getValue()){
                return true;
            }
        }

        //If at maximum the hand is a pair, the last card is not valid
        return false;
    }

    /**
     * Helper Function to check if hand is a Straight
     * @param hand
     * @return a boolean whether or not hand fits the combo
     */
    private static boolean isStraight(ArrayList<Card> hand){        
        //Hand needs to be sorted in order for isStraight to work properly
        int[] vals = {hand.get(0).getValue(), hand.get(1).getValue(), hand.get(2).getValue()};

        Arrays.sort(vals);

        if (vals[1] == vals[0] + 1 && vals[2] == vals[1] + 1){
            //Besides the first card, the second and third card must be one up of the previous
            return true;
        }

        return false;
    }

     /**
     * Helper Function to check if hand is a Flush
     * @aram hand
     * @return a boolean whether or not hand fits the combo
     */
    private static boolean isFlush(ArrayList<Card> hand){
        if (hand.get(0).getSuit() == hand.get(1).getSuit()){
            if  (hand.get(1).getSuit() == hand.get(2).getSuit()){
                //Suits must match from Card 1 to Card 3
                return true;
            }
        }
        return false;
    }
 
     /**
     * Helper Function to check if hand is a Pair
     * @param hand
     * @return a boolean whether or not hand fits the combo
     */
    private static boolean isPair(ArrayList<Card> hand){

        //Each card pair must be check to see if their values match
        if (hand.get(0).getValue() == hand.get(1).getValue()){
            return true;
        }

        if (hand.get(0).getValue() == hand.get(2).getValue()){
            return true;
        }

        if (hand.get(1).getValue() == hand.get(2).getValue()){
            return true;
        }
        
        return false;
    }
    

    /**
     * Eval Hand will check the specified hand for which combination they fit in from 
     * highest at 1 and lowest at 5. If no combo is maid, then it must be a high card which is 0
     * @param hand
     * @return a number from 0-5 depending on the highest combo of the hand
     */
    public static int evalHand(ArrayList<Card> hand){
        
        if (isStraightFlush(hand)){
            return 1;
        }
        
        if (isThrees(hand)){
            return 2;
        }
        
        if (isStraight(hand)){
            return 3;
        }
        
        if (isFlush(hand)){
            return 4;
        }
        
        if (isPair(hand)){
            return 5; 
        }
        
        return 6; //isHighCard
    }

    /**
     * Evaluates the Pair Plus Winnings and alters the bet size depending on
     * the scenaro of the round
     * @param hand
     * @param bet
     * @return a number the indicates their overall winnings for the round
     */
    public static int evalPPWinnings(ArrayList<Card> hand, int bet){
        int condition = evalHand(hand);
        switch(condition){
            case 1:
                return bet * 40;
            case 2:
                return bet * 30;
            case 3:
                return bet * 6;
            case 4:
                return bet * 3;
            case 5:
                return bet;
            default:
                return 0;
        }
    }

    /**
     * Compares the Dealer's and Player's hand to see which one is better overall
     * Priority: Highest Combo | If Tied: Highest Card Wins | Same High Card: Tie
     * @param dealer
     * @param player
     * @return a number ranging from 0-2 where 0 is a tie, 1 is Dealer win, and 2 is Player win
     */
    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player){
        int dealerHand = evalHand(dealer);
        int playerHand = evalHand(player);

        //The lower the number, the better the combination
        //Ex: 1 > 5 since Straight Flush is better than Pair

        if (dealerHand < playerHand){
            //Dealer had a better combination, dealer won
            return 1;
        }

        if (playerHand < dealerHand){
            //Player had a better combination, player won
            return 2;
        }

        if (dealerHand == playerHand){
            //Dealer and Player have the same combination
            int[] dVals = {dealer.get(0).getValue(), dealer.get(1).getValue(), dealer.get(2).getValue()};
            Arrays.sort(dVals);

            int[] pVals = {player.get(0).getValue(), player.get(1).getValue(), player.get(2).getValue()};
            Arrays.sort(pVals);

            //Sort Dealer and Player Hands and check whoever has the highest card wins
            //Sorting forces the highest value card to be at the end of the array
            if (dVals[2] > pVals[2]){
                //Dealer had higher card, dealer won
                return 1;
            }

            if (pVals[2] > dVals[2]){
                //Player had higher card, player won
                return 2;
            }

            if (dVals[2] == pVals[2]){
                //Neither Dealer or Player had higher card, therefore is a draw
                return 0;
            }
        }

        return 0;
    }


    /**
     * Function to check whether the dealer has a queen high or better
     * @param hand
     * @return a boolean if it has a queen high or better
     */
    public static boolean checkQueenHigh(ArrayList<Card> hand){
        boolean queenHigh = false;
        int isHighCard = evalHand(hand);
        if(isHighCard < 6){
            queenHigh = true;
        }
        else if(isHighCard == 6){
            for(Card card : hand){
                if(card.getValue() >= 12){
                    queenHigh = true;
                    break;
                }
            }
        }
        return queenHigh;
    }

    /**
     * Helper Function for UI that will assign each card in the hand with their associated
     * card image and will have it in an Array that contains 3 elements
     * @param hand
     * @return a String Array of Card Image file names
     */
    public static String[] getCardImages(ArrayList<Card> hand){
        String[] images = new String[hand.size()];
        //All file names will be "SuitValue.png"
        //For example: "2C.png" = 2 of Clubs

        for (int i = 0; i < hand.size(); i++){
            Card card = hand.get(i);
            char suit = card.getSuit();
            String imgName; 

            //Switch Case for Face Value cards
            switch(card.getValue()){
                case 11:
                    //Jack Case
                    imgName = "J" + suit + ".png";
                    images[i] = imgName;
                    break;
                case 12:
                    //Queen Case
                    imgName = "Q" + suit + ".png";
                    images[i] = imgName;
                    break;
                case 13:
                    //King Case
                    imgName = "K" + suit + ".png";
                    images[i] = imgName;
                    break;
                case 14:
                    //Ace Case
                    imgName = "A" + suit + ".png";
                    images[i] = imgName;
                    break;
                default:
                    //Default case for any non-face values from 2-10
                    int value = card.getValue();
                    imgName = String.valueOf(value) + suit + ".png";
                    images[i] = imgName;
                    break;
            }

        }
        return images;
    }

}
public class Card {
    private
        char suit; //Range: {'C', 'D', 'S', 'H'}
        int value; //Range: 2 - 14 (J: 11, Q: 12, K: 13, A: 14)
    
    /** 
     * Initializes a Card to be used for playing that is created with two values 
     * @param suit which indicates the char type of card 
     * @return value which indicates an integer of the card ranging from 2-14
    */    
    public Card(char suit, int value){
        setSuit(suit);
        setValue(value);
    }

    /**
     * A Setter Function to designate a given suit. Value is not case-sensitive 
     * @param suit - Can be upper or lower case as long as it is a valid char
     */
    public final void setSuit(char suit){
        char[] suits = {'C', 'D', 'S', 'H'};

        char upperSuit = Character.toUpperCase(suit);
        //Useful incase the suit inputted is lowercase

        for (char validSuit : suits){
            if (upperSuit == validSuit){
                //Only returns suit if it is valid in the array
                this.suit = upperSuit;
                return;
            }
        }
    }
    
    /**
     * A getter function for the suit private var
     * @return
     */
    public char getSuit(){
        return this.suit;
    }


    /**
     * A Setter Function to designate a given value. Must be within range
     * @param value
     */
    public final void setValue(int value){
        if (value < 2 || value > 14){
            //Value is initialized to 0 if nothing is returned
            return;
        }

        this.value = value;
    }
    
    /**
     * A getter function for the value private var
     * @return
     */
    public int getValue(){
        return this.value;
    }
}
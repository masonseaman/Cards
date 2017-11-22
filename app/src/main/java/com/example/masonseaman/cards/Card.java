package com.example.masonseaman.cards;

/**
 * Created by Mason Seaman on 11/20/17.
 */

public class Card {
    private int suit;
    private int value;

    //diamonds, spades, clubs, hearts
    public Card(int suit, int value){
        this.suit = suit;
        this.value = value;
    }

    public int getSuit(Card card){
        return card.suit;
    }

    public int getValue(Card card){
        return card.value;
    }

    public String toString(){
        switch (suit){
            case 0:
                if(value==1)
                    return "AD";
                else if(value==11)
                    return "JD";
                else if(value==12)
                    return "QD";
                else if(value==13)
                    return "KD";
                else return value + "D";
            case 1:
                if(value==1)
                    return "AS";
                else if(value==11)
                    return "JS";
                else if(value==12)
                    return "QS";
                else if(value==13)
                    return "KS";
                else return value + "S";
            case 2:
                if(value==1)
                    return "AC";
                else if(value==11)
                    return "JC";
                else if(value==12)
                    return "QC";
                else if(value==13)
                    return "KC";
                else return value + "C";
            case 3:
                if(value==1)
                    return "AH";
                else if(value==11)
                    return "JH";
                else if(value==12)
                    return "QH";
                else if(value==13)
                    return "KH";
                else return value + "H";
        }
        return "err";
    }
}

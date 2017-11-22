package com.example.masonseaman.cards;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Mason Seaman on 11/20/17.
 */

public class Deck {
    ArrayList<Integer> deck = new ArrayList<>(52);

    //call createDeck
    public Deck(){
        createDeck();
    }


    //shuffle array
    public void shuffle(){
        Collections.shuffle(deck);
    }


    //pop card from beginning of list
    public int dealOneCard(){
        if(deck.isEmpty()){
            return -1;
        }else{
            return deck.remove(0);
        }
    }

    //clear the deck and make a new one
    public void resetDeck(){
        deck.clear();
        createDeck();
    }


    //method to make deck
    public void createDeck(){
        for(int i = 0; i<52; i++){
            deck.add(i);
        }
    }

}

package com.example.masonseaman.cards;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedpreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        final ArrayList<String> activityFeed = new ArrayList<>();

        setContentView(R.layout.activity_main);

        final Map<Integer,Card> deckMap;

        //check if app has been run, if so build a deck of cards and save in shared preferences
        if(!sharedpreferences.contains("run")){

            //map to store the generic deck of cards in
            deckMap = new HashMap<>();

            //use this for key of each card to lookup when returned to user
            int index = 0;

            //loop through suits
            for(int i = 0; i<4; i++){
                //loop through values
                for(int j = 1; j<14; j++){
                    deckMap.put(index, new Card(i,j));
                    index++;
                }
            }

            //convert map to string so it can be added to
            String hashMapString = gson.toJson(deckMap);

            SharedPreferences.Editor editor = sharedpreferences.edit();

            //put string to show that app has been run and deck of cards has been created in prefs
            editor.putString("run", "true");
            //put string of deck in prefs
            editor.putString("deck", hashMapString);
            editor.commit();
        }else{
            deckMap = getHashmapFromPrefs();
        }

        final Button shuffle =  findViewById(R.id.shuffle);
        final Button deal = findViewById(R.id.deal);
        final Button reset =  findViewById(R.id.reset);
        final TextView cardCounterView = findViewById(R.id.cardCounter);
        final ImageView cardView = findViewById(R.id.cardImage);

        final Deck deck = new Deck();
        final int[] cardCounter = {52};
        cardCounterView.setText("Cards Remaining: " + cardCounter[0]);

        //show activity feed
        final ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activityFeed);
        final ListView activityFeedView = (ListView) findViewById(R.id.activityFeed);
        activityFeedView.setAdapter(itemsAdapter);
        activityFeedView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cardView.setImageResource(getResources().getIdentifier("_" + activityFeedView.getItemAtPosition(i).toString().toLowerCase(), "drawable", getPackageName()));
                cardView.invalidate();
            }
        });

        //call shuffle on click
        shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deck.shuffle();
                activityFeed.add("Deck Shuffled");
                itemsAdapter.notifyDataSetChanged();
            }
        });

        //call deal on click
        deal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int cardDealt = deck.dealOneCard();
                //check if return is -1 which means the deck has been all dealt
                if(cardDealt==-1){
                    //only post message once
                    if(!activityFeed.get(activityFeed.size()-1).equals("Entire deck has been dealt")) {
                        activityFeed.add("Entire deck has been dealt");
                        itemsAdapter.notifyDataSetChanged();
                    }
                }else{
                    //show dealt card in activity feed and show image
                    Card dealt = deckMap.get(cardDealt);
                    activityFeed.add(dealt.toString());
                    itemsAdapter.notifyDataSetChanged();
                    cardCounter[0]--;
                    cardCounterView.setText("Cards Remaining: " + cardCounter[0]);
                    cardView.setImageResource(getResources().getIdentifier("_" + dealt.toString().toLowerCase(), "drawable", getPackageName()));
                    cardView.invalidate();
                }
            }
        });

        //reset deck of cards
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deck.resetDeck();

                //clear activity feed
                activityFeed.clear();
                activityFeed.add("Deck reset");

                itemsAdapter.notifyDataSetChanged();
                cardCounter[0]=52;
                cardCounterView.setText("Cards Remaining: " + cardCounter[0]);
                cardView.setImageDrawable(null);
            }
        });
    }

    //extracts hashmap from sharedpreferenece
    private HashMap<Integer,Card> getHashmapFromPrefs(){
        SharedPreferences sharedpreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        String storedHashMapString = sharedpreferences.getString("deck","error, no deck has been created");
        java.lang.reflect.Type type = new TypeToken<HashMap<Integer, Card>>(){}.getType();
        return gson.fromJson(storedHashMapString, type);
    }
}

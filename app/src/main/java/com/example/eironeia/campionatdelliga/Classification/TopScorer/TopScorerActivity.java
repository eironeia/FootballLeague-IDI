package com.example.eironeia.campionatdelliga.Classification.TopScorer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.eironeia.campionatdelliga.DB.PlayerDB;
import com.example.eironeia.campionatdelliga.R;

import java.util.List;

/**
 * Created by Eironeia on 24/5/16.
 */
public class TopScorerActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clasification_goals);

        updateList();
    }

    public void updateList(){
        List<PlayerDB> arrayOfPlayer = PlayerDB.findWithQuery(PlayerDB.class, "SELECT * FROM PLAYER_DB ORDER BY goals DESC");

        // Create the adapter to convert the array to views
        CustomListTopScorer adapter = new CustomListTopScorer(this, arrayOfPlayer);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listClasificationGoals);
        listView.setAdapter(adapter);
    }


}

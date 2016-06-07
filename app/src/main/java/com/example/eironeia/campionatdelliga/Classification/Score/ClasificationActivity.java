package com.example.eironeia.campionatdelliga.Classification.Score;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.eironeia.campionatdelliga.R;
import com.example.eironeia.campionatdelliga.DB.TeamDB;

import java.util.List;

/**
 * Created by Eironeia on 24/5/16.
 */
public class ClasificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clasification);
        updateClasificationList();
    }

    private void updateClasificationList(){
        // Construct the data source
        List<TeamDB> arrayOfTeam = TeamDB.findWithQuery(TeamDB.class, "SELECT * FROM TEAM_DB ORDER BY score DESC");

        // Create the adapter to convert the array to views
        CustomClasificationList adapter = new CustomClasificationList(this, arrayOfTeam);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listClasification);
        listView.setAdapter(adapter);
    }
}

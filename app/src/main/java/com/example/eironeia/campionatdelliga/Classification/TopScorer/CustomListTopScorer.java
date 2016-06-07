package com.example.eironeia.campionatdelliga.Classification.TopScorer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eironeia.campionatdelliga.DB.PlayerDB;
import com.example.eironeia.campionatdelliga.R;

import java.util.List;

/**
 * Created by Eironeia on 24/5/16.
 */
public class CustomListTopScorer extends ArrayAdapter<PlayerDB> {
    public CustomListTopScorer(Context context, List<PlayerDB> player) {
        super(context, 0, player);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PlayerDB player = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_clasification_goals, parent, false);
        }
        // Lookup view for data population
        TextView namePlayer = (TextView) convertView.findViewById(R.id.namePlayer);
        TextView goalsOfPlayer = (TextView) convertView.findViewById(R.id.numberOfGoals);
        // Populate the data into the template view using the data object
        namePlayer.setText(player.getName());
        goalsOfPlayer.setText("Goals: "+player.getGoals());
        // Return the completed view to render on screen
        return convertView;
    }
}

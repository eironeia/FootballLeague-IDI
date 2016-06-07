package com.example.eironeia.campionatdelliga.Jornada.Goals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eironeia.campionatdelliga.R;

import java.util.List;

/**
 * Created by Eironeia on 19/5/16.
 */
public class CustomListJornadaGoals extends ArrayAdapter<String> {

    public CustomListJornadaGoals(Context context, List<String> gameGoals) {
        super(context, 0, gameGoals);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String gameGoalsText = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_player_goals, parent, false);
        }
        // Lookup view for data population
        TextView textOnListView = (TextView) convertView.findViewById(R.id.textOnListViewOnJornadaGoalsPlayers);
        // Populate the data into the template view using the data object
        textOnListView.setText(gameGoalsText);
        // Return the completed view to render on screen
        return convertView;
    }
}

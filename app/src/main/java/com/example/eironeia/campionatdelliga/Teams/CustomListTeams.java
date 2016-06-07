package com.example.eironeia.campionatdelliga.Teams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eironeia.campionatdelliga.DB.TeamDB;
import com.example.eironeia.campionatdelliga.R;

import java.util.List;

/**
 * Created by Eironeia on 15/5/16.
 */
public class CustomListTeams extends ArrayAdapter<TeamDB> {
    public CustomListTeams(Context context, List<TeamDB> teams) {
        super(context, 0, teams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TeamDB team = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_team, parent, false);
        }

        TextView nameTeam = (TextView) convertView.findViewById(R.id.nameTeamOnCustomListTeam);

        // Populate the data into the template view using the data object
        nameTeam.setText(team.getName());

        // Return the completed view to render on screen
        return convertView;
    }
}

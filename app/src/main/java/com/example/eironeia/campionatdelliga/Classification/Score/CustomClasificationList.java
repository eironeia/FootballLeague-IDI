package com.example.eironeia.campionatdelliga.Classification.Score;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.eironeia.campionatdelliga.R;
import com.example.eironeia.campionatdelliga.DB.TeamDB;

import java.util.List;

/**
 * Created by Eironeia on 24/5/16.
 */
public class CustomClasificationList extends ArrayAdapter<TeamDB> {
    public CustomClasificationList(Context context, List<TeamDB> teams) {
        super(context, 0, teams);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TeamDB team = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_clasification_list, parent, false);
        }

        TextView posList = (TextView) convertView.findViewById(R.id.positionClasificationNumber);
        TextView nameTeam = (TextView) convertView.findViewById(R.id.nameTeamClasification);
        TextView numberPoints = (TextView) convertView.findViewById(R.id.pointsNumber);
        TextView numberWons = (TextView) convertView.findViewById(R.id.winsNumber);
        TextView numberTieds = (TextView) convertView.findViewById(R.id.tiedNumber);
        TextView numberLosts = (TextView) convertView.findViewById(R.id.lostNumber);
        // Populate the data into the template view using the data object
        nameTeam.setText(team.getName());
        numberPoints.setText(Long.toString(team.getScore()));
        numberWons.setText(Long.toString(team.getVictories()));
        numberTieds.setText(Long.toString(team.getDraws()));
        numberLosts.setText(Long.toString(team.getDefeats()));
        posList.setText(Integer.toString(position+1));


        // Return the completed view to render on screen
        return convertView;
    }
}

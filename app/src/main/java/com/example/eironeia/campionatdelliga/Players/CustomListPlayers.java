package com.example.eironeia.campionatdelliga.Players;

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
 * Created by Eironeia on 15/5/16.
 */
public class CustomListPlayers  extends ArrayAdapter<PlayerDB> {
    public CustomListPlayers(Context context, List<PlayerDB> player) {
        super(context, 0, player);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PlayerDB player = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_list_player, parent, false);
        }
        // Lookup view for data population
        TextView namePlayer = (TextView) convertView.findViewById(R.id.namePlayer);
        // Populate the data into the template view using the data object
        namePlayer.setText(player.getName());
        // Return the completed view to render on screen
        return convertView;
    }
}

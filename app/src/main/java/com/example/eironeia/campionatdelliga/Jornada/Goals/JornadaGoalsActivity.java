package com.example.eironeia.campionatdelliga.Jornada.Goals;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eironeia.campionatdelliga.DB.GoalsDB;
import com.example.eironeia.campionatdelliga.DB.JornadaDB;
import com.example.eironeia.campionatdelliga.DB.PlayerDB;
import com.example.eironeia.campionatdelliga.R;
import com.example.eironeia.campionatdelliga.DB.TeamDB;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eironeia on 18/5/16.
 */
public class JornadaGoalsActivity extends AppCompatActivity {

    long jornadaActual, numOfMatches;
    long localGoals = 0;
    long visitantGoals = 0;
    String localTeam;
    String visitantTeam;


    Dialog dialog;
    long numberOfGoals;
    Button buttonInc, buttonDec;
    TextView numOfGoals;

    Spinner spinnerTeams, spinnerPlayers;

    List<String> teamLocalOrVisitant = new ArrayList<>();
    List<String> playersNameLocalOrVisitant = new ArrayList<>();
    List<Long> goalsOfPlayers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jornada_goals);

        receiveData();
    }

    public void receiveData(){
        Bundle bundle = getIntent().getExtras();
        localTeam = bundle.getString("localTeamName");
        visitantTeam = bundle.getString("visitantTeamName");
        jornadaActual = bundle.getLong("JornadaNumber");
        numOfMatches = bundle.getLong("numeroDePartits");
    }
    public void loadSpinnerTeamsOnDialog(){
        List<String> teams = new ArrayList<>();
        teams.add(localTeam);
        teams.add(visitantTeam);

        spinnerTeams = (Spinner) dialog.findViewById(R.id.spinnerOfTeamsOnJornadaGoals);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeams.setAdapter(spinnerAdapter);
        for (int i = 0; i < teams.size() ; ++i) {
            spinnerAdapter.add(teams.get(i));
        }
        spinnerAdapter.notifyDataSetChanged();
    }
    public void loadSpinnerPlayersOnDialog(String team){
        List <PlayerDB> players = PlayerDB.findWithQuery(PlayerDB.class,"SELECT * FROM PLAYER_DB WHERE team = ?", team);
        spinnerPlayers = (Spinner) dialog.findViewById(R.id.spinnerOfPlayersOnJornadaGoals);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlayers.setAdapter(spinnerAdapter);
        for (int i = 0; i < players.size() ; ++i) {
            spinnerAdapter.add(players.get(i).getName());
        }
        spinnerAdapter.notifyDataSetChanged();
    }

    public void configureButtonsOfDialog(){
        buttonInc = (Button) dialog.findViewById(R.id.button1);
        buttonDec = (Button) dialog.findViewById(R.id.button2);
        numOfGoals = (TextView) dialog.findViewById(R.id.txt);

        buttonInc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOfGoals++;
                numOfGoals.setText(String.valueOf(numberOfGoals));

            }
        });

        buttonDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfGoals > 0) {
                    numberOfGoals--;
                    numOfGoals.setText(String.valueOf(numberOfGoals));
                }
            }
        });
    }
    public void configureSpinnerClicked(){

        spinnerTeams.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                loadSpinnerPlayersOnDialog(spinnerTeams.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void onAddGoalsPressed(View view){

        dialog = new Dialog(this);
        dialog.setTitle("Goals");
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog_goals);
        dialog.show();

        loadSpinnerTeamsOnDialog();
        loadSpinnerPlayersOnDialog(localTeam);
        configureSpinnerClicked();
        configureButtonsOfDialog();
        //loadAttributes(); futura oprimitzacio d'afegir els gols de cada equip
    }
    public void onAddPlayerGoalsPressed(View view){
        saveInLocal();
        loadOnListViewGoalPlayers();
        modifyPuntuation();
        numberOfGoals = 0;
        numOfGoals.setText(""+numberOfGoals);

    }
    public void saveInLocal(){
        teamLocalOrVisitant.add(spinnerTeams.getSelectedItem().toString());
        playersNameLocalOrVisitant.add(spinnerPlayers.getSelectedItem().toString());
        goalsOfPlayers.add(Long.parseLong(numOfGoals.getText().toString()));
    }
    public void loadOnListViewGoalPlayers(){

        // Construct the data source
        List<String> gameGoals  = new ArrayList<>();

        for (int i = 0; i < teamLocalOrVisitant.size(); ++i){
            gameGoals.add(teamLocalOrVisitant.get(i) + "   " + playersNameLocalOrVisitant.get(i) + "   Goals: " + goalsOfPlayers.get(i));
        }

        // Create the adapter to convert the array to views
        CustomListJornadaGoals adapter = new CustomListJornadaGoals(this, gameGoals);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listOfPlayersScoreOnJornada);
        listView.setAdapter(adapter);

    }
    public void modifyPuntuation(){
        long goalsScored = goalsOfPlayers.get(goalsOfPlayers.size() - 1);
        String teamGoals = teamLocalOrVisitant.get(teamLocalOrVisitant.size() - 1);
        if (teamGoals == localTeam) localGoals += goalsScored;
        else visitantGoals += goalsScored;
        Toast.makeText(this,playersNameLocalOrVisitant.get(playersNameLocalOrVisitant.size()-1)+" has score "+goalsScored+" goals",Toast.LENGTH_SHORT).show();

        TextView puntuation = (TextView) findViewById(R.id.puntuation);
        puntuation.setText(localGoals+"  -  "+visitantGoals);
    }
    public void onAddMatchPressed(View view){
        savePlayerDB();
        saveOnTeamDB();
        saveJornadaDB();
        saveGoalsDB();
        finish();
    }

    public void savePlayerDB(){
        for (int i = 0; i <  playersNameLocalOrVisitant.size(); ++i) {
            PlayerDB player = PlayerDB.findWithQuery(PlayerDB.class, "SELECT * FROM PLAYER_DB WHERE name = ?", playersNameLocalOrVisitant.get(i)).get(0);
            player.incrementGoals(goalsOfPlayers.get(i));
            PlayerDB.executeQuery("UPDATE PLAYER_DB SET goals = ? WHERE name = ?", player.getGoals() + "", player.getName());
        }
    }
    public void saveOnTeamDB(){
        TeamDB teamLocal = TeamDB.findWithQuery(TeamDB.class, "SELECT * FROM TEAM_DB WHERE name = ?",localTeam).get(0);
        TeamDB teamVisitant = TeamDB.findWithQuery(TeamDB.class, "SELECT * FROM TEAM_DB WHERE name = ?",visitantTeam).get(0);
        if (localGoals > visitantGoals) {
            teamLocal.incrementVictories();
            teamVisitant.incrementDefeats();
        }
        else if (localGoals < visitantGoals) {
            teamLocal.incrementDefeats();
            teamVisitant.incrementVictories();
        }
        else {
            teamLocal.incrementDraws();
            teamVisitant.incrementDraws();
        }

        teamLocal.save();
        teamVisitant.save();
    }
    public void saveJornadaDB() {
        JornadaDB jornada = new JornadaDB();
        jornada.setNumJornada(jornadaActual);
        jornada.setLocalTeam(localTeam);
        jornada.setVisitantTeam(visitantTeam);
        jornada.setLocalsGoals(localGoals);
        jornada.setVisitantGoals(visitantGoals);
        jornada.save();

    }
    public void saveGoalsDB(){
        for (int i = 0; i < playersNameLocalOrVisitant.size(); ++i){
            GoalsDB goals = new GoalsDB(jornadaActual,numOfMatches,teamLocalOrVisitant.get(i),playersNameLocalOrVisitant.get(i),goalsOfPlayers.get(i));
            goals.save();
        }

    }

}

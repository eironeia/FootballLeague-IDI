package com.example.eironeia.campionatdelliga.Players;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eironeia.campionatdelliga.DB.PlayerDB;
import com.example.eironeia.campionatdelliga.R;
import com.example.eironeia.campionatdelliga.DB.TeamDB;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.List;

/**
 * Created by Eironeia on 14/5/16.
 */
public class PlayersActivity extends AppCompatActivity implements View.OnClickListener{


    private ShowcaseView showcaseView;
    private Target t1,t2,t3;
    private int whichShowCase = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.players);


        uploadTeamsOnSpinner();
        configureSpinner();
        prepareHelp();

    }

    /** Button Pressed **/
    public void onAddPressed (View view){
        addPlayer();
    }

    /** Action Button **/
    private void addPlayer(){
        EditText playerNameEditText = (EditText) findViewById(R.id.nameNewPlayerEditText);
        Spinner listOfTeams = (Spinner) findViewById(R.id.spinnerOfTeamsOnPlayers);
        if (listOfTeams.getCount() == 0) { openDialogMissingTeams(); playerNameEditText.setText(""); }
        else {
            String playerName = playerNameEditText.getText().toString();
            String teamName = listOfTeams.getSelectedItem().toString();
            checkIfCanAdd(playerName, teamName);
            playerNameEditText.setText("");
        }
    }
    private void checkIfCanAdd(String playerName, String teamName){
        if (playerName.isEmpty()) openDialogMissAttributes();
        else if (existsPlayer(playerName)) openDialogPlayerAlreadyExists();
        else if (isTeamFull(teamName)) openDialogFullTeam();
        else addToPlayerDB(playerName,0,teamName);
    }
    private void addToPlayerDB(String playerName, long goals, String teamName){
        PlayerDB playerDB = new PlayerDB(playerName,goals,teamName);
        playerDB.save();
        Toast.makeText(this, "Player " + playerDB.getName() + " added", Toast.LENGTH_SHORT).show();
    }

    /** Requirements **/
    private Boolean existsPlayer(String playerName){
        List<PlayerDB> listOfPlayers = PlayerDB.findWithQuery(PlayerDB.class,"Select name FROM PLAYER_DB where name = ?", playerName);
        if (listOfPlayers.size() > 0) {openDialogPlayerAlreadyExists(); return true;}
        else return false;
    }
    private Boolean isTeamFull(String teamName){
        List<PlayerDB> playerL = PlayerDB.findWithQuery(PlayerDB.class,"SELECT * FROM PLAYER_DB WHERE team = ?", teamName);
        if (playerL.size() >= 12) return true;
        return false;
    }

    /** Dialogs **/
    private void openDialogMissAttributes(){
        new AlertDialog.Builder(this)
                .setTitle("Error adding")
                .setMessage("You must have to put a name of the Player and select in which team he plays")
                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void openDialogPlayerAlreadyExists(){
        new AlertDialog.Builder(this)
                .setTitle("Error adding")
                .setMessage("Already exists a player whith this name ")
                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void openDialogFullTeam(){
        new AlertDialog.Builder(this)
                .setTitle("Error adding")
                .setMessage("The team is full")
                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void openDialogMissingTeams(){
        new AlertDialog.Builder(this)
                .setTitle("Error adding")
                .setMessage("You have to add at least 1 team")
                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /** Spinner and List **/
    public void configureSpinner(){
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerOfTeamsOnPlayers);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                uploadListPlayers(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    private void uploadTeamsOnSpinner(){
        List<TeamDB> l = TeamDB.listAll(TeamDB.class);

        Spinner spinner = (Spinner)findViewById(R.id.spinnerOfTeamsOnPlayers);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        for (int i = 0; i < l.size() ; ++i) {
            spinnerAdapter.add((l.get(i)).getName());
        }
        spinnerAdapter.notifyDataSetChanged();
    }
    private void uploadListPlayers(String teamPlayers){
        // Construct the data source
        List<PlayerDB> arrayOfPlayerOnTeam = PlayerDB.findWithQuery(PlayerDB.class, "Select * from PLAYER_DB WHERE team = ? ORDER BY name",teamPlayers);

        // Create the adapter to convert the array to views
        CustomListPlayers adapter = new CustomListPlayers(this, arrayOfPlayerOnTeam);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listOfPlayersOnPlayers);
        listView.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Button btm = (Button) findViewById(R.id.teamsButton);
        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            showcaseView = new ShowcaseView.Builder(this)
                    .setTarget(new ViewTarget(R.id.nameNewPlayerEditText,this))
                    .setContentTitle("Name Player")
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentText("Here you have to write the name of your new player")
                    .hideOnTouchOutside()
                    .setOnClickListener(this)
                    .build();
            showcaseView.setButtonText("Next");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void prepareHelp(){
        t1 = new ViewTarget(R.id.spinnerOfTeamsOnPlayers,this);
        t2 = new ViewTarget(R.id.listOfPlayersOnPlayers,this);
        t3 = new ViewTarget(R.id.addNewPlayer,this);
    }

    @Override
    public void onClick(View v){
        switch (whichShowCase){
            case 1:
                showcaseView.setShowcase(t1,true);
                showcaseView.setContentTitle("Selected team");
                showcaseView.setContentText("This will be the team that player will plays");
                break;
            case 2:
                showcaseView.setShowcase(t2,true);
                showcaseView.setContentTitle("List of teams");
                showcaseView.setContentText("These are the teams that you have on Database");
                break;
            case 3:
                showcaseView.setShowcase(t3, true);

                showcaseView.setContentTitle("Add player");
                showcaseView.setContentText("Add a new Player on Database");
                break;
            case 4:

                whichShowCase = 0;
                showcaseView.hide();
                break;

        }
        whichShowCase++;
    }
}

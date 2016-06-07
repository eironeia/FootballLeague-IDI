package com.example.eironeia.campionatdelliga.Teams;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.eironeia.campionatdelliga.DB.TeamDB;
import com.example.eironeia.campionatdelliga.R;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.List;

/**
 * Created by Eironeia on 15/5/16.
 */
public class TeamActivity extends AppCompatActivity implements View.OnClickListener{
    private ShowcaseView showcaseView;
    private Target t1,t2,t3;
    private int whichShowCase = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teams);

        populateTeamList();
        prepareHelp();
    }


    /** Buttons pressed **/
    public void onAddTeamPressed(View view){
        checkIfCanAdd();
    }
    public void onAddTeamShield(View view){

    }

    /** Buttons Action **/
    private void checkIfCanAdd(){
        EditText nameOfTeam = (EditText) findViewById(R.id.nameNewTeamEditText);
        EditText nameOfCity = (EditText) findViewById(R.id.nameCityEditText);
        checkRequirements(nameOfTeam.getText().toString(), nameOfCity.getText().toString());

    }
    private void checkRequirements(String nameOfTeam, String nameOfCity){
        List<TeamDB> teamsWithThisName = TeamDB.find(TeamDB.class, "name = ?", nameOfTeam);
        List<TeamDB> allTeams = TeamDB.findWithQuery(TeamDB.class, "Select * from TEAM_DB");
        if (nameOfCity.isEmpty() || nameOfTeam.isEmpty()) openDialogMissAttributes();
        else if (teamsWithThisName.size() > 0) openDialogTeamAlreadyExists();
        else if (allTeams.size() >= 10) openDialogFullTeam();
        else {
            addToTeamDB(nameOfTeam,nameOfCity);
        }
    }
    private void addToTeamDB(String nameOfTeam, String nameOfCity){
        TeamDB newTeam = new TeamDB(nameOfTeam,nameOfCity,0,0,0,0);
        newTeam.save();
        Toast.makeText(this, "Team " + newTeam.getName() + " added", Toast.LENGTH_SHORT).show();
        populateTeamList();
        cleanFields();
    }


    /** Dialogs **/
    private void openDialogMissAttributes(){
        new AlertDialog.Builder(this)
                .setTitle("Error adding")
                .setMessage("You must have to put a name of the Team and in which city it is")
                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void openDialogTeamAlreadyExists(){
        new AlertDialog.Builder(this)
                .setTitle("Error adding")
                .setMessage("Already exists a Team with this name ")
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
                .setMessage("The number max of teams is 10")
                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /** Auxiliar Functions **/
    private void cleanFields(){
        EditText nameOfTeam = (EditText) findViewById(R.id.nameNewTeamEditText);
        EditText nameOfCity = (EditText) findViewById(R.id.nameCityEditText);
        nameOfCity.setText("");
        nameOfTeam.setText("");
    }
    private void populateTeamList() {
        // Construct the data source
        List<TeamDB> arrayOfTeams = TeamDB.findWithQuery(TeamDB.class, "Select * from TEAM_DB ORDER BY name");

        // Create the adapter to convert the array to views
        CustomListTeams adapter = new CustomListTeams(this, arrayOfTeams);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listOfTeamsOnTeams);
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
                    .setTarget(new ViewTarget(R.id.nameNewTeamEditText,this))
                    .setContentTitle("Name team")
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentText("Here you have to write the name of your new team")
                    .hideOnTouchOutside()
                    .setOnClickListener(this)
                    .build();
            showcaseView.setButtonText("Next");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void prepareHelp(){
        t1 = new ViewTarget(R.id.nameCityEditText,this);
        t2 = new ViewTarget(R.id.listOfTeamsOnTeams,this);
        t3 = new ViewTarget(R.id.addNewTeam,this);
    }

    @Override
    public void onClick(View v){
        switch (whichShowCase){
            case 1:
                showcaseView.setShowcase(t1,true);
                showcaseView.setContentTitle("City Name");
                showcaseView.setContentText("Here you have to write the city name of your new team");
                break;
            case 2:
                showcaseView.setShowcase(t2,true);
                showcaseView.setContentTitle("List of teams");
                showcaseView.setContentText("These are the teams that you have on Database");
                break;
            case 3:
                showcaseView.setShowcase(t3, true);

                showcaseView.setContentTitle("Add team");
                showcaseView.setContentText("Add a new Team on Database");
                break;
            case 4:

                whichShowCase = 0;
                showcaseView.hide();
                break;

        }
        whichShowCase++;
    }
}

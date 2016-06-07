package com.example.eironeia.campionatdelliga.Teams;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eironeia.campionatdelliga.DB.PlayerDB;
import com.example.eironeia.campionatdelliga.DB.TeamDB;
import com.example.eironeia.campionatdelliga.R;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.List;

/**
 * Created by Eironeia on 24/5/16.
 */
public class DescendTeamActivity  extends AppCompatActivity implements View.OnClickListener {

    private ShowcaseView showcaseView;
    private Target t1,t2,t3;
    private int whichShowCase = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descend_team);

        uploadTeamsOnSpinner();

        prepareHelp();
    }

    private void uploadTeamsOnSpinner(){
        List<TeamDB> l = TeamDB.findWithQuery(TeamDB.class, "Select * from team_db ORDER BY name");

        Spinner spinner = (Spinner)findViewById(R.id.spinnerTeamsDescend);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        for (int i = 0; i < l.size() ; ++i) {
            spinnerAdapter.add((l.get(i)).getName());
        }
        spinnerAdapter.notifyDataSetChanged();
    }

    public void onAscendTeam(View view){

        Spinner spinnerTeamsDescends = (Spinner) findViewById(R.id.spinnerTeamsDescend);
        EditText nameOfTeam = (EditText) findViewById(R.id.nameTeamAscend);
        EditText nameOfCity = (EditText) findViewById(R.id.nameCityAscend);
        List<TeamDB> teams = TeamDB.find(TeamDB .class, "name = ?", nameOfTeam.getText().toString());
        if (nameOfCity.getText().toString().isEmpty() || nameOfTeam.getText().toString().isEmpty()) openDialogEmptyFields();
        else if (teams.size() > 0) openDialogAlreadyExists();
        else {
            TeamDB team = TeamDB.find(TeamDB.class, "name = ?", spinnerTeamsDescends.getSelectedItem().toString()).get(0);
            team.delete();
            TeamDB team2 = new TeamDB(nameOfTeam.getText().toString(),nameOfCity.getText().toString(),0,0,0,0);
            team2.setName(nameOfTeam.getText().toString());
            team2.setCity(nameOfCity.getText().toString());
            team2.save();
            List<PlayerDB> players = PlayerDB.find(PlayerDB.class, "team = ?", nameOfTeam.getText().toString());
            for (int i = 0; i < players.size(); ++i) players.get(i).delete();
            Toast.makeText(this, "Team ascended: " + nameOfTeam.getText().toString()
                    + "\nTeam descended: " + spinnerTeamsDescends.getSelectedItem().toString()
                    , Toast.LENGTH_LONG).show();
            cleanFields();
        }

    }

    private void openDialogAlreadyExists(){
        new AlertDialog.Builder(this)
                .setTitle("Team exists")
                .setMessage("This team already exists")
                .setNeutralButton("Try other team", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void openDialogEmptyFields(){
        new AlertDialog.Builder(this)
                .setTitle("Empty Fields")
                .setMessage("You must have to complete all the fields")
                .setNeutralButton("Okay, I understand", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    private void cleanFields(){
        EditText nameOfTeam = (EditText) findViewById(R.id.nameTeamAscend);
        EditText nameOfCity = (EditText) findViewById(R.id.nameCityAscend);
        nameOfCity.setText("");
        nameOfTeam.setText("");
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
                    .setTarget(new ViewTarget(R.id.spinnerTeamsDescend,this))
                    .setContentTitle("Team descend")
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentText("Select a team that you would like to descend and delete")
                    .hideOnTouchOutside()
                    .setOnClickListener(this)
                    .build();
            showcaseView.setButtonText("Next");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void prepareHelp(){
        t1 = new ViewTarget(R.id.nameTeamAscend,this);
    }

    @Override
    public void onClick(View v){
        switch (whichShowCase){
            case 1:
                showcaseView.setShowcase(t1,true);
                showcaseView.setContentTitle("Ascend team");
                showcaseView.setContentText("This will be the new team with the written city");
                break;
            case 2:

                whichShowCase = 0;
                showcaseView.hide();
                break;

        }
        whichShowCase++;
    }

}

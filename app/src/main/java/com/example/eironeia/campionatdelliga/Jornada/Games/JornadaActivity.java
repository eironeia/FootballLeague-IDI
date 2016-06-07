package com.example.eironeia.campionatdelliga.Jornada.Games;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.eironeia.campionatdelliga.DB.JornadaDB;
import com.example.eironeia.campionatdelliga.DB.GoalsDB;
import com.example.eironeia.campionatdelliga.Jornada.Goals.JornadaGoalsActivity;
import com.example.eironeia.campionatdelliga.R;
import com.example.eironeia.campionatdelliga.DB.TeamDB;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eironeia on 16/5/16.
 */
public class JornadaActivity extends AppCompatActivity implements View.OnClickListener{

    long jornadaActual;
    long numeroDePartits;

    private ShowcaseView showcaseView;
    private Target t1,t2,t3;
    private int whichShowCase = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jornada);
        prepareHelp();
    }

    public void onResume() {
        super.onResume();  // Always call the superclass method first

        loadAttributes();
    }

    public void loadAttributes(){
        loadJornada();
        loadGamesPlayed();
        loadSpinners();
    }

    public void loadJornada(){
        TextView jornadaLabel = (TextView) findViewById(R.id.jornadaLabel);
        List<JornadaDB> partits = JornadaDB.listAll(JornadaDB.class);
        long partitsJugats = partits.size();

        jornadaActual = 0;
        while (partitsJugats > 0){
            partitsJugats -= 5;
            jornadaActual += 1;
        }
        if (partitsJugats == 0) jornadaActual += 1;

        jornadaLabel.setText("Round " + jornadaActual);
    }


    public void loadGamesPlayed(){
        List<JornadaDB> partits = JornadaDB.findWithQuery(JornadaDB.class, "SELECT * FROM JORNADA_DB WHERE NUM_JORNADA = ?", "" + jornadaActual);
        numeroDePartits = partits.size();
        TextView partitsLabel = (TextView) findViewById(R.id.partitsLabel);
        partitsLabel.setText("Game " + numeroDePartits + "/5");
    }
    public void loadSpinners(){
        List<TeamDB> teams = TeamDB.listAll(TeamDB.class);
        List<JornadaDB> jornadaDb = GoalsDB.findWithQuery(JornadaDB.class, "SELECT * FROM JORNADA_DB WHERE num_jornada = ?", "" + jornadaActual);

        List<TeamDB> teams2 = new ArrayList<>();
        for(int i = 0; i < teams.size(); ++i){
            String teamName = teams.get(i).getName();
            boolean delete = false;
            for (int j = 0; j < jornadaDb.size() && !delete; ++j){
                if (teamName.equals(jornadaDb.get(j).getLocalTeam()) || teamName.equals(jornadaDb.get(j).getVisitantTeam())){
                    delete = true;
                }
            }
            if (delete);
            else teams2.add(teams.get(i));
        }


        loadLocalSpinner(teams2);
        loadVisitantSpinner(teams2);

    }
    public void loadLocalSpinner(List<TeamDB> teams){
        Spinner spinner = (Spinner)findViewById(R.id.spinnerOfTeamsLocalOnJornada);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        for (int i = 0; i < teams.size() ; ++i) {
            spinnerAdapter.add((teams.get(i)).getName());
        }
        spinnerAdapter.notifyDataSetChanged();
    }
    public void loadVisitantSpinner(List<TeamDB> teams){
        Spinner spinner = (Spinner)findViewById(R.id.spinnerOfTeamsVisitantOnJornada);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        for (int i = 0; i < teams.size() ; ++i) {
            spinnerAdapter.add((teams.get(i)).getName());
        }
        spinnerAdapter.notifyDataSetChanged();
    }

    public void onAddGoalsPressed(View view){
        Spinner localSpinner = (Spinner)findViewById(R.id.spinnerOfTeamsLocalOnJornada);
        Spinner visitantSpinner = (Spinner)findViewById(R.id.spinnerOfTeamsVisitantOnJornada);
        String localTeam = localSpinner.getSelectedItem().toString();
        String visitantTeam = visitantSpinner.getSelectedItem().toString();

        if (localTeam == visitantTeam) openDialodSameTeam();
        else {
            Intent intent = new Intent(this, JornadaGoalsActivity.class);
            intent.putExtra("localTeamName",localTeam);
            intent.putExtra("visitantTeamName",visitantTeam);
            intent.putExtra("JornadaNumber", jornadaActual);
            intent.putExtra("numeroDePartits",numeroDePartits);
            startActivity(intent);
        }
    }

    private void openDialodSameTeam(){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("The selected teams can not be the same")
                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            showcaseView = new ShowcaseView.Builder(this)
                    .setTarget(new ViewTarget(R.id.jornadaLabel,this))
                    .setContentTitle("MatchDay")
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentText("This is the current MatchDay")
                    .hideOnTouchOutside()
                    .setOnClickListener(this)
                    .build();
            showcaseView.setButtonText("Next");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void prepareHelp(){
        t1 = new ViewTarget(R.id.partitsLabel,this);
        t2 = new ViewTarget(R.id.spinnerOfTeamsLocalOnJornada,this);
        t3 = new ViewTarget(R.id.addJornadaOnJornada,this);
    }

    @Override
    public void onClick(View v){
        switch (whichShowCase){
            case 1:
                showcaseView.setShowcase(t1,true);
                showcaseView.setContentTitle("Games");
                showcaseView.setContentText("This is the games played on this specific MatchDay");
                break;
            case 2:
                showcaseView.setShowcase(t2,true);
                showcaseView.setContentTitle("Local and visitant team");
                showcaseView.setContentText("Here you have to choice which teams will play the game");
                break;
            case 3:
                showcaseView.setShowcase(t3, true);

                showcaseView.setContentTitle("Add goals");
                showcaseView.setContentText("Allows you add goals of players on this game");
                break;
            case 4:

                whichShowCase = 0;
                showcaseView.hide();
                break;

        }
        whichShowCase++;
    }


}

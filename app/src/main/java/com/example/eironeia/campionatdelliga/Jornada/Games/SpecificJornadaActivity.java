package com.example.eironeia.campionatdelliga.Jornada.Games;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eironeia.campionatdelliga.DB.JornadaDB;
import com.example.eironeia.campionatdelliga.Jornada.Goals.CustomListJornadaGoals;
import com.example.eironeia.campionatdelliga.DB.GoalsDB;
import com.example.eironeia.campionatdelliga.R;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eironeia on 24/5/16.
 */
public class SpecificJornadaActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinnerGames, spinnerJornada;

    int jornadaActual;
    long localGoals2 = 0;
    long visitantGoals2 = 0;
    List<String> localTeam = new ArrayList<>();
    List <String> visitantTeam = new ArrayList<>();

    private ShowcaseView showcaseView;
    private Target t1,t2,t3;
    private int whichShowCase = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.specific_jornada);

        spinnerGames = (Spinner) findViewById(R.id.gamesPlayedSpinner);
        spinnerJornada = (Spinner) findViewById(R.id.numberOfJornadaSpinner);

        loadAttributes();
        prepareHelp();
    }

    public void loadAttributes(){
        loadSpinnerJornada();
//        loadListOfGoals();
        configureSpinnerClicked();
    }

    public void loadSpinnerJornada(){
        spinnerJornada = (Spinner) findViewById(R.id.numberOfJornadaSpinner);

        List<JornadaDB> partits = JornadaDB.listAll(JornadaDB.class);
        long partitsJugats = partits.size();

        jornadaActual = 0;
        while (partitsJugats > 0){
            partitsJugats -= 5;
            jornadaActual += 1;
        }

        ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJornada.setAdapter(spinnerAdapter);
        for (int i = 1; i <= jornadaActual ; ++i) {
            spinnerAdapter.add(i);
        }
        spinnerAdapter.notifyDataSetChanged();
    }
    public void loadSpinnerGames(String numJornada){

        List<JornadaDB> partits = JornadaDB.findWithQuery(JornadaDB.class, "SELECT * FROM Jornada_DB WHERE NUM_JORNADA = ?",numJornada);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGames.setAdapter(spinnerAdapter);

        localTeam.clear();
        visitantTeam.clear();
        for (int i = 0; i < partits.size() ; ++i) {
            localTeam.add(partits.get(i).getLocalTeam());
            visitantTeam.add(partits.get(i).getVisitantTeam());
            spinnerAdapter.add( partits.get(i).getLocalTeam()+ " - " + partits.get(i).getVisitantTeam());
        }
        spinnerAdapter.notifyDataSetChanged();
    }

    public void configureSpinnerClicked(){

        spinnerJornada.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                jornadaActual = position+1;
                loadSpinnerGames((position+1) + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinnerGames.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                loadList(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void loadList(int position){

        List<GoalsDB> gols = GoalsDB.findWithQuery(GoalsDB.class, "SELECT * FROM GOALS_DB WHERE JORNADA = ?", jornadaActual + "");
        List<String> gameGoals  = new ArrayList<>();
        localGoals2 = 0;
        visitantGoals2 = 0;

        for (int i = 0; i < gols.size(); ++i){
            String team = gols.get(i).getTeam();
                String team1 = localTeam.get(position);
                String team2 = visitantTeam.get(position);
                if (team.equals(team1)){
                    localGoals2 += gols.get(i).getGoals();
                    gameGoals.add(team + "   " + gols.get(i).getPlayer() + "   Goals: " + gols.get(i).getGoals());
                }
                else if (team.equals(team2)){
                    visitantGoals2 += gols.get(i).getGoals();
                    gameGoals.add(team + "   " + gols.get(i).getPlayer() + "   Goals: " + gols.get(i).getGoals());
                }

        }

        // Create the adapter to convert the array to views
        CustomListJornadaGoals adapter = new CustomListJornadaGoals(this, gameGoals);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.listOfGoalsScored);
        listView.setAdapter(adapter);

        TextView puntuation2 = (TextView) findViewById(R.id.puntuationOnSpecificJornada);
        puntuation2.setText(localGoals2 + "  -  " + visitantGoals2);
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
                    .setTarget(new ViewTarget(R.id.puntuationOnSpecificJornada,this))
                    .setContentTitle("Result")
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentText("This is the result of the selected game played on selected match day")
                    .hideOnTouchOutside()
                    .setOnClickListener(this)
                    .build();
            showcaseView.setButtonText("Next");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void prepareHelp(){
        t1 = new ViewTarget(R.id.numberOfJornadaSpinner,this);
        t2 = new ViewTarget(R.id.gamesPlayedSpinner,this);
    }

    @Override
    public void onClick(View v){
        switch (whichShowCase){
            case 1:
                showcaseView.setShowcase(t1,true);
                showcaseView.setContentTitle("Match day");
                showcaseView.setContentText("Select a match day to see which games have been played");
                break;
            case 2:
                showcaseView.setShowcase(t2,true);
                showcaseView.setContentTitle("Games");
                showcaseView.setContentText("Select a game to see who score the goals");
                break;

            case 3:

                whichShowCase = 0;
                showcaseView.hide();
                break;

        }
        whichShowCase++;
    }

}

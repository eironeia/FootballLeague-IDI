package com.example.eironeia.campionatdelliga;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.eironeia.campionatdelliga.Classification.TopScorer.TopScorerActivity;
import com.example.eironeia.campionatdelliga.Classification.Score.ClasificationActivity;
import com.example.eironeia.campionatdelliga.DB.GoalsDB;
import com.example.eironeia.campionatdelliga.Jornada.Games.JornadaActivity;
import com.example.eironeia.campionatdelliga.DB.JornadaDB;
import com.example.eironeia.campionatdelliga.DB.PlayerDB;
import com.example.eironeia.campionatdelliga.Jornada.Games.SpecificJornadaActivity;
import com.example.eironeia.campionatdelliga.Players.PlayersActivity;
import com.example.eironeia.campionatdelliga.Players.SignPlayerActivity;
import com.example.eironeia.campionatdelliga.Teams.DescendTeamActivity;
import com.example.eironeia.campionatdelliga.Teams.TeamActivity;
import com.example.eironeia.campionatdelliga.DB.TeamDB;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ShowcaseView helpShowcaseView;
    private ShowcaseView aboutShowcaseView;
    private int whichShowCase = 1;
    private Target t1,t2,t3,t4,t5,t6,t7,t8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (emptyDB()) addSampleDB();
        else {
            TeamDB.deleteAll(TeamDB.class);
            PlayerDB.deleteAll(PlayerDB.class);
            JornadaDB.deleteAll(JornadaDB.class);
            GoalsDB.deleteAll(GoalsDB.class);
            addSampleDB();
        }

        prepareHelp();

//        Intent myIntent = new Intent(this, DescendTeamActivity.class);
//        this.startActivity(myIntent);
    }

    public void onPlayer(View view){
        Intent myIntent = new Intent(this, PlayersActivity.class);
        this.startActivity(myIntent);
    }



    public void onTeam(View view){
        Intent myIntent = new Intent(this, TeamActivity.class);
        this.startActivity(myIntent);

    }
    public void onJornada(View view){
        if (allReadyToStartCompetition()){
            Intent myIntent = new Intent(this, JornadaActivity.class);
            this.startActivity(myIntent);
        }
        else {
            openDialogMissingTeamsOrPlayers();
        }
    }
    public void onClasification(View view){
        if (allReadyToStartCompetition()) {
            Intent myIntent = new Intent(this, ClasificationActivity.class);
            this.startActivity(myIntent);
        }
        else {
            openDialogMissingTeamsOrPlayers();
        }
    }
    public void onTopScorer(View view){
        Intent myIntent = new Intent(this, TopScorerActivity.class);
        this.startActivity(myIntent);
    }
    public void onSpecificMatchDay(View view){
        if (allReadyToStartCompetition()) {
            Intent myIntent = new Intent(this, SpecificJornadaActivity.class);
            this.startActivity(myIntent);
        }
        else {
            openDialogMissingTeamsOrPlayers();
        }
    }
    public void onAscendTeam(View view){
        Intent myIntent = new Intent(this, DescendTeamActivity.class);
        this.startActivity(myIntent);
    }
    public void onSignPlayer(View view){
        Intent myIntent = new Intent(this, SignPlayerActivity.class);
        this.startActivity(myIntent);
    }

    public Boolean emptyDB(){
        if (PlayerDB.count(PlayerDB.class) == 0 && TeamDB.count(TeamDB.class) == 0) return true;
        return false;
    }

    public void addSampleDB(){
        addTeamsPlayers();
        addJornadas();
        addGoals();
    }
    public void addTeamsPlayers(){
        for (int i = 1; i <= 10; ++i){
            String playerTeam = new String();
            for (int j = 1; j <= 12; ++j){
                int posFix = i*12+j;
                String playerName = "Player"+posFix;
                playerTeam = "Team"+i;
                PlayerDB player;
                if (playerName.equals("Player13")) {
                    player = new PlayerDB(playerName,1,playerTeam);
                }
                else if (playerName.equals("Player37")) {
                    player = new PlayerDB(playerName,2,playerTeam);
                }
                else if (playerName.equals("Player53")) {
                    player = new PlayerDB(playerName,2,playerTeam);
                }
                else if (playerName.equals("Player61")) {
                    player = new PlayerDB(playerName,2,playerTeam);
                }
                else if (playerName.equals("Player109")) {
                    player = new PlayerDB(playerName,3,playerTeam);
                }
                else if (playerName.equals("Player121")) {
                    player = new PlayerDB(playerName,4,playerTeam);
                }
                else {
                    player = new PlayerDB(playerName,0,playerTeam);
                }
                player.save();
            }
            TeamDB team = new TeamDB();
            if (playerTeam.equals("Team1")){
                team = new TeamDB(playerTeam, "BCN",1,0,1,4);
            }
            else if (playerTeam.equals("Team2")){
                team = new TeamDB(playerTeam, "BCN",0,1,1,1);
            }
            else if (playerTeam.equals("Team3")){
                team = new TeamDB(playerTeam, "BCN",0,0,2,2);
            }
            else if (playerTeam.equals("Team4")){
                team = new TeamDB(playerTeam, "BCN",0,0,2,2);
            }
            else if (playerTeam.equals("Team5")){
                team = new TeamDB(playerTeam, "BCN",1,0,1,4);
            }
            else if (playerTeam.equals("Team6")){
                team = new TeamDB(playerTeam, "BCN",0,1,1,1);
            }
            else if (playerTeam.equals("Team7")){
                team = new TeamDB(playerTeam, "BCN",0,0,2,2);
            }
            else if (playerTeam.equals("Team8")){
                team = new TeamDB(playerTeam, "BCN",0,0,2,2);
            }
            else if (playerTeam.equals("Team9")){
                team = new TeamDB(playerTeam, "BCN",0,1,1,1);
            }
            else if (playerTeam.equals("Team10")){
                team = new TeamDB(playerTeam, "BCN",1,0,1,4);
            }
            team.save();
        }
    }
    public void addJornadas(){
        JornadaDB jornada;
        jornada = new JornadaDB(1,"Team1","Team2",0,0);
        jornada.save();
        jornada = new JornadaDB(1,"Team3","Team4",0,0);
        jornada.save();
        jornada = new JornadaDB(1,"Team5","Team6",2,0);
        jornada.save();
        jornada = new JornadaDB(1,"Team7","Team8",0,0);
        jornada.save();
        jornada = new JornadaDB(1,"Team9","Team10",3,4);
        jornada.save();
        jornada = new JornadaDB(2,"Team2","Team1",0,1);
        jornada.save();
        jornada = new JornadaDB(2,"Team4","Team3",2,2);
        jornada.save();
        jornada = new JornadaDB(2,"Team6","Team5",0,0);
        jornada.save();
        jornada = new JornadaDB(2,"Team8","Team7",0,0);
        jornada.save();
        jornada = new JornadaDB(2,"Team10","Team9",0,0);
        jornada.save();
    }
    public void addGoals(){

        GoalsDB goal;
        goal = new GoalsDB(1,2,"Team5","Player61",2);
        goal.save();
        goal = new GoalsDB(1,4,"Team9","Player109",3);
        goal.save();
        goal = new GoalsDB(1,4,"Team10","Player121",4);
        goal.save();
        goal = new GoalsDB(2,0,"Team1","Player13",1);
        goal.save();
        goal = new GoalsDB(2,1,"Team4","Player53",2);
        goal.save();
        goal = new GoalsDB(2,1,"Team3","Player37",2);
        goal.save();

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
            helpShowcaseView = new ShowcaseView.Builder(this)
                    .setTarget(new ViewTarget(R.id.teamsButton,this))
                    .setContentTitle("Teams")
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentText("Allows you add a new team")
                    .hideOnTouchOutside()
                    .setOnClickListener(this)
                    .build();
            helpShowcaseView.setButtonText("Next");
            return true;
        }
        else if (id == R.id.about){
            aboutShowcaseView = new ShowcaseView.Builder(this)
                    .setContentTitle("About")
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentText("\n\nFootball league of UPC, FIB.\n\nApp version 1.0\n\nUsed libraries:\n\n* SugarOrm: to do a bit easier the queries of Database\n\n* Showcase: to do it more visible the help function.\n")
                    .hideOnTouchOutside()
                    .build();
            aboutShowcaseView.setButtonText("Okay");
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean allReadyToStartCompetition(){
        if (TeamDB.count(TeamDB.class) == 10 && PlayerDB.count(PlayerDB.class) == 120) return true;
        return false;
    }

    private void openDialogMissingTeamsOrPlayers(){
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("To start competition you need 10 teams and 12 players for each team")
                .setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void prepareHelp(){
        t1 = new ViewTarget(R.id.teamsButton,this);
        t2 = new ViewTarget(R.id.playersButton,this);
        t3 = new ViewTarget(R.id.jornadaButton,this);
        t4 = new ViewTarget(R.id.specificJornadaButton,this);
        t5 = new ViewTarget(R.id.clasificationButton,this);
        t6 = new ViewTarget(R.id.topScorerButton,this);
        t7 = new ViewTarget(R.id.ascendTeamButton,this);
        t8 = new ViewTarget(R.id.signPlayerButton,this);
    }

    @Override
    public void onClick(View v){
        switch (whichShowCase){
            case 0:
                helpShowcaseView.setShowcase(t1,true);
                helpShowcaseView.setContentTitle("Teams");
                helpShowcaseView.setContentText("Allows you add a new team");
                break;
            case 1:
                helpShowcaseView.setShowcase(t2,true);
                helpShowcaseView.setContentTitle("Players");
                helpShowcaseView.setContentText("Allows you add a new player on team selected in the spinner");
                break;
            case 2:
                helpShowcaseView.setShowcase(t3, true);

                helpShowcaseView.setContentTitle("Round");
                helpShowcaseView.setContentText("Allows you add a new Match between two teams in a specific Round");
                helpShowcaseView.forceTextPosition(ShowcaseView.ABOVE_SHOWCASE);
                break;
            case 3:
                helpShowcaseView.setShowcase(t4,true);
                helpShowcaseView.setContentTitle("Specific Round");
                helpShowcaseView.setContentText("Allows you see in a specific round and in a specific game the goals of the players");
                break;
            case 4:
                helpShowcaseView.setShowcase(t5, true);
                helpShowcaseView.setContentTitle("Clasification");
                helpShowcaseView.setContentText("Allows you see how the teams goes. Position, Score, Wins, Draws,..");
                break;
            case 5:

                helpShowcaseView.setShowcase(t6,true);
                helpShowcaseView.setContentTitle("Top Scorer");
                helpShowcaseView.setContentText("Allows you see which are the players that has scored more goals");
                break;
            case 6:
                helpShowcaseView.setShowcase(t7,true);
                helpShowcaseView.setContentTitle("Ascend Team");
                helpShowcaseView.setContentText("Allows you ascend a Team and as consecuence descend other one");
                break;

            case 7:
                helpShowcaseView.setShowcase(t8,true);
                helpShowcaseView.setContentTitle("Sign Player");
                helpShowcaseView.setContentText("Allows you sign a Player in one team and as consecuence delete other player of the same team");
                break;

            case 8:

                whichShowCase = 0;
                helpShowcaseView.hide();
                break;

        }
        whichShowCase++;
    }

    public void addTeamsonDB() {

//        for (int i = 0; i < 2; ++i){ // Jornada
//            for (int j = 0; j < 5; ++j){//Partits
//                for (int k = 1; k <= 10; ++k){ //Equips
//                    for (int z = 1; z <= 12; ++z){ //Jugadors
//                        new PlayerDB()
//
//                    }
//                }
//            }
//        }
    }

    public void addPlayersOnDB(){

    }

    public void addGoalsOnDB(){

    }

    public void addJornadasonDB(){

    }
}



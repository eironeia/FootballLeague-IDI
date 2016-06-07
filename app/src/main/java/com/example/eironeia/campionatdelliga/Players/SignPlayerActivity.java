package com.example.eironeia.campionatdelliga.Players;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eironeia.campionatdelliga.DB.PlayerDB;
import com.example.eironeia.campionatdelliga.DB.TeamDB;
import com.example.eironeia.campionatdelliga.R;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.Target;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.List;

/**
 * Created by Eironeia on 26/5/16.
 */
public class SignPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner;
    private ShowcaseView showcaseView;
    private Target t1,t2,t3;
    private int whichShowCase = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_player);

        uploadPlayersOnSpinner();
        prepareHelp();
    }

    private void uploadPlayersOnSpinner(){
        List<PlayerDB> l = TeamDB.findWithQuery(PlayerDB.class, "Select * from player_db ORDER BY name");

        spinner = (Spinner)findViewById(R.id.spinnerPlayerUnsubscribe);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        for (int i = 0; i < l.size() ; ++i) {
            spinnerAdapter.add((l.get(i)).getName());
        }
        spinnerAdapter.notifyDataSetChanged();
    }

    public void onSignPlayer(View view){
        String playerName = spinner.getSelectedItem().toString();
        List<PlayerDB> p = TeamDB.findWithQuery(PlayerDB.class, "Select * from player_db WHERE name = ?", playerName);

        TextView nameNewPlayer = (TextView) findViewById(R.id.nameplayerSign);
        if (nameNewPlayer.getText().toString().isEmpty()){
            openDialogEmptyFields();
        }
        else {
            List<PlayerDB> p2 = TeamDB.findWithQuery(PlayerDB.class, "Select * from player_db WHERE name = ?", nameNewPlayer.getText().toString());
            if (p2.size() == 0){
                PlayerDB player = p.get(0);
                player.setName(nameNewPlayer.getText().toString());
                player.setGoals(0);
                player.save();
                Toast.makeText(this,"Player "+nameNewPlayer.getText().toString()+" added\nPlayer "+playerName + " unsubscribed",Toast.LENGTH_SHORT).show();
                uploadPlayersOnSpinner();
            }
            else{
                openDialogPlayerAlreadyExists();
            }
        }


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
                    .setTarget(new ViewTarget(R.id.spinnerPlayerUnsubscribe,this))
                    .setContentTitle("Unsubscribe player")
                    .setStyle(R.style.CustomShowcaseTheme3)
                    .setContentText("Unsubscribe the selected player of his team")
                    .hideOnTouchOutside()
                    .setOnClickListener(this)
                    .build();
            showcaseView.setButtonText("Next");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void prepareHelp(){
        t1 = new ViewTarget(R.id.nameplayerSign,this);
    }

    @Override
    public void onClick(View v){
        switch (whichShowCase){
            case 1:
                showcaseView.setShowcase(t1,true);
                showcaseView.setContentTitle("New player");
                showcaseView.setContentText("This will be a new player added on the team of the selected player");
                break;
            case 2:

                whichShowCase = 0;
                showcaseView.hide();
                break;

        }
        whichShowCase++;
    }
}


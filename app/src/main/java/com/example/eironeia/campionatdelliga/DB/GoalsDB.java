package com.example.eironeia.campionatdelliga.DB;

import com.orm.SugarRecord;

/**
 * Created by Eironeia on 23/5/16.
 */
public class GoalsDB extends SugarRecord {

    long jornada;
    long numOfMatches;
    String team;
    String player;
    long goals;

    public GoalsDB() {
    }

    public GoalsDB(long jornada, long numOfMatches, String team, String player, long goals) {
        this.jornada = jornada;
        this.numOfMatches = numOfMatches;
        this.team = team;
        this.player = player;
        this.goals = goals;
    }


    public long getJornada() {
        return jornada;
    }

    public void setJornada(long jornada) {
        this.jornada = jornada;
    }

    public long getNumOfMatches() {
        return numOfMatches;
    }

    public void setNumOfMatches(long numOfMatches) {
        this.numOfMatches = numOfMatches;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public long getGoals() {
        return goals;
    }

    public void setGoals(long goals) {
        this.goals = goals;
    }
}

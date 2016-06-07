package com.example.eironeia.campionatdelliga.DB;

import com.orm.SugarRecord;

/**
 * Created by Eironeia on 14/5/16.
 */
public class PlayerDB extends SugarRecord{
    private String name;
    private long goals;
    private String team;

    public PlayerDB() {
    }

    public PlayerDB(String name, long goals, String team) {
        this.name = name;
        this.goals = goals;
        this.team = team;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public long getGoals() {
        return goals;
    }
    public void setGoals(long goals) {
        this.goals = goals;
    }
    public String getTeam() {
        return team;
    }
    public void setTeam(String team) {
        this.team = team;
    }

    public void incrementGoals(long newGoals){
        this.goals += newGoals;
    }
}

package com.example.eironeia.campionatdelliga.DB;

import com.orm.SugarRecord;

/**
 * Created by Eironeia on 15/5/16.
 */
public class TeamDB extends SugarRecord{
    private String name;
    private String city;
    private long victories;
    private long defeats;
    private long draws;
    private long score;

    public TeamDB() {
    }

    public TeamDB(String name, String city, long victories, long defeats, long draws, long score) {
        this.name = name;
        this.city = city;
        this.victories = victories;
        this.defeats = defeats;
        this.draws = draws;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public long getVictories() {
        return victories;
    }

    public void setVictories(long victories) {
        this.victories = victories;
    }

    public long getDefeats() {
        return defeats;
    }

    public void setDefeats(long defeats) {
        this.defeats = defeats;
    }

    public long getDraws() {
        return draws;
    }

    public void setDraws(long draws) {
        this.draws = draws;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public void incrementVictories(){
        victories += 1;
        score += 3;
    }

    public void incrementDefeats(){
        defeats += 1;
    }

    public void incrementDraws(){
        draws += 1;
        score += 1;
    }



}

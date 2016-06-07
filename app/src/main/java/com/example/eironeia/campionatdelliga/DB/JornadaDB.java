package com.example.eironeia.campionatdelliga.DB;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Eironeia on 16/5/16.
 */
public class JornadaDB extends SugarRecord {
    long numJornada;
    String localTeam;
    String visitantTeam;
    long localsGoals;
    long visitantGoals;

    public JornadaDB() {
    }

    public JornadaDB(long numJornada, String localTeam, String visitantTeam, long localsGoals, long visitantGoals) {
        this.numJornada = numJornada;
        this.localTeam = localTeam;
        this.visitantTeam = visitantTeam;
        this.localsGoals = localsGoals;
        this.visitantGoals = visitantGoals;
    }

    public long getNumJornada() {
        return numJornada;
    }

    public void setNumJornada(long numJornada) {
        this.numJornada = numJornada;
    }

    public String getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(String localTeam) {
        this.localTeam = localTeam;
    }

    public String getVisitantTeam() {
        return visitantTeam;
    }

    public void setVisitantTeam(String visitantTeam) {
        this.visitantTeam = visitantTeam;
    }

    public long getLocalsGoals() {
        return localsGoals;
    }

    public void setLocalsGoals(long localsGoals) {
        this.localsGoals = localsGoals;
    }

    public long getVisitantGoals() {
        return visitantGoals;
    }

    public void setVisitantGoals(long visitantGoals) {
        this.visitantGoals = visitantGoals;
    }
}
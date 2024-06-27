package ranksystem;

import java.util.ArrayList;

public class Player {
    private String name;
    private int id;
    private int mmr;

    private int matchesPlayed;
    private int victories;
    private ArrayList<MatchData> matchHistory;

    public Player(String name, int id){
        this.name = name;
        this.id = id;
        mmr = 100;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMmr() {
        return mmr;
    }



    public int getMatchesPlayed() {
        return matchesPlayed;
    }



    public int getVictories() {
        return victories;
    }



    public ArrayList<MatchData> getMatchHistory() {
        return matchHistory;
    }


}

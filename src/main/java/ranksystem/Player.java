package ranksystem;

import java.util.ArrayList;

public class Player {
    private String name;
    private int id;
    private int mmr;

    private int matchesPlayed;
    private int victories;

    private int defeats;
    private ArrayList<MatchData> matchHistory;

    public Player(String name, int id){
        this.name = name;
        this.id = id;
        mmr = 100;
        matchHistory = new ArrayList<>();

    }

    public Player(String name, int id, int mmr, int matchesPlayed, int victories, int defeats){
        this.name = name;
        this.id = id;
        this.mmr = mmr;
        this.matchesPlayed = matchesPlayed;
        this.victories = victories;
        this.defeats = defeats;

        matchHistory = new ArrayList<>();
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

    public int getDefeats(){
        return defeats;
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

    public void increaseMmr(){
        this.mmr += 20;
    }
    public void decreaseMmr(){
        if(this.mmr <= 20){
            this.mmr = 0;
        }else{
            this.mmr -= 20;
        }
    }
    public void increaseWins(){
        victories++;
    }

    public void increaseMatchesPlayed(){
        matchesPlayed++;
    }

    public void increaseDefeats(){
        defeats++;
    }

    public void saveMatchData(MatchData match){
        this.matchHistory.add(match);
    }


}

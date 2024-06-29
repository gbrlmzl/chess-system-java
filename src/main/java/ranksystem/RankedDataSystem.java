package ranksystem;

import chess.Color;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Comparator;

public class RankedDataSystem {
    private ArrayList<Player> playersList;
    private ArrayList<MatchData> matchHistory;


    public RankedDataSystem(){
        this.playersList = new ArrayList<>();
        this.matchHistory = new ArrayList<>();
    }

    public boolean registerPlayer(Player player){
        if(playerIdExists(player)){
            throw new DataException("THIS PLAYER ID ALREADY EXISTS!");
        }
        playersList.add(player);
        return true;
    }

    public void registerMatch(MatchData match){
        matchHistory.add(match);
    }

    private Player[] makeRanking (){
        ArrayList<Player> clonePlayerList = new ArrayList<>(playersList) ;
        clonePlayerList.sort(Comparator.comparingInt(Player::getMmr).reversed());
        if(clonePlayerList.isEmpty()){
            return null;
        }
        int size = Math.min(clonePlayerList.size(), 10);
        Player[] topPlayers = new Player[size];

        for(int i = 0; i < size; i++){
            topPlayers[i] = clonePlayerList.get(i);
        }
        return topPlayers;




    }
    public void showRanking(){
        Player[] playersRank =  makeRanking();

        if(makeRanking() == null){
                System.out.println();
                System.out.println("There are no players registered.");

            }else{
                for(int i = 0; i < playersRank.length; i++){
                    System.out.println((i+1) +  " - " + playersRank[i].getName() + "\n" +
                                                "MMR: " +playersRank[i].getMmr() + "\n" +
                                                "-----------------------");
                }
            }
        }


    private boolean playerIdExists(Player player){
        for(Player p : playersList){
            if(player.getId() == p.getId()){
                return true;
            }
        }
        return false;
    }
    private boolean playerIdExists(int id){
        for(Player p : playersList){
            if(id == p.getId()){
                return true;
            }
        }
        return false;

    }

    public Player getPlayer(int id){
        for (Player p : playersList){
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }



    public boolean validRankedParameters(int whiteId, int blackId){
        if(whiteId == blackId){
            throw new DataException("YOU CAN'T PLAY AGAINST YOURSELF");
        }
        if(!playerIdExists(whiteId)){
            throw new DataException("WHITE PLAYER ID NOT REGISTERED");
        }
        if(!playerIdExists(blackId)){
            throw new DataException("BLACK PLAYER ID NOT REGISTERED");
        }


        return true;
    }

    public int generateMatchCode(){
        return (this.matchHistory.size() + 1);
    }

    public Player getWinnerPlayer(TempPlayer whitePlayer, TempPlayer blackPlayer, Color winner){
        if(whitePlayer.getColor().equals(winner)){
            return getPlayer(whitePlayer.getId());
        }else{
            return getPlayer(blackPlayer.getId());
        }
    }
    public Player getLoserPlayer(TempPlayer whitePlayer, TempPlayer blackPlayer, Color winner){
        if(whitePlayer.getColor().equals(winner)){
            return getPlayer(blackPlayer.getId());
        }else{
            return getPlayer(whitePlayer.getId());
        }
    }

    public void updateMmr(Player winner, Player loser){
        for(Player p : playersList){
            if(p.getId() == winner.getId()){
                p.increaseMmr();
                p.increaseWins();
                p.increaseMatchesPlayed();
                break;
            }
        }
        for(Player p : playersList){
            if(p.getId() == loser.getId()){
                p.decreaseMmr();
                p.increaseDefeats();
                p.increaseMatchesPlayed();
                break;
            }
        }

    }

    public void updateMatchHistory(TempPlayer whitePlayer, TempPlayer blackPlayer, MatchData match){
        for(Player p : playersList){
            if(p.getId() == getPlayer(whitePlayer.getId()).getId()){
                p.saveMatchData(match);
                break;

            }
        }
        for(Player p : playersList){
            if(p.getId() == getPlayer(blackPlayer.getId()).getId()){
                p.saveMatchData(match);
                break;
            }
        }
    }

    public String showPlayerScore(TempPlayer whitePlayer, TempPlayer blackPlayer){
        return  "-----------------------" + "\n" +
                "White Player name: " +
                getPlayer(whitePlayer.getId()).getName() + "\n" +
                "MMR: " + getPlayer(whitePlayer.getId()).getMmr() + "\n" +
                "-----------------------" + "\n" +
                "Black player name: " +
                getPlayer(blackPlayer.getId()).getName() + "\n" +
                "MMR: " + getPlayer(blackPlayer.getId()).getMmr() + "\n" +
                "-----------------------";
    }

    public ArrayList<MatchData> getMatchHistory() {
        return new ArrayList<>(matchHistory);
    }

    private ArrayList<MatchData> last10Matches(){
        ArrayList<MatchData> invertedList = new ArrayList<>(getMatchHistory().reversed());
        ArrayList<MatchData> last10 = new ArrayList<>();

        if(invertedList.isEmpty()){
            return null;
        }
        int size = Math.min(invertedList.size(), 10);

        for(int i = 0; i < size; i++){
            last10.add(invertedList.get(i));
        }

        return last10;
    }

    public void showLast10Matches(){
        if(last10Matches() == null){
            throw new DataException("THERE ARE NO MATCHES RECORDED IN HISTORY!");
        }

        for(MatchData m : last10Matches()){
            System.out.println(m.showResumedInfo());
        }



    }

}

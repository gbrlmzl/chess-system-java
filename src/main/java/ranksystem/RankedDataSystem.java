package ranksystem;

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
        if(playerIdAlreadyExists(player)){
            throw new DataException("THIS PLAYER ID ALREADY EXISTS!");
        }
        playersList.add(player);
        return true;
    }

    private Player[] makeRanking (){
        ArrayList<Player> clonePlayerList = playersList;
        clonePlayerList.sort(Comparator.comparingInt(Player::getMmr).reversed());
        Player[] top10 = new Player[10];
        for(int i = 0; i < 10; i++){
            top10[i] = clonePlayerList.get(i);
        }
        return top10;

    }
    public void showRanking(){
        for(int i = 0; i < makeRanking().length; i++){
            if(makeRanking()[i] == null){
                System.out.println();
                System.out.println("There are no more players.");
                break;
            }else{
                System.out.println((i+1) + " - " + makeRanking()[i].getName() + "\n" +
                                   "MMR: " +makeRanking()[i].getMmr() + "\n" +
                                   "-----------------------");
            }
        }
    }

    private boolean playerIdAlreadyExists(Player player){
        for(Player p : playersList){
            if(player.getId() == p.getId()){
                return true;
            }
        }
        return false;
    }
}

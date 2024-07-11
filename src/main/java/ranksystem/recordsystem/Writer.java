package ranksystem.recordsystem;

import ranksystem.MatchData;
import ranksystem.Player;
import ranksystem.RankedDataSystem;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Writer {
    private File folderWritePath = new File("C:\\temp\\ChessManiaData");






    private void makeDirectories(){
        if(!folderWritePath.exists()){
           folderWritePath.mkdir();

        }

    }
    private String formatedPlayerData(Player player){
        return player.getName() + ", " + player.getId() + ", " + player.getMmr() + ", " + player.getMatchesPlayed() + ", " + player.getVictories() + ", " + player.getDefeats();

    }

    private String formatedMatchData(MatchData match){
        return match.getMatchCode() + ", " + match.getBlackPlayer().getId() + ", " + match.getWhitePlayer().getId() + ", " + match.getWinner().getId() + ", " + match.getTotalMoves() + ", " + match.getStartTime() + ", " + match.getEndTime();
    }


    public void saveData(RankedDataSystem rankSystem){
        makeDirectories();


        try(BufferedWriter playerBw = new BufferedWriter(new FileWriter(folderWritePath.getPath() + "\\Players.csv"))){
            for(Player p : rankSystem.getPlayersList()){
                playerBw.write(formatedPlayerData(p));
                playerBw.newLine();
            }



        }catch(IOException e){
            System.out.println("ERROR WRITING"); //provisório
        }

        try(BufferedWriter matchDataBw = new BufferedWriter(new FileWriter(folderWritePath.getPath() + "\\MatchHistory.csv"))){
            for(MatchData match : rankSystem.getMatchHistory()){
                matchDataBw.write(formatedMatchData(match));
                matchDataBw.newLine();
            }

        }catch(IOException e){
            System.out.println("ERROR WRITING MATCHDATA"); //provisorio
        }


    }

    public void initialize(RankedDataSystem rankSystem){
        File playerFolder = new File(folderWritePath.getPath() + "\\Players.csv");
        File matchFolder = new File(folderWritePath.getPath() +"\\MatchHistory.csv");
        if(playerFolder.exists() && matchFolder.exists()){

            try(BufferedReader playerBr = new BufferedReader(new FileReader(playerFolder))){
                String line = playerBr.readLine();

                while(line != null){
                    String[] playerData = line.split(", ");
                    String playerName = playerData[0];
                    int playerId = Integer.parseInt(playerData[1]);
                    int playerMmr = Integer.parseInt(playerData[2]);
                    int playerMatchs = Integer.parseInt(playerData[3]);
                    int playerVictories = Integer.parseInt(playerData[4]);
                    int playerDefeats = Integer.parseInt(playerData[5]);

                    Player player = new Player(playerName, playerId, playerMmr, playerMatchs, playerVictories, playerDefeats);
                    rankSystem.registerPlayer(player);

                    line = playerBr.readLine();
                    //devo criar primeiro o leitor do histórico de partidas
                }

            }catch(IOException e){
                System.out.println("ERROR PLAYER READING"); //provisorio
            }

            try(BufferedReader matchBr = new BufferedReader(new FileReader(matchFolder))){
                String line = matchBr.readLine();
                while(line != null){
                    String[] matchHistoryData = line.split(", ");
                    int matchCode = Integer.parseInt(matchHistoryData[0]);
                    int blackPlayerId = Integer.parseInt(matchHistoryData[1]);
                    int whitePlayerId = Integer.parseInt(matchHistoryData[2]);
                    int winnerId = Integer.parseInt(matchHistoryData[3]);
                    int totalMoves = Integer.parseInt(matchHistoryData[4]);
                    LocalDateTime startTime = LocalDateTime.parse(matchHistoryData[5]);
                    LocalDateTime endTime = LocalDateTime.parse(matchHistoryData[6]);

                    MatchData match = new MatchData(matchCode, rankSystem.getPlayer(blackPlayerId), rankSystem.getPlayer(whitePlayerId), rankSystem.getPlayer(winnerId),totalMoves, startTime, endTime);
                    /*aqui está outro problema. provavelmente terei que ler os dados dos players primeiro, cadastra-los com um matchHistory vazio
                    e depois ler os dados das matchDatas e verificar cada uma e adicionar ao historico dos jogadores. */

                    rankSystem.registerMatch(match);

                    line = matchBr.readLine();




                }


            }catch(IOException e){
                System.out.println("ERROR MATCH READING"); //provisorio
            }
            //3 etapa que consiste em adicionar as partidas  aos historicos dos respectivos players

            rankSystem.reviewPlayerMatchHistory();







        }
        //do contrário, não faz nada
    }






}

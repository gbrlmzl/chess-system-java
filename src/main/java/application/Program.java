package application;


import chess.*;
import ranksystem.MatchData;
import ranksystem.Player;
import ranksystem.RankedDataSystem;
import ranksystem.TempPlayer;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean exit = false;
        RankedDataSystem rankSystem = new RankedDataSystem();
        while (!exit) {
            System.out.println(UI.menu);
            int option = Integer.parseInt(sc.nextLine());
            switch(option){
                case 1:
                    ChessMatch chessMatch = new ChessMatch();
                    List<ChessPiece> captured = new ArrayList<>();
                    while(!chessMatch.getCheckMate()) {
                        try {

                            UI.printMatch(chessMatch, captured);
                            System.out.println();
                            System.out.print("Source: ");
                            ChessPosition source = UI.readChessPosition(sc);

                            boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                            UI.clearScreen();
                            UI.printBoard(chessMatch.getPieces(), possibleMoves);
                            System.out.println();
                            System.out.print("Target: ");
                            ChessPosition target = UI.readChessPosition(sc);

                            ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                            if(capturedPiece != null ){
                                captured.add(capturedPiece);
                            }
                            if(chessMatch.getPromoted() != null){
                                System.out.println("Enter piece for promotion (B/N/R/Q): ");
                                String type = sc.nextLine().toUpperCase();
                                while(!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")){
                                    System.out.println("Invalid value! Enter piece for promotion (B/N/R/Q): ");
                                    type = sc.nextLine().toUpperCase();
                                }
                                chessMatch.replacePromotedPiece(type);
                            }


                        } catch (ChessException e) {
                            System.out.println(e.getMessage());
                            sc.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println(e.getMessage());
                            sc.nextLine();
                        }
                    }
                    UI.clearScreen();
                    UI.printMatch(chessMatch, captured);
                    System.out.println();
                    System.out.println("Match completed, press enter to return to menu");
                    sc.nextLine();
                    break;


                case 2:
                    System.out.print("White player ID: ");
                    int whiteId = Integer.parseInt(sc.nextLine());
                    System.out.print("Black player ID: ");
                    int blackId = Integer.parseInt(sc.nextLine());

                    if(rankSystem.validRankedParameters(whiteId, blackId)){
                        TempPlayer whitePlayer = new TempPlayer(whiteId, Color.WHITE);
                        TempPlayer blackPlayer = new TempPlayer(blackId, Color.BLACK);

                        //printar classificação atual e nome dos jogadores
                        System.out.println(rankSystem.showPlayerScore(whitePlayer, blackPlayer));
                        System.out.println("Press enter to start the match");
                        sc.nextLine();

                        //press enter



                        //COMEÇO DA PARTIDA
                        chessMatch = new ChessMatch();
                        captured = new ArrayList<>();
                        LocalDateTime startTime = LocalDateTime.now();

                        while(!chessMatch.getCheckMate()) {
                            try {
                                UI.printMatch(chessMatch, captured);
                                System.out.println();
                                System.out.print("Source: ");
                                ChessPosition source = UI.readChessPosition(sc);

                                boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                                UI.clearScreen();
                                UI.printBoard(chessMatch.getPieces(), possibleMoves);
                                System.out.println();
                                System.out.print("Target: ");
                                ChessPosition target = UI.readChessPosition(sc);

                                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                                if(capturedPiece != null ){
                                    captured.add(capturedPiece);
                                }
                                if(chessMatch.getPromoted() != null){
                                    System.out.println("Enter piece for promotion (B/N/R/Q): ");
                                    String type = sc.nextLine().toUpperCase();
                                    while(!type.equals("B") && !type.equals("N") && !type.equals("R") & !type.equals("Q")){
                                        System.out.println("Invalid value! Enter piece for promotion (B/N/R/Q): ");
                                        type = sc.nextLine().toUpperCase();
                                    }
                                    chessMatch.replacePromotedPiece(type);
                                }


                            } catch (ChessException e) {
                                System.out.println(e.getMessage());
                                sc.nextLine();
                            } catch (InputMismatchException e) {
                                System.out.println(e.getMessage());
                                sc.nextLine();
                            }
                        }
                        LocalDateTime endTime = LocalDateTime.now();
                        UI.clearScreen();
                        UI.printMatch(chessMatch, captured);


                        //criar matchData
                        Player winnerPlayer = rankSystem.getWinnerPlayer(whitePlayer, blackPlayer, chessMatch.getWinner());
                        Player loserPlayer = rankSystem.getLoserPlayer(whitePlayer, blackPlayer, chessMatch.getWinner());
                        MatchData match = new MatchData(rankSystem.generateMatchCode(), rankSystem.getPlayer(blackPlayer.getId()), rankSystem.getPlayer(whitePlayer.getId()),winnerPlayer ,chessMatch.getTurn(), startTime, endTime);
                        rankSystem.registerMatch(match);
                        //aplicar e printar resultados de classificação
                        //+20 de mmr no vencedor e -20 no perdedor
                        rankSystem.updateMmr(winnerPlayer, loserPlayer);

                        //printar scores atuais
                        System.out.println("Updated ranking: ");
                        System.out.println(rankSystem.showPlayerScore(whitePlayer, blackPlayer));

                        //salvar paradas nos dados internos do programa

                        //gravador***
                        //tratar excecoes***



                        System.out.println();
                        System.out.println("Match completed, press enter to return to menu");
                        sc.nextLine();
                        break;



                    }






                case 3:
                    System.out.print("Player name: ");
                    String playerName = sc.nextLine();
                    System.out.print("Player ID(only numbers): ");
                    int playerId = Integer.parseInt(sc.nextLine());
                    if(rankSystem.registerPlayer(new Player(playerName, playerId))){
                        System.out.println("Player successfully registered.");
                    }
                    break;



                case 4:
                    System.out.println("Ranking");
                    rankSystem.showRanking();
                    System.out.println("Press enter to return to menu");
                    sc.nextLine();
                    break;

                case 5:




                case 6:
                    //verificação, etc
                    exit = true;
                    break;



            }







        }


    }
}

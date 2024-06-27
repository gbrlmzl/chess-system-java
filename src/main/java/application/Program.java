package application;


import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import ranksystem.MatchData;
import ranksystem.Player;
import ranksystem.RankedDataSystem;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ChessMatch chessMatch = new ChessMatch();
        List<ChessPiece> captured = new ArrayList<>();
        LocalDateTime startTime = LocalDateTime.now();
        boolean exit = false;
        RankedDataSystem rankSystem = new RankedDataSystem();
        while (!exit) {
            System.out.println(UI.menu);

            int option = Integer.parseInt(sc.nextLine());
            switch(option){
                case 1:
                    /*while(!chessMatch.getCheckMate()) {
                        try {

                            UI.clearScreen();
                            System.out.println(UI.menu);
                            sc.nextLine();
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
                    UI.printMatch(chessMatch, captured); */


                case 2:


                case 3:
                    System.out.print("Player name: ");
                    String playerName = sc.nextLine();
                    System.out.print("Player ID(only numbers): ");
                    int playerId = Integer.parseInt(sc.nextLine());
                    if(rankSystem.registerPlayer(new Player(playerName, playerId))){
                        System.out.println("Player successfully registered.");
                    }
                    System.out.println(UI.menu);
                    option = Integer.parseInt(sc.nextLine());
                    break;



                case 4:
                    System.out.println("Ranking");
                    rankSystem.showRanking();
                    option = Integer.parseInt(sc.nextLine());
                    break;

                case 5:


            }





            /* while(!chessMatch.getCheckMate()) {
                try {

                    UI.clearScreen();
                    System.out.println(UI.menu);
                    sc.nextLine();
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
            //MatchData data = new MatchData()
            UI.clearScreen();
            UI.printMatch(chessMatch, captured);



        } */

        }


    }
}

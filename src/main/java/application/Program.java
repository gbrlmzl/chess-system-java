package application;


import chess.*;
import ranksystem.*;
import ranksystem.recordsystem.Writer;
import java.time.LocalDateTime;
import java.util.*;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        boolean exit = false;
        RankedDataSystem rankSystem = new RankedDataSystem();
        Writer dataControl = new Writer();

        dataControl.initialize(rankSystem);
        UI.clearScreen();

        while (!exit) {
            dataControl.saveData(rankSystem);
            try{
                System.out.println(UI.menu);
                int option = Integer.parseInt(sc.nextLine());
                switch(option){
                    case 1:
                        ChessMatch chessMatch = new ChessMatch();
                        List<ChessPiece> captured = new ArrayList<>();
                        UI.clearScreen();
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
                                UI.clearScreen();

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
                                UI.clearScreen();
                            } catch (InputMismatchException e) {
                                System.out.println(e.getMessage());
                                sc.nextLine();
                                UI.clearScreen();
                            }
                        }
                        UI.clearScreen();
                        UI.printMatch(chessMatch, captured);
                        System.out.println();
                        System.out.println("Match completed, press enter to return to menu");
                        sc.nextLine();
                        UI.clearScreen();
                        break;


                    case 2:
                        UI.clearScreen();


                        try{
                            System.out.print("White player ID: ");
                            int whiteId = Integer.parseInt(sc.nextLine());
                            System.out.print("Black player ID: ");
                            int blackId = Integer.parseInt(sc.nextLine());

                            rankSystem.validRankedParameters(whiteId, blackId);
                            TempPlayer whitePlayer = new TempPlayer(whiteId, Color.WHITE);
                            TempPlayer blackPlayer = new TempPlayer(blackId, Color.BLACK);

                            System.out.println(rankSystem.showPlayerScore(whitePlayer, blackPlayer));
                            System.out.println("Press enter to start the match");
                            sc.nextLine();
                            UI.clearScreen();


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
                                    UI.clearScreen();

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
                                    UI.clearScreen();
                                } catch (InputMismatchException e) {
                                    System.out.println(e.getMessage());
                                    sc.nextLine();
                                    UI.clearScreen();
                                }
                            }
                            LocalDateTime endTime = LocalDateTime.now();
                            UI.clearScreen();
                            UI.printMatch(chessMatch, captured);


                            //generate matchData
                            Player winnerPlayer = rankSystem.getWinnerPlayer(whitePlayer, blackPlayer, chessMatch.getWinner());
                            Player loserPlayer = rankSystem.getLoserPlayer(whitePlayer, blackPlayer, chessMatch.getWinner());
                            MatchData match = new MatchData(rankSystem.generateMatchCode(), rankSystem.getPlayer(blackPlayer.getId()), rankSystem.getPlayer(whitePlayer.getId()),winnerPlayer ,chessMatch.getTurn(), startTime, endTime);
                            rankSystem.registerMatch(match);
                            //update mmr
                            //+20 mmr to the winner and -20 to the loser
                            rankSystem.updateMmr(winnerPlayer, loserPlayer);
                            rankSystem.updateMatchHistory(whitePlayer, blackPlayer, match);


                            System.out.println("Updated ranking: ");
                            System.out.println(rankSystem.showPlayerScore(whitePlayer, blackPlayer));




                            System.out.println();
                            System.out.println("Match completed, press enter to return to menu");
                            sc.nextLine();
                            UI.clearScreen();
                            break;
                        }catch(DataException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Press enter to return to menu");
                            sc.nextLine();
                            UI.clearScreen();
                            break;
                        }catch(NumberFormatException e){
                            System.out.println("INVALID CHARACTER TYPE");
                            System.out.println("Press enter to return to menu");
                            sc.nextLine();
                            UI.clearScreen();
                            break;
                        }





                    case 3:
                        UI.clearScreen();
                        try{System.out.print("Player name: ");
                            String playerName = sc.nextLine();
                            System.out.print("Player ID(numbers only): ");
                            int playerId = Integer.parseInt(sc.nextLine());
                            if(rankSystem.registerPlayer(new Player(playerName, playerId))){
                                System.out.println(UI.ANSI_GREEN + "Player successfully registered." + UI.ANSI_RESET);
                            }
                            System.out.println("Press enter to return to menu");
                            sc.nextLine();
                            UI.clearScreen();
                            break;
                        }catch(DataException e){
                            System.out.println(e.getMessage());
                            System.out.println("Press enter to return to menu");
                            sc.nextLine();
                            UI.clearScreen();
                            break;
                        }




                    case 4:
                        UI.clearScreen();
                        System.out.println("       Ranking");
                        System.out.println();
                        rankSystem.showRanking();
                        System.out.println("Press enter to return to menu");
                        sc.nextLine();
                        UI.clearScreen();
                        break;

                    case 5:
                        UI.clearScreen();

                        try{
                            rankSystem.showLast10Matches();

                            System.out.println(UI.matchDetailsMenu);
                            int opt = Integer.parseInt(sc.nextLine());
                            if(opt == 1){
                                System.out.print("Match code: ");
                                int matchCode = Integer.parseInt(sc.nextLine());
                                UI.clearScreen();
                                rankSystem.showDetailedInfoOfAMatch(matchCode);

                                System.out.println("Press enter to return to menu");
                                sc.nextLine();
                                UI.clearScreen();
                                break;


                            } else if (opt == 2){
                                break;

                            }else{
                                System.out.println("INVALID OPTION! \n" + "Press enter to return to menu." );
                                sc.nextLine();
                                break;

                            }







                        }catch(DataException e){
                            System.out.println(e.getMessage());
                            System.out.println("Press enter to return to menu");
                            sc.nextLine();
                            UI.clearScreen();
                            break;

                        }catch (NumberFormatException e){
                            System.out.println("INVALID OPTION! \n" + "Press enter to return to menu." );
                            sc.nextLine();
                            UI.clearScreen();

                            break;
                        }











                    case 6:




                        exit = true;
                        break;



                    default:
                        System.out.println("INVALID OPTION! \n" + "You will return to main menu.");
                        break;
                }

            }catch (NumberFormatException e){
                System.out.println("INVALID OPTION! \n" + "Press enter to return to menu.");
                sc.nextLine();
                UI.clearScreen();
            }










        }


    }
}

package ranksystem;

import application.UI;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MatchData {
    private int matchCode;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player winner;
    private int totalMoves;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration matchDuration;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter hourFormatter = DateTimeFormatter.ofPattern("HH:mm");


    public MatchData(int matchCode,Player blackPlayer, Player whitePlayer, Player winner, int totalMoves, LocalDateTime startTime, LocalDateTime endTime){
        this.matchCode = matchCode;
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        this.winner = winner;
        this.totalMoves = totalMoves;
        this.startTime = startTime;
        this.endTime = endTime;
        this.matchDuration = Duration.between(getStartTime(), getEndTime());


    }



    public int getMatchCode() {
        return matchCode;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }


    public Player getWinner() {
        return winner;
    }

    public int getTotalMoves() {
        return totalMoves;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String formatedStartDate(){
        return getStartTime().format(dateFormatter);
    }
    public String formatedStartHour(){
        return getStartTime().format(hourFormatter);
    }

    public String formatedEndDate(){
        return getEndTime().format(dateFormatter);
    }

    public String formatedEndHour(){
        return getEndTime().format(hourFormatter);
    }

    public String getDuration(){
        if(matchDuration.toMinutes() < 60){
            return matchDuration.toMinutes() + " minutes";
        }
        return formatMinutes(matchDuration.toMinutes());


    }
    public String formatMinutes(long toMinutes){
        int hours = (int)(toMinutes / 60);
        int minutes = (int)(toMinutes % 60);

        if(minutes == 0){
            return hours + "h";
        }
        return hours + "h" + minutes + "min";


    }


    public String showResumedInfo(){
        return  "-------------------------------------\n" +
                "Match code: " + UI.ANSI_YELLOW + getMatchCode() + UI.ANSI_RESET + "\n" +
                "White Pieces: " + whitePlayer.getName() + "\n" +
                "Black Pieces: " + blackPlayer.getName() + "\n" +
                "Winner: " + winner.getName() + "\n" +
                "Date: " + formatedStartDate() + "\n" +
                "-------------------------------------";
    }

    public String showDetailedInfo(){
        return  "---------------------------------------------------------------\n" +
                "Match: " + UI.ANSI_YELLOW + getMatchCode() + UI.ANSI_RESET + "\n" +
                "Date: " + formatedStartDate() + "\n" +
                "Started at " + formatedStartHour() + "   Ended at " + formatedEndHour() + "\n" +
                "Duration: " + getDuration() + "\n" +
                "Total moves: " + getTotalMoves() + "\n" +
                "White Pieces: " + whitePlayer.getName() + "\n" +
                "White Player MMR: " + whitePlayer.getMmr() + "\n" +
                "Black Pieces: " + blackPlayer.getName() + "\n" +
                "Black Player MMR: " + blackPlayer.getMmr() + "\n" +
                "Winner: " + winner.getName() + "\n" +
                "---------------------------------------------------------------";
    }




}

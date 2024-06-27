package ranksystem;

import java.time.LocalDateTime;

public class MatchData {
    private int matchCode;
    private Player blackPlayer;
    private Player whitePlayer;
    private Player winner;
    private int totalMoves;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public MatchData(int matchCode,Player blackPlayer, Player whitePlayer, Player winner, int totalMoves, LocalDateTime startTime, LocalDateTime endTime){
        this.matchCode = matchCode;
        this.blackPlayer = blackPlayer;
        this.whitePlayer = whitePlayer;
        this.winner = winner;
        this.totalMoves = totalMoves;
        this.startTime = startTime;
        this.endTime = endTime;


    }




}

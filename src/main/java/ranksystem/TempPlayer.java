package ranksystem;

import chess.Color;

public class TempPlayer {
    private int id;
    private Color color;


    public TempPlayer(int id, Color color){
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}


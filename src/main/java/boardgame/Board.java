package boardgame;

public class Board {
    private int rows;
    private int columns;
    private Piece[][] pieces;

    public Board(int rows, int columns){
        if(rows < 1 || columns < 1){
            throw new BoardException("ERROR CREATING BOARD: THERE MUST BE AT LEAST ONE ROW AND ONE COLUMN!");
        }
        this.rows = columns;
        this.columns = columns;
        pieces = new Piece[rows][columns];

    }


    public int getRows() {
        return rows;
    }


    public int getColumns() {
        return columns;
    }

    public Piece piece(int row, int column){
        if(!positionExists(row, column)){
            throw new BoardException("INVALID POSITION");
        }
        return pieces[row][column];
    }


    public Piece piece(Position position){
        if(!positionExists(position)){
            throw new BoardException("INVALID POSITION!");
        }
        return pieces[position.getRow()][position.getColumn()];
    }
    public void placePiece(Piece piece, Position position){
        if(thereIsAPiece(position)){
            throw new BoardException("THERE IS ALREADY A PIECE ON POSITION: " + position);
        }
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }
    public Piece removePiece(Position position){
        if(!positionExists(position)){
            throw new BoardException("INVALID POSITION!");
        }
        if (piece(position) == null){
            return null;
        }
        Piece aux = piece(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }

    public boolean positionExists(int row, int column){
        return row >= 0 && row < rows && column >= 0 && column < columns;

    }
    public boolean positionExists(Position position){
        return positionExists(position.getRow(), position.getColumn());

    }
    public boolean thereIsAPiece(Position position){
        if(!positionExists(position)){
            throw new BoardException("INVALID POSITION!");
        }
        return piece(position) != null;

    }
}

public class Pawn extends Piece
{

    private String symbol;
    public Pawn(char color, double x, double y)
    {
        super("Pawn", color, x, y, true);
        if (color == 'w')
            this.symbol = "[♙ ]";
        else
            this.symbol = "[♟︎ ]";
    }
    public String getSymbol()
    {
        return symbol;
    }
}
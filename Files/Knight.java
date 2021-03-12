public class Knight extends Piece
{

    private String symbol;
    public Knight(char color, double x, double y)
    {
        super("Knight", color, x, y, true);
        if (color == 'w')
            this.symbol = "[♘ ]";
        else
            this.symbol = "[♞ ]";
    }
    public String getSymbol()
    {
        return symbol;
    }
}
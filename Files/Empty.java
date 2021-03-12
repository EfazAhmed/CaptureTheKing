public class Empty extends Piece
{

    private String symbol;
    public Empty(double x, double y)
    {
        super("Empty", x, y);
        symbol = "[  ]";
    }
    public String getSymbol()
    {
        return symbol;
    }
}
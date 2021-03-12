public class Queen extends Piece
{

    private String symbol;
    public Queen(char color, double x, double y)
    {
        super("Queen", color, x, y, true);
        if (color == 'w')
            this.symbol = "[♕ ]";
        else
            this.symbol = "[♛ ]";
    }
    public String getSymbol()
    {
        return symbol;
    }
}
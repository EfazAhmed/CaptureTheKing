public class Bishop extends Piece
{

    private String symbol;
    public Bishop(char color, double x, double y)
    {
        super("Bishop", color, x, y, true);
        if (color == 'w')
            this.symbol = "[♗ ]";
        else
            this.symbol = "[♝ ]";
    }
    public String getSymbol()
    {
        return symbol;
    }
}
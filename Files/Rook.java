public class Rook extends Piece
{

    private String symbol;
    private boolean hasMoved;
    public Rook(char color, double x, double y)
    {
        super("Rook", color, x, y, true);
        if (color == 'w')
            this.symbol = "[♖ ]";
        else
            this.symbol = "[♜ ]";
        hasMoved = false;
    }
    public boolean getHasMoved()
    {
        return hasMoved;
    }
    public void hasMoved()
    {
        hasMoved = true;
    }
    public String getSymbol()
    {
        return symbol;
    }
}
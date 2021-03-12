
public class King extends Piece
{

    private String symbol;
    public King(char color, double x, double y)
    {
        super("King", color, x, y, true);
        if (color == 'w')
            this.symbol = "[♔ ]";
        else
            this.symbol = "[♚ ]";
    }
    public String getSymbol()
    {
        return symbol;
    }
    public static void main(String[]args) {
        String dfa = "" + Math.PI;
        String x = "hi";
        String y = "bye";
		int isBigger = (x.compareTo(y)>0) ? 12 : 0;
    }
}
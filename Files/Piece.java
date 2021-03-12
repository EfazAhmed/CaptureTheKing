import java.awt.geom.Point2D;

public class Piece
{
    private String name;
    private char notation;
    private char color;
    private Point2D coordinates;
    private boolean alive; 
    private boolean hasMoved;
    public Piece(String name, char color, double x, double y, boolean alive)
    {
        coordinates = new Point2D.Double();
        this.name = name;
        this.color = color;
        this.alive = alive;
        coordinates.setLocation(x, y);
    }
    public void setCoordinates(double x, double y)
    {
        coordinates.setLocation(x, y);
    }
    public Piece(String name, double x, double y)
    {
        coordinates = new Point2D.Double();
        coordinates.setLocation(x, y);
        this.name = name;
    }
    public String getName()
    {
        return name;
    }
    public char getNotation()
    {
        return notation;
    }
    public char getColor()
    {
        return color;
    }
    public boolean getAlive()
    {
        return alive;
    }
    public String getSymbol()
    {
        return "lol";
    }
    public Point2D getCoordinates()
    {
        return coordinates;
    }
    public boolean getHasMoved()
    {
        return hasMoved;
    }
    public void hasMoved()
    {
        hasMoved = true;
    }
    public String toString()
    {
        return getName() + " " + getColor() + " " + (int)getCoordinates().getX() + " " + (int)getCoordinates().getY();
    }


}
/*
white chess king	♔	U+2654	&#9812;	&#x2654;
white chess queen	♕	U+2655	&#9813;	&#x2655;
white chess rook	♖	U+2656	&#9814;	&#x2656;
white chess bishop	♗	U+2657	&#9815;	&#x2657;
white chess knight	♘	U+2658	&#9816;	&#x2658;
white chess pawn	♙	U+2659	&#9817;	&#x2659;
black chess king	♚	U+265A	&#9818;	&#x265A;
black chess queen	♛	U+265B	&#9819;	&#x265B;
black chess rook	♜	U+265C	&#9820;	&#x265C;
black chess bishop	♝	U+265D	&#9821;	&#x265D;
black chess knight	♞	U+265E	&#9822;	&#x265E;
black chess pawn	♟︎	U+265F	&#9823;	&#x265F;
*/


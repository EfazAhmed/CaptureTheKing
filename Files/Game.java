import java.util.Scanner;
import java.awt.geom.Point2D;
import java.lang.Math;
import java.util.ArrayList;


public class Game{

    public static Piece[][] board;
	public static char turn = 'w';
    public static boolean wKingMove = false;
    public static boolean bKingMove = false;
    public static Piece savePiece;
    public static Point2D saveSquare;
    public static Point2D deleteSquare;
    public static boolean castlingRightNow = false;
    public static boolean deletingSquareRightNow = false;
    public static ArrayList<String> moves = new ArrayList<String>();
    public static String winner = null;
    public static long start;
    public static long end;
    public static long timeRemainingWhite = 10;
    public static long timeRemainingBlack = 10;
    public static int time;
    
    public static void main(String[] args){
        makeBoard();
        Scanner w = new Scanner(System.in);
        Scanner b = new Scanner(System.in);
        Scanner p = new Scanner(System.in);
        reset();
        while(winner == null){
            makeTurn('w');
            printBoard();
            String again = "";
            if(time != -60){
                System.out.println("❥ Time Remaining for White: " + getTimeRemaining('w') + "s");
                System.out.println("❥ Time Remaining for Black: " + getTimeRemaining('b') + "s");
            }
            System.out.print("❥ White Move: ");
            start = System.currentTimeMillis();
            String white = w.nextLine(); 
            
            while(!isInputValid(white) || !canMove(coordinatesToPiece(inputToCoordinates(white.substring(0,2))), inputToCoordinates(white.substring(2,4)))){
                printBoard();
                if(!isInputValid(white)){
                    System.out.println("❥ I can't seem to understand what you are saying.");
                }
                else{
                    System.out.println("❥ The move you are trying to make seems impossible.");
                }
                if(time != -60){
                    System.out.println("❥ Time Remaining for White: " + getTimeRemaining('w') + "s");
                    System.out.println("❥ Time Remaining for Black: " + getTimeRemaining('b') + "s");
                }
                System.out.print("❥ White Try Again: ");
                white = w.nextLine();
            }

            end = System.currentTimeMillis();
            setTimeRemaining('w', (end - start) / 1000);

            if (getTimeRemaining('w') <= 0 && time != -60)
            {
                System.out.println("❤ Black wins on time! ❤"); 
                System.out.println("❥ Do you want to play again?(Y/N) ");
                again = p.nextLine();
                if(again.toUpperCase().equals("Y") || again.toUpperCase().equals("YES")){
                    reset();
                    continue;
                }
                else{
                    break;
                }
            }

            move(coordinatesToPiece(inputToCoordinates(white.substring(0,2))), inputToCoordinates(white.substring(2,4)));
			moves.add(white);
            if(isGameWon('b')){  
                printBoard();             
                System.out.println(winner + " wins!"); 
                System.out.println("❥ Do you want to play again?(Y/N) ");
                again = p.nextLine();
                if(again.toUpperCase().equals("Y") || again.toUpperCase().equals("YES")){
                    reset();
                    continue;
                }
                else{
                    break;
                }
            }
            
            makeTurn('b');
            printBoard();
            if(time != -60){
                System.out.println("❥ Time Remaining for White: " + getTimeRemaining('w') + "s");
                System.out.println("❥ Time Remaining for Black: " + getTimeRemaining('b') + "s");
            }
            System.out.print("❥ Black Move: ");
            start = System.currentTimeMillis();
            String black = b.nextLine();
            while(!isInputValid(black) || !canMove(coordinatesToPiece(inputToCoordinates(black.substring(0,2))), inputToCoordinates(black.substring(2,4)))){
                printBoard();
                if(!isInputValid(black)){
                    System.out.println("❥ I can't seem to understand what you are saying.");
                }
                else{
                    System.out.println("❥ The move you are trying to make seems impossible.");
                }
                if(time != -60){
                    System.out.println("❥ Time Remaining for White: " + getTimeRemaining('w') + "s");
                    System.out.println("❥ Time Remaining for Black: " + getTimeRemaining('b') + "s");
                }
                System.out.print("❥ Black Try Again: ");
                black = b.nextLine();
            }

            end = System.currentTimeMillis();
            if(time != -60){
                setTimeRemaining('b', (end - start) / 1000);
            }
            
            if (getTimeRemaining('b') <= 0 && time != -60)
            {
                System.out.println("❤ White wins on time! ❤"); 
                System.out.println("❥ Do you want to play again?(Y/N) ");
                again = p.nextLine();
                if(again.toUpperCase().equals("Y") || again.toUpperCase().equals("YES")){
                    reset();
                    continue;
                }
                else{
                    break;
                }
            }

            move(coordinatesToPiece(inputToCoordinates(black.substring(0,2))), inputToCoordinates(black.substring(2,4)));
            moves.add(black);
            if(isGameWon('w')){               
                //winner
                printBoard();
                System.out.println(winner + " wins!"); 
                System.out.println("❥ Do you want to play again?(Y/N) ");
                again = p.nextLine();
                if(again.toUpperCase().equals("Y") || again.toUpperCase().equals("YES")){
                    reset();
                    continue;
                }
                else{
                    break;
                }
            }
        }    
        System.out.println("Thanks for playing!");
    }

    public static void setTime(int x)
    {
        timeRemainingWhite = x;
        timeRemainingBlack = x;
    }
    public static double getTimeRemaining(char color)
    {
        if (color == 'w')
        {
            return timeRemainingWhite;
        }
        else
        {
            return timeRemainingBlack;
        }
    }
    public static void setTimeRemaining(char color, long x)
    {
        if (color == 'w')
        {
            timeRemainingWhite = timeRemainingWhite - x;
        }
        else
        {
            timeRemainingBlack = timeRemainingBlack - x;
        }
    }
    
    public static void reset(){
        Scanner q = new Scanner(System.in);
        makeBoard();
	    turn = 'w';
        wKingMove = false;
        bKingMove = false;
        castlingRightNow = false;
        deletingSquareRightNow = false;
        moves = new ArrayList<String>();
        winner = null;
        System.out.print("❥ How much minutes would you like each player to have. Enter -1 if you don't want to play with a clock: ");
        int time = q.nextInt();
        while(time != -1 && time <= 0)
        {
            System.out.print("❥ Please enter a number greater than 0: ");
            time = q.nextInt();
        }
        time = 60 * time;
        setTime(time);
    }

    public static boolean getHasKingMoved()
    {
        return board[(int)findKing(getTurn()).getY()][(int)findKing(getTurn()).getX()].getHasMoved();
    }
    
    public static void kingMoved()
    {
    	board[(int)findKing(getTurn()).getY()][(int)findKing(getTurn()).getX()].hasMoved();
    }
  
    public static void makeTurn(char c)
    {
    		turn = c;
    }
    
    public static char getTurn()
    {
    		return turn;
    }

    public static Point2D inputToCoordinates(String input)
    {
        Point2D p = new Point2D.Double();
        char letter = input.charAt(0);
        int number = Integer.parseInt(input.substring(1, 2));
        p.setLocation((double)(int)letter - 97, (double)8 - number);
        return p;
      }
      
    public static Piece coordinatesToPiece(Point2D coordinates)
    {
    	int j = (int)coordinates.getX();
        int i = (int)coordinates.getY();
    		return board[i][j];
    }
    
    public static boolean isThisMyPiece(Piece piece)
    {
    		return piece.getColor() == turn;
    }
    
    public static boolean isInputValid(String input){
    	if(input.length() == 4){
        	char c1 = input.charAt(0);
            char c2 = input.charAt(1);
            char c3 = input.charAt(2);
            char c4 = input.charAt(3);
            
            if((int)c1 >= 97 && (int)c1 <= 104){
            	if((int)c3 >= 97 && (int)c3 <= 104){
            		if((int)c2 <= 56 && (int)c2 >= 49){
                    	if((int)c4 <= 56 && (int)c4 >= 49){
                        	return true;
                    	}
                    }
				}
			}   
        }
        return false;
    }
    
    public static boolean isOnBoard(Point2D square){
    	if((int)square.getX() < 8 && (int)square.getX() >= 0){
        	if((int)square.getY() < 8 && (int)square.getY() >= 0){
        		return true;
        	}		
        }
        return false;
    }

    public static void promotePawn(Piece piece)
    {
        if (piece.getName().equals("Pawn"))
        {
            if (piece.getColor() == 'w')
            {
                if ((int)piece.getCoordinates().getY() == 0)
                {
                    board[(int)piece.getCoordinates().getY()][(int)piece.getCoordinates().getX()] = new Queen('w', (int)piece.getCoordinates().getX(), (int)piece.getCoordinates().getY());
                }
            }
            else
            {
                if ((int)piece.getCoordinates().getY() == 7)
                {
                    board[(int)piece.getCoordinates().getY()][(int)piece.getCoordinates().getX()] = new Queen('b', (int)piece.getCoordinates().getX(), (int)piece.getCoordinates().getY());
                }
            }
        }
    }

    // files -- X
    // ranks -- Y
    public static boolean canPawnMove(Piece piece, Point2D square)
    {
        if (piece.getName().equals("Pawn"))
        {
            //White
            if (piece.getColor() == 'w')
            {
                if (square.getY() > piece.getCoordinates().getY()){
                    return false;
                }
                if (square.getX() == piece.getCoordinates().getX()) //on same file
                {
                    if ((int)piece.getCoordinates().getY() == 6) //on second rank
                    {
                        if (square.getY() < 4){ //if pawn wants to move further than 2 squares
                            return false; 
                        }            
                        else
                        {
                        	if ((int)square.getY() == 4)
                            {
                            	if (!(board[(int)square.getY() + 1][(int)square.getX()].getSymbol().equals("[  ]") && board[(int)square.getY()][(int)square.getX()].getSymbol().equals("[  ]"))){ //empty squares
                                	return false;
                                }
                            }
                            else
                            {
                            	if (!board[(int)square.getY()][(int)square.getX()].getSymbol().equals("[  ]")){ //empty squares
                                    return false;
                                }
                            }
                        }
                    }
                    else
                    {
                        if (Math.abs(square.getY() - piece.getCoordinates().getY()) > 1){ // if pawn wants to move more than 1 square
                            return false; 
                        }
                        else
                        {
                            if (!board[(int)square.getY()][(int)square.getX()].getSymbol().equals("[  ]")){ //empty squares
                                return false;
                            }
                        }
                    }
                }
                else
                {
                    if (!canPawnTake(piece, square));{
                        return true;
                    }
                }
            }
            //Black
            else
            {
                if (square.getY() < piece.getCoordinates().getY()){
                    return false;
                }
                if (square.getX() == piece.getCoordinates().getX()) //on same file
                {
                    if ((int)piece.getCoordinates().getY() == 1) //on seventh rank
                    {
                        if (square.getY() > 3){ //if pawn wants to move further than 2 squares
                            return false; 
                        }            
                        else
                        {
                        	if ((int)square.getY() == 3)
                            {
                            	if (!(board[(int)square.getY() - 1][(int)square.getX()].getSymbol().equals("[  ]") && board[(int)square.getY()][(int)square.getX()].getSymbol().equals("[  ]"))){ //empty squares
                                	return false;
                                }
                            }
                            else
                            {
                            	if (!board[(int)square.getY()][(int)square.getX()].getSymbol().equals("[  ]")){ //empty squares
                                    return false;
                                }
                            }
                        }
                    }
                    else
                    {
                        if (Math.abs(square.getY() - piece.getCoordinates().getY()) > 1){ // if pawn wants to move more than 1 square
                            return false; 
                        }
                        else
                        {
                            if (!board[(int)square.getY()][(int)square.getX()].getSymbol().equals("[  ]")){ //empty squares
                                return false;
                            }
                        }
                    }
                }
                else
                {
                    if (!canPawnTake(piece, square)) {
                        return true;
                    }
                }
            }
        }
        return true;
    }
    
    public static boolean canPawnTake(Piece piece, Point2D square)
    {
    	Point2D x = new Point2D.Double();
        Point2D y = new Point2D.Double();
        x.setLocation(1,1);
        y.setLocation(2,2);
        double pawnCaptureConstant = x.distance(y);
        if (piece.getColor() == findPiece(square).getColor())
        {
            return false;
        }
    	//White
        if (getTurn() == 'w') {
            // en passent
            if (square.distance(piece.getCoordinates()) == pawnCaptureConstant && square.getY() < piece.getCoordinates().getY() && board[(int)square.getY()][(int)square.getX()].getName().equals("Empty"))
            {
                String str = moves.get(moves.size() - 1);
                char c = str.charAt(1);
                char z = str.charAt(3);
                Piece p = coordinatesToPiece(inputToCoordinates(str.substring(2)));
                if (z - c != -2 || !p.getName().equals("Pawn") || square.getX() != p.getCoordinates().getX() || square.getY() - p.getCoordinates().getY() != -1){
                    return false;
                }
                else
                {
                    setEnPassentPawn(p.getCoordinates());
                    return true;
                }
            }
            if (board[(int)square.getY()][(int)square.getX()].getName().equals("Empty") || square.distance(piece.getCoordinates()) != pawnCaptureConstant || square.getY() > piece.getCoordinates().getY() || board[(int)square.getY()][(int)square.getX()].getColor() == 'w'){
                return false;
            }
            else{
                return true;
            }
        }
        //Black
        else if (getTurn() == 'b') {
            if (square.distance(piece.getCoordinates()) == pawnCaptureConstant && square.getY() > piece.getCoordinates().getY() && board[(int)square.getY()][(int)square.getX()].getName().equals("Empty"))
            {
                String str = moves.get(moves.size() - 1);
                char c = str.charAt(1);
                char z = str.charAt(3);
                Piece p = coordinatesToPiece(inputToCoordinates(str.substring(2)));
                if (z - c != 2 || !p.getName().equals("Pawn") || square.getX() != p.getCoordinates().getX() || square.getY() - p.getCoordinates().getY() != 1)
                    return false;
                else
                {
                    setEnPassentPawn(p.getCoordinates());
                    return true;
                }
            }
            if (board[(int)square.getY()][(int)square.getX()].getName().equals("Empty") || square.distance(piece.getCoordinates()) != pawnCaptureConstant || square.getY() < piece.getCoordinates().getY() || board[(int)square.getY()][(int)square.getX()].getColor() == 'b'){
                return false;
            }
            else{ 
                return true;
            }
        }
        else{
            return true;
        }
    }
    public static void takeEnPassentPawn()
    {
        board[(int)deleteSquare.getY()][(int)deleteSquare.getX()] = new Piece("Empty", (int)deleteSquare.getX(), (int)deleteSquare.getY());
    }

    public static void setEnPassentPawn(Point2D square)
    {
        deletingSquareRightNow = true;
        deleteSquare = square;
    }
    public static boolean canKnightMove(Piece piece, Point2D square)
    {
    	Point2D x = new Point2D.Double();
        Point2D y = new Point2D.Double();
        x.setLocation(0, 0);
        y.setLocation(1, 2);
        double distance = x.distance(y);
        if (piece.getCoordinates().distance(square) != distance)
        	return false;
        if (isThisMyPiece(board[(int)square.getY()][(int)square.getX()]))
        	return false;
        return true;
    }
    public static boolean canCastle(Piece piece, Point2D square)
    {
    	if(getHasKingMoved()){
            return false;
        }
        int i = (int)piece.getCoordinates().getX();
        int dir;
        if (i > square.getX())
        	dir = -1;
        else
            dir = 1;
        i += dir;
        while(i != square.getX())
        {
            if (!board[(int)piece.getCoordinates().getY()][i].getName().equals("Empty")){
                return false;
            }
            i += dir;
        }
        int castleShort = 7; // i + dir; // dir
        int castleLong = 0; // i + dir + dir; // dir
        if (dir > 0) // Castle Shorte
        {
            if ((board[(int)piece.getCoordinates().getY()][castleShort].getName().equals("Rook") && board[(int)piece.getCoordinates().getY()][castleShort].getHasMoved() == false))
            {
                if (getTurn() == 'w')
                {
                    Point2D p = new Point2D.Double(); //h1 rook
                    p.setLocation(5, 7);
                    setCastlingRook(board[7][7], p);
                  
                }
                else
                {
                    Point2D q = new Point2D.Double(); //h8 rook
                    q.setLocation(5, 0);
                    setCastlingRook(board[0][7], q);
                }
            }
            else
            {
                return false;
            }

        }
        else
        {
            if ((board[(int)piece.getCoordinates().getY()][castleLong].getName().equals("Rook") && board[(int)piece.getCoordinates().getY()][castleLong].getHasMoved() == false)){
                if (getTurn() == 'w')
                {
                    Point2D r = new Point2D.Double(); // a1 rook -- d1 rook
                    r.setLocation(3, 7);
            	    setCastlingRook(board[7][0], r); 
                	
                }
                else
                {
                	Point2D s = new Point2D.Double(); // a8 rook
                	s.setLocation(3, 0);
                    setCastlingRook(board[0][0], s);
                }
            }
            else
            {
                return false;
            }
        }
    return true;
    }
    public static void setCastlingRook(Piece piece, Point2D square)
    {
    	savePiece = piece;
        saveSquare = square;
    }
    public static void castlingRook()
    {
    		forceMove(savePiece, saveSquare);
    } 
    public static boolean canKingMove(Piece piece, Point2D square) 
    {
    	if (!(Math.abs(piece.getCoordinates().getX() - square.getX()) <= 1 && Math.abs(piece.getCoordinates().getY() - square.getY()) <=1)){
            if(Math.abs(square.getX() - piece.getCoordinates().getX()) == 2)
            {
                if(!canCastle(piece, square)){
                    return false;
                }
                else{
                    castlingRightNow = true;
                    return true;
                }
            }
            return false;
        }
        
        return true;
    }

    public static boolean canQueenMove(Piece piece, Point2D square)
    {
        return canRookMove(piece, square) || canBishopMove(piece, square);
    }

    public static boolean canRookMove(Piece piece, Point2D square)
    {
    	if (piece.getCoordinates().getY() != square.getY() && piece.getCoordinates().getX() != square.getX()){
            return false;
        }
        int pieceX, pieceY, squareX, squareY, start, end, dir, constant;
        pieceX = (int)piece.getCoordinates().getX();
        pieceY = (int)piece.getCoordinates().getY();
        squareX = (int)square.getX();
        squareY = (int)square.getY();
        
        if (pieceX == squareX)
        {
        	constant = pieceX;
            start = pieceY;
            end = squareY;
        	if (pieceY < squareY)
            {
                dir = 1;
            }
            else
            {
                dir = -1;
            }
        }
        else
        {
        	constant = pieceY;
            start = pieceX;
            end = squareX;
            if (pieceX < squareX)
            {
                dir = 1;
            }
            else
            {
                dir = -1;
            }
        }
        
        int i = start + dir;
    	while(i != end)
        {
        	if (constant == pieceX)
            {
            	if (isThisMyPiece(board[i][constant])){
              	    return false;
                }
              	else if (!board[i][constant].getName().equals("Empty")) //if the square has an enemy piece
                {
                	if (pieceX == squareX && pieceY == squareY)
                    {
                    	return true;
                    }
                	return false;
                }
                else
                {
                		if (pieceX == squareX && pieceY == squareY)
                    	return true;
                }
            
            }
            else
            {
            	if (isThisMyPiece(board[constant][i])){
              	    return false;
                }
              	else if (!board[constant][i].getName().equals("Empty")) //if the square has an enemy piece
                {
                	if (pieceX == squareX && pieceY == squareY)
                    {
                    	return true;
                    }
                	return false;
                }
                else
                {
                		if (pieceX == squareX && pieceY == squareY)
                    	return true;
                }
            }
            i += dir;
        }
        if(isThisMyPiece(board[squareY][squareX])){
            return false;
        }
        return true;
    }
    public static boolean hasRookMoved(Rook piece)
    {
    	return piece.getHasMoved();
    }
    public static void rookMoved(Piece piece)
    {
        if(piece.getName().equals("Rook")){
            piece.hasMoved();
        }
    }
    
    public static Piece findPiece(Point2D square)
    {
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j].getCoordinates().equals(square)){
                    return board[i][j];
                }
            }
        }
        return null;
    }

    public static boolean isGameWon(char color)
    {
        if (findKing(color) == null)
        {
            if (color == 'b')
            {
                winner = "White";
            }
            else
            {
                winner = "Black";
            }
            return true;
        }
        return false;
    }

    public static Point2D findKing(char color){
        Point2D king = new Point2D.Double();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                if(board[i][j].getName().equals("King") && board[i][j].getColor() == color){
                    king.setLocation(board[i][j].getCoordinates());
                    return king;
                }
            }
        }
        return null;
    }
    
    public static boolean canBishopMove(Piece piece, Point2D square){
        int x = (int)piece.getCoordinates().getX();
        int y = (int)piece.getCoordinates().getY();
        //topright
        int varX = x + 1;
        int varY = y - 1;
        while(varX <= 7 && varY >=0){
        	if (isThisMyPiece(board[varY][varX]))
            	break;
            if (board[varY][varX].getName().equals("Empty") && ((int)square.getX() == varX && (int)square.getY() == varY))
            	return true;
            if(!isThisMyPiece(board[varY][varX]) && !board[varY][varX].getName().equals("Empty")) {
                if ((int)square.getX() == varX && (int)square.getY() == varY)
					return true;
                else
                	break;
            }
            else{
                varX += 1;
                varY -= 1;
            }
        }
        //topleft
        varX = x - 1;
        varY = y - 1;
        while(varX >= 0 && varY >=0){
            if (isThisMyPiece(board[varY][varX]))
            	break;
            if (board[varY][varX].getName().equals("Empty") && ((int)square.getX() == varX && (int)square.getY() == varY))
            	return true;
            if(!isThisMyPiece(board[varY][varX]) && !board[varY][varX].getName().equals("Empty")) {
                if ((int)square.getX() == varX && (int)square.getY() == varY)
									return true;
                else
                	break;
            }
            else{
                varX -= 1;
                varY -= 1;
            }
        }
        //bottomright
        varX = x + 1;
        varY = y + 1;
        while(varX <= 7 && varY <= 7){
            if (isThisMyPiece(board[varY][varX]))
            	break;
            if (board[varY][varX].getName().equals("Empty") && ((int)square.getX() == varX && (int)square.getY() == varY))
            	return true;
            if(!isThisMyPiece(board[varY][varX]) && !board[varY][varX].getName().equals("Empty")) {
                if ((int)square.getX() == varX && (int)square.getY() == varY)
									return true;
                else
                	break;
            }
            else{
                varX += 1;
                varY += 1;
            }
        }
        //bottomleft
        varX = x - 1;
        varY = y + 1;
        while(varX >= 0 && varY <= 7){
            if (isThisMyPiece(board[varY][varX]))
            	break;
            if (board[varY][varX].getName().equals("Empty") && ((int)square.getX() == varX && (int)square.getY() == varY))
            	return true;
            if(!isThisMyPiece(board[varY][varX]) && !board[varY][varX].getName().equals("Empty")) {
                if ((int)square.getX() == varX && (int)square.getY() == varY)
									return true;
                else
                	break;
            }
            else{
                varX -= 1;
                varY += 1;
            }
        }
        return false;   
    }

    public static boolean canMove(Piece piece, Point2D square)
    {
    	if (!isThisMyPiece(piece)  || piece.getCoordinates().equals(square))
        {
            if (!isThisMyPiece(piece))
                System.out.println("Cannot move b/c not piece");
            else{
                System.out.println("Cannot move b/c square is where piece is");
            }
        	return false;
                
        }
        if (piece.getName().equals("Pawn"))
        {
            if (!canPawnMove(piece, square)){
                return false;
            }
            else{
                return true;
            }
        }
        else if (piece.getName().equals("Knight"))
        {
            if (!canKnightMove(piece, square)) {
                return false;
            }
            else {
                return true;
            }
        }
        else if (piece.getName().equals("Bishop"))
        {
        	if (!canBishopMove(piece, square)) {
                return false;
            }
            else {
                return true;
            }
        }
        else if (piece.getName().equals("Rook"))
        {
        	if (!canRookMove(piece, square))
            	return false;
            else
            	return true;
        }
        else if (piece.getName().equals("Queen"))
        {
        	if (!canQueenMove(piece, square))
            	return false;
            else
            	return true;
        }
        else if (piece.getName().equals("King"))
        {
        	if (!canKingMove(piece, square))
            	return false;
            else
            	return true;
        }
        return false; 
    }

    public static void forceMove(Piece piece, Point2D square)
    {
    	Piece temp = piece;
        board[(int)piece.getCoordinates().getY()][(int)piece.getCoordinates().getX()] = new Empty((int)piece.getCoordinates().getX(), (int)piece.getCoordinates().getY());
        piece.setCoordinates(square.getX(), square.getY());
        board[(int)square.getY()][(int)square.getX()] = temp;
    }
    
    public static void move(Piece piece, Point2D square)
    {
            Piece temp = piece;
            board[(int)piece.getCoordinates().getY()][(int)piece.getCoordinates().getX()] = new Empty((int)piece.getCoordinates().getX(), (int)piece.getCoordinates().getY());
            piece.setCoordinates(square.getX(), square.getY());
            board[(int)square.getY()][(int)square.getX()] = temp;
            if (piece.getName().equals("Rook")){
                rookMoved(piece);
            }
            if (piece.getName().equals("King")){
                kingMoved();
                if(castlingRightNow){
                    castlingRook();
                    castlingRightNow = false;
                }
            }
            if (piece.getName().equals("Pawn")){
                promotePawn(piece);
                if(deletingSquareRightNow){
                    takeEnPassentPawn();
                    deletingSquareRightNow = false;
                }
            }   
    }
    
    public static void printBoard(){
        if(turn == 'w'){
            System.out.println("     ♥ ~ ♥ ~ ♥ ~ B L A C K ~ ♥ ~ ♥ ~ ♥  ");
            System.out.println("      a   b   c   d   e   f   g   h");
            for(int i = 0; i < 8; i++){
                System.out.print("❤  " + (8-i) + " ");
                for(int j = 0; j < 8; j++){
                    if(board[i][j] == null){
                        System.out.print(i);
                        System.out.print(j);
                    }
                    System.out.print(board[i][j].getSymbol());
                }
                System.out.println(" " + (8-i) + " ❤");
            }
            System.out.println("      a   b   c   d   e   f   g   h");
            System.out.println("     ♥ ~ ♥ ~ ♥ ~ W H I T E ~ ♥ ~ ♥ ~ ♥  ");
        }
        else if(turn == 'b'){
            System.out.println("     ♥ ~ ♥ ~ ♥ ~ W H I T E ~ ♥ ~ ♥ ~ ♥  ");
            System.out.println("      h   g   f   e   d   c   b   a");
            for(int i = 7; i >= 0; i--){
                System.out.print("❤ " + (8-i) + " ");
                for(int j = 7; j >= 0; j--){
                    if(board[i][j] == null){
                        System.out.print(i);
                        System.out.print(j);
                    }
                    System.out.print(board[i][j].getSymbol());
                }
                System.out.println(" " + (8-i) + " ❤");
            }
            System.out.println("      h   g   f   e   d   c   b   a");
            System.out.println("     ♥ ~ ♥ ~ ♥ ~ B L A C K ~ ♥ ~ ♥ ~ ♥  ");
        }
    }
    public static String coordinatesToString(Point2D square)
    {
        char letter = (char)(int)square.getX();
        int number = (int)square.getY();
        return "" + (char)(letter + 97) + (8 - number);
    }
    
    public static void makeBoard(){
        board = new Piece[8][8];
        for(int i = 2; i < 6; i++){
            for(int j =0; j < 8; j++){
                board[i][j] = new Empty(j, i);
            }
        }
        for(int i = 0; i < 8; i++){
            board[1][i] = new Pawn('b', i, 1);
            board[6][i] = new Pawn('w', i, 6);
        }
        board[0][0] = new Rook('b', 0, 0);
        board[0][7] = new Rook('b', 7, 0);
        board[7][0] = new Rook('w', 0, 7);
        board[7][7] = new Rook('w', 7, 7);
        board[0][1] = new Knight('b', 1, 0);
        board[0][6] = new Knight('b', 6, 0);
        board[7][1] = new Knight('w', 1, 7);
        board[7][6] = new Knight('w', 6, 7);
        board[0][2] = new Bishop('b', 2, 0);
        board[0][5] = new Bishop('b', 5, 0);
        board[7][2] = new Bishop('w', 2, 7);
        board[7][5] = new Bishop('w', 5, 7);
        board[0][4] = new King('b', 4, 0);
        board[7][4] = new King('w', 4, 7);
        board[0][3] = new Queen('b', 3, 0);
        board[7][3] = new Queen('w', 3, 7);

        //board[0][4] = new Empty(4, 0);
        //board[7][4] = new Empty(4, 7);


        // board[0][1] = new Empty(1, 0);
        // board[0][6] = new Empty(6, 0);
        // board[7][1] = new Empty(1, 7);
        // board[7][6] = new Empty(6, 7);
        // board[0][2] = new Empty(2, 0);
        // board[0][5] = new Empty(5, 0);
        // board[7][2] = new Empty(2, 7);
        // board[7][5] = new Empty(5, 7);
        // board[0][3] = new Empty(3, 0);
        // board[7][3] = new Empty(3, 7);
    }
}
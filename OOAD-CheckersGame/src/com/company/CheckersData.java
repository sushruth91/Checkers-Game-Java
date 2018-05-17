package com.company;


import java.util.ArrayList;
import java.util.Stack;

/**
 * An object of this class holds data about a game of checkers.
 * It knows what kind of piece is on each square of the checkerboard.
 * Note that RED moves "up" the board (i.e. row number decreases)
 * while BLACK moves "down" the board (i.e. row number increases).
 * Methods are provided to return lists of available legal moves.
 */
public class CheckersData {
    /*  The following constants represent the possible contents of a square
    on the board.  The constants RED and BLACK also represent players
    in the game. */
     static final int
            EMPTY      = 0,
            RED        = 1,
            RED_KING   = 2,
            BLACK      = 3,
            BLACK_KING = 4;

    Integer[][] board;  // board[r][c] is the contents of row r, column c.
    Stack<ArrayList<Integer>> previousState = new Stack<>();


    /**
     * Constructor.  Create the board and set it up for a new game.
     */
    CheckersData() {

        board = new Integer[8][8];
        setUpGame();
    }//end of constructor

    public void currentState() {
        ArrayList<Integer> List1 = new  ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                List1.add(board[row][col]);
            }
        }
        if(Board.validMove){
        previousState.push(List1);}

    }
    public void getBoardStatus() {
        ArrayList<Integer> List1 ;
        int j = 0;


        List1 =  previousState.pop();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col] = List1.get(col + j);
            }
            j = j + 8;
        }
    }

    /**
     * Set up the board with checkers in position for the beginning
     * of a game.  Note that checkers can only be found in squares
     * that satisfy  row % 2 == col % 2.  At the start of the game,
     * all such squares in the first three rows contain black squares
     * and all such squares in the last three rows contain red squares.
     */
    void setUpGame() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ( row % 2 == col % 2 ) {
                    if (row < 3) {
                        board[row][col] = BLACK;
                    }
                    else if (row > 4) {
                        board[row][col] = RED;
                    }
                    else {
                        board[row][col] = EMPTY;
                    }
                }
                else {
                    board[row][col] = EMPTY;
                }
            }
        }
    }//end of method setUpGame

    /**
     * Return the contents of the square in the specified row and column.
     */
    int pieceAt(int row, int col) {
        return board[row][col];
    }//end of method pieceAt



    /**
     * Make the specified move.  It is assumed that move
     * is non-null and that the move it represents is legal.
     */
    void makeMove(CheckersMove move) {
        makeMove(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());
    }//end of method makeMove with ChekcersMove object as an argument

    /**
     * Make the move from (fromRow,fromCol) to (toRow,toCol).  It is
     * assumed that this move is legal.  If the move is a jump, the
     * jumped piece is removed from the board.  If a piece moves
     * the last row on the opponent's side of the board, the
     * piece becomes a king.
     */
    private void makeMove(int fromRow, int fromCol, int toRow, int toCol) {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = EMPTY;
        if (fromRow - toRow == 2 || fromRow - toRow == -2) {
            // The move is a jump.  Remove the jumped piece from the board.
            int jumpRow = (fromRow + toRow) / 2;  // Row of the jumped piece.
            int jumpCol = (fromCol + toCol) / 2;  // Column of the jumped piece.
            board[jumpRow][jumpCol] = EMPTY;
        }
        if (toRow == 0 && board[toRow][toCol] == RED) {
            board[toRow][toCol] = RED_KING;
        }
        if (toRow == 7 && board[toRow][toCol] == BLACK) {
            board[toRow][toCol] = BLACK_KING;
        }
    }//end of method makeMove with 4 int arguments

    /**
     * Return an array containing all the legal CheckersMoves
     * for the specfied player on the current board.  If the player
     * has no legal moves, null is returned.  The value of player
     * should be one of the constants RED or BLACK; if not, null
     * is returned.  If the returned value is non-null, it consists
     * entirely of jump moves or entirely of regular moves, since
     * if the player can jump, only jumps are legal moves. Here we also check whether the jump is
     * 1 jump/ 2 jumps / 3 jumps-- possible when the player has continous jumps
     */
    protected CheckersMove[] getLegalMoves(int player) {
        if (player != RED && player != BLACK) {
            return null;
        }

        int playerKing;  // The constant representing a King belonging to player.

        if (player == RED) {
            playerKing = RED_KING;
        }
        else {
            playerKing = BLACK_KING;
        }

        ArrayList<CheckersMove> moves = new ArrayList<>();  // Moves will be stored in this list.
       /*  First, check for any possible jumps.  Look at each square on the board.
        If that square contains one of the player's pieces, look at a possible
        jump in each of the four directions from that square.  If there is
        a legal jump in that direction, put it in the moves ArrayList.
        */
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == player || board[row][col] == playerKing) {
                    if (canJump(player, row, col, row+1, col+1, row+2, col+2)) {
                        if(canJump(player, row+2, col+2, row+3, col+3, row+4, col+4)) {
                            if(canJump(player, row+4, col+4, row+5, col+5, row+6, col+6)) {
                                moves.add(new CheckersMove(row, col, row+6, col+6));
                            } else {
                                moves.add(new CheckersMove(row, col, row+4, col+4));
                            }
                        } else {
                            moves.add(new CheckersMove(row, col, row+2, col+2));
                        }
                    }
                    if (canJump(player, row, col, row-1, col+1, row-2, col+2)) {
                        if(canJump(player, row-2, col+2, row-3, col+3, row-4, col+4)) {
                            if(canJump(player, row-4, col+4, row-5, col+5, row-6, col+6)) {
                                moves.add(new CheckersMove(row, col, row-6, col+6));
                            } else {
                                moves.add(new CheckersMove(row, col, row-4, col+4));
                            }
                        } else {
                            moves.add(new CheckersMove(row, col, row-2, col+2));
                        }
                    }
                    if (canJump(player, row, col, row+1, col-1, row+2, col-2)) {
                        if(canJump(player, row+2, col-2, row+3, col-3, row+4, col-4)) {
                            if(canJump(player, row+4, col-4, row+5, col-5, row+6, col-6)) {
                                moves.add(new CheckersMove(row, col, row+6, col-6));
                            } else {
                                moves.add(new CheckersMove(row, col, row+4, col-4));
                            }
                        } else {
                            moves.add(new CheckersMove(row, col, row+2, col-2));
                        }
                    }
                    if (canJump(player, row, col, row-1, col-1, row-2, col-2)) {
                        if(canJump(player, row-2, col-2, row-3, col-3, row-4, col-4)) {
                            if(canJump(player, row-4, col-4, row-5, col-5, row-6, col-6)) {
                                moves.add(new CheckersMove(row, col, row-6, col-6));
                            } else {
                                moves.add(new CheckersMove(row, col, row-4, col-4));
                            }
                        } else {
                            moves.add(new CheckersMove(row, col, row-2, col-2));
                        }
                    }
                }
            }
        }
       /*  If any jump moves were found, then the user must jump, so we don't
        add any regular moves.  However, if no jumps were found, check for
        any legal regualar moves.  Look at each square on the board.
        If that square contains one of the player's pieces, look at a possible
        move in each of the four directions from that square.  If there is
        a legal move in that direction, put it in the moves ArrayList.
        */
        if (moves.size() == 0) {
            for (int row = 0; row < 8; row++) {
                for (int col = 0; col < 8; col++) {
                    if (board[row][col] == player || board[row][col] == playerKing) {
                        if (canMove(player,row,col,row+1,col+1)) {
                            moves.add(new CheckersMove(row,col,row+1,col+1));
                        }
                        if (canMove(player,row,col,row-1,col+1)) {
                            moves.add(new CheckersMove(row,col,row-1,col+1));
                        }
                        if (canMove(player,row,col,row+1,col-1)) {
                            moves.add(new CheckersMove(row,col,row+1,col-1));
                        }
                        if (canMove(player,row,col,row-1,col-1)) {
                            moves.add(new CheckersMove(row,col,row-1,col-1));
                        }
                    }
                }
            }
        }
       /* If no legal moves have been found, return null.  Otherwise, create
        an array just big enough to hold all the legal moves, copy the
        legal moves from the ArrayList into the array, and return the array. */
        if (moves.size() == 0) {
            return null;
        }
        else {
            CheckersMove[] moveArray = new CheckersMove[moves.size()];
            for (int i = 0; i < moves.size(); i++) {
                moveArray[i] = moves.get(i);

            }
            return moveArray;
        }
    }//end of method getLegalMoves()

    /**
     * Return a list of the legal jumps that the specified player can
     * make starting from the specified row and column.  If no such
     * jumps are possible, null is returned.  The logic is similar
     * to the logic of the getLegalMoves() method.
     */
    CheckersMove[] getLegalJumpsFrom(int player, int row, int col) {
        if (player != RED && player != BLACK) {
            return null;
        }

        int playerKing;  // The constant representing a King belonging to player.

        if (player == RED) {
            playerKing = RED_KING;
        }
        else {
            playerKing = BLACK_KING;
        }

        ArrayList<CheckersMove> moves = new ArrayList<>();  // The legal jumps will be stored in this list.

        if (board[row][col] == player || board[row][col] == playerKing) {
            if (canJump(player, row, col, row+1, col+1, row+2, col+2)) {
                if(canJump(player, row+2, col+2, row+3, col+3, row+4, col+4)) {
                    if(canJump(player, row+4, col+4, row+5, col+5, row+6, col+6)) {
                        moves.add(new CheckersMove(row, col, row+6, col+6));
                    } else {
                        moves.add(new CheckersMove(row, col, row+4, col+4));
                    }
                } else {
                    moves.add(new CheckersMove(row, col, row+2, col+2));
                }
            }
            if (canJump(player, row, col, row-1, col+1, row-2, col+2)) {
                if(canJump(player, row-2, col+2, row-3, col+3, row-4, col+4)) {
                    if(canJump(player, row-4, col+4, row-5, col+5, row-6, col+6)) {
                        moves.add(new CheckersMove(row, col, row-6, col+6));
                    } else {
                        moves.add(new CheckersMove(row, col, row-4, col+4));
                    }
                } else {
                    moves.add(new CheckersMove(row, col, row-2, col+2));
                }
            }
            if (canJump(player, row, col, row+1, col-1, row+2, col-2)) {
                if(canJump(player, row+2, col-2, row+3, col-3, row+4, col-4)) {
                    if(canJump(player, row+4, col-4, row+5, col-5, row+6, col-6)) {
                        moves.add(new CheckersMove(row, col, row+6, col-6));
                    } else {
                        moves.add(new CheckersMove(row, col, row+4, col-4));
                    }
                } else {
                    moves.add(new CheckersMove(row, col, row+2, col-2));
                }
            }
            if (canJump(player, row, col, row-1, col-1, row-2, col-2)) {
                if(canJump(player, row-2, col-2, row-3, col-3, row-4, col-4)) {
                    if(canJump(player, row-4, col-4, row-5, col-5, row-6, col-6)) {
                        moves.add(new CheckersMove(row, col, row-6, col-6));
                    } else {
                        moves.add(new CheckersMove(row, col, row-4, col-4));
                    }
                } else {
                    moves.add(new CheckersMove(row, col, row-2, col-2));
                }
            }
        }
        if (moves.size() == 0) {
            return null;
        }
        else {
            CheckersMove[] moveArray = new CheckersMove[moves.size()];
            for (int i = 0; i < moves.size(); i++) {
                moveArray[i] = moves.get(i);

            }
            return moveArray;
        }
    }// end of method getLegalJumpsFrom()

    /**
     * This is called by the two previous methods to check whether the
     * player can legally jump from (r1,c1) to (r3,c3).  It is assumed
     * that the player has a piece at (r1,c1), that (r3,c3) is a position
     * that is 2 rows and 2 columns distant from (r1,c1) and that
     * (r2,c2) is the square between (r1,c1) and (r3,c3).
     */
    private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {
        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8) {
            return false;  // (r3,c3) is off the board.
        }

        if (board[r3][c3] != EMPTY) {
            return false;  // (r3,c3) already contains a piece.
        }

        if (player == RED) {
            if (board[r1][c1] == RED && r3 > r1) {
                return false;  // Regular red piece can only move  up.
            }
            if (board[r2][c2] != BLACK && board[r2][c2] != BLACK_KING) {
                return false;  // There is no black piece to jump.
            }
            return true;  // The jump is legal.
        }
        else if (board[r1][c1] == BLACK && r3 < r1) {
                return false;  // Regular black piece can only move downn.
            }
            else if (board[r2][c2] != RED && board[r2][c2] != RED_KING) {
                return false;  // There is no red piece to jump.
            }
            return true;  // The jump is legal.


    }//end of method canJump()

    /**
     * This is called by the getLegalMoves() method to determine whether
     * the player can legally move from (r1,c1) to (r2,c2).  It is
     * assumed that (r1,r2) contains one of the player's pieces and
     * that (r2,c2) is a neighboring square.
     */
    private boolean canMove(int player, int r1, int c1, int r2, int c2) {

        if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) {
            return false;  // (r2,c2) is off the board.
        }

        if (board[r2][c2] != EMPTY) {
            return false;  // (r2,c2) already contains a piece.
        }

        if (player == RED) {
            if (board[r1][c1] == RED && r2 > r1) {
                return false;  // Regular red piece can only move down.
            }
            return true;  // The move is legal.
        }
        else if (board[r1][c1] == BLACK && r2 < r1) {
                return false;  // Regular black piece can only move up.
            }
            return true;  // The move is legal.


    }//end of method canMove()

    public Integer[][] getBoard() {
        return board;
    }
}//end of class CheckersData
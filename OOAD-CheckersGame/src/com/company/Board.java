
package com.company;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Stack;

/**
 * This panel displays a 160-by-160 checkerboard pattern with
 * a 2-pixel black border.  It is assumed that the size of the
 * canvas is set to exactly 164-by-164 pixels.  This class does
 * the work of letting the users play checkers, and it displays
 * the checkerboard.
 */
public class Board extends JPanel implements ActionListener {

    private JButton newGameButton;
    private JButton resignButton;
    private JLabel message;
    private JButton nextMoveButton;
    private JButton previousButton;
    private int moveCount;
    private int j;
    private Stack<String> liveMoves;

    private ArrayList<String> movesFromFile;


    private InputReader inputReader;
    private HashMap<Integer,String> matchValues;
    protected boolean isValid;
    protected static boolean validMove;


    private CheckersData board;  // The data for the checkers board is kept here.
    //    This board is also responsible for generating
    //    lists of legal moves.

    private boolean gameInProgress; // Is a game currently in progress?

    /* The next three variables are valid only when the game is in progress. */

    private int currentPlayer;      // Whose turn is it now?  The possible values
    //    are CheckersData.RED and CheckersData.BLACK.

    private int selectedRow, selectedCol;  // If the current player has selected a piece to
    //     move, these give the row and column
    //     containing that piece.  If no piece is
    //     yet selected, then selectedRow is -1.

    private CheckersMove[] legalMoves;  // An array containing the legal moves for the






    /**
     * Constructor.  Create the buttons and label.  Listens for mouse
     * clicks and for clicks on the buttons.  Create the board and
     * start the first game.
     */
     Board() {
        moveCount=0;
        setBackground(Color.BLACK);

        nextMoveButton = new JButton("Next");
        resignButton = new JButton("Quit");
        newGameButton = new JButton("New Game");
        previousButton = new JButton("Previous");

        message = new JLabel("",JLabel.CENTER);

        resignButton.addActionListener(this);
        nextMoveButton.addActionListener(this);


        newGameButton.addActionListener(this);
        previousButton.addActionListener(this);

        message.setFont(new  Font("Serif", Font.BOLD, 14));
        message.setForeground(Color.green);
        board = new CheckersData();
        doNewGame();
        liveMoves = new Stack<String>();
        inputReader = new InputReader();
        movesFromFile = inputReader.getmovesFromFile();
        matchValues = inputReader.getnewHashMap();
        isValid=true;
        validMove=true;



    }


    public static boolean isValidMove() {
        return validMove;
    }

    public JButton getNextMoveButton() {
        return nextMoveButton;
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getResignButton() {
        return resignButton;
    }

    public JLabel getMessage() {
        return message;
    }

    public JButton getPreviousButton() {
        return previousButton;
    }

    /**
     * Respond to user's click on one of the 4 buttons.
     */
    public void actionPerformed(ActionEvent evt) {
        Object src = evt.getSource();
        if (src == newGameButton) {
            doNewGame();
        }
        else if (src == resignButton) {
            doResign();
        }

        else if(src==nextMoveButton) {


            if (moveCount > movesFromFile.size()-1) {
//                nextMoveButton.setEnabled(false);
                JOptionPane.showMessageDialog(null, "End of Input");


            }
            else
            {
                j=0;
                doNextMove();
            }



        }
        else if(src==previousButton) {
//            doPrevious();
//            moveCount--;


                try {
                    doPrevious();

                } catch (EmptyStackException e) {

                    JOptionPane.showMessageDialog(null, "No more previous moves");
                }
                    moveCount--;
                if (moveCount <= 0) {
                    moveCount = 1;
                    board.setUpGame();
                }

            }


    }

    /**
     *  Simulates the next move after reading the moves from the file. Current status of the board is maintained so that it can be retrieved
     *  on previous move at nay point in time.
     * Respond to a user click Next button.  If no game is in progress, show
     * an error message.  Otherwise, find the row and column that the user
     * read from the file and call doSelectSquare() to handle it.
     */


    private void doNextMove() {
        if (!gameInProgress) message.setText("Click \"New Game\" to start a new game.");
        else {

            String move = movesFromFile.get(moveCount);

            liveMoves.push(move);
            int fromrow = 0;
            int fromcol = 0;
            int torow = 0;
            int tocol = 0;
            String[] coordinates = move.split(",");

            String[] separetedValues = coordinates[0].split("-");
            String[] separatedValues1 = coordinates[1].split("-");
            fromrow= Integer.parseInt(separetedValues[0]);
            fromcol = Integer.parseInt(separetedValues[1]);
            torow = Integer.parseInt(separatedValues1[0]);
            tocol = Integer.parseInt(separatedValues1[1]);



            board.currentState();
                if (currentPlayer == CheckersData.RED) {



                    verifyMove(fromrow, fromcol, torow, tocol);
                } else if (currentPlayer == CheckersData.BLACK) {

                    verifyMove(fromrow, fromcol, torow, tocol);

                    }

                }
            }


    private void verifyMove(int fromrow, int fromcol, int torow, int tocol ) {
        while (true) {
            if (fromrow - torow > 2 && fromcol - tocol > 2) { //left upper side for red
                int introw = fromrow - 2;
                int intcol = fromcol - 2;
                doSelectSquare(fromrow, fromcol, introw, intcol);
                fromrow = introw;
                fromcol = intcol;
            }
            else if (fromrow - torow > 2 && fromcol - tocol < -2) { //right upper side for red
                int introw = fromrow - 2;
                int intcol = fromcol + 2;
                doSelectSquare(fromrow, fromcol, introw, intcol);
                fromrow = introw;
                fromcol = intcol;
            }
            else if (fromrow - torow < -2 && fromcol - tocol > 2) {//left lower corner for red
                int introw = fromrow + 2;
                int intcol = fromcol - 2;
                doSelectSquare(fromrow, fromcol, introw, intcol);
                fromrow = introw;
                fromcol = intcol;
            }
            else if (fromrow - torow < -2 && fromcol - tocol < -2) {//right lower corner for red
                int introw = fromrow + 2;
                int intcol = fromcol + 2;
                doSelectSquare(fromrow, fromcol, introw, intcol);
                fromrow = introw;
                fromcol = intcol;
            } else {
                doSelectSquare(fromrow, fromcol, torow, tocol);
                break;
            }
        }
    }

    /**
     *  Previous button clicked---
     */

    private void doPrevious(){


        if(moveCount > 0) {

            board.getBoardStatus();
            if (currentPlayer == CheckersData.RED) {
                currentPlayer = CheckersData.BLACK;
                legalMoves = board.getLegalMoves(currentPlayer);
                if (legalMoves == null)
                    System.out.println("BLACK has no moves.  RED wins.");
                else
                    message.setText("BLACK:  Make your move.");
            }
            else {
                currentPlayer = CheckersData.RED;
                legalMoves = board.getLegalMoves(currentPlayer);
                if (legalMoves == null)
                    System.out.println("RED has no moves.  BLACK wins.");
                else
                    message.setText("RED:  Make your move.");
            }
        }
        repaint();



        }








    /**
     * Start a new game
     */
    private void doNewGame() {
        if (gameInProgress == true) {
            // This should not be possible, but it doens't hurt to check.
            message.setText("Finish the current game first!");
            return;
        }
        board.setUpGame();   // Set up the pieces.
        currentPlayer = CheckersData.RED;   // RED moves first.
        legalMoves = board.getLegalMoves(CheckersData.RED);  // Get RED's legal moves.
        selectedRow = -1;   // RED has not yet selected a piece to move.
        message.setText("Red:  Make your move.");
        gameInProgress = true;
        newGameButton.setEnabled(false);
        resignButton.setEnabled(true);
        nextMoveButton.setEnabled(true);
        moveCount=0;
        repaint();
    }


    /**
     * Current player resigns.  Game ends.  Opponent wins.
     */
    private void doResign() {
        if (!gameInProgress) {
            message.setText("There is no game in progress!");
            return;
        }
        if (currentPlayer == CheckersData.RED)
            gameOver("RED resigns.  BLACK wins.");
        else
            gameOver("BLACK resigns.  RED wins.");
    }


    /**
     * The game ends.  The parameter, str, is displayed as a message
     * to the user.  The states of the buttons are adjusted so playes
     * can start a new game.  This method is called when the game
     * ends at any point in this class.
     */
    private void gameOver(String str) {
        message.setText(str);
        newGameButton.setEnabled(true);
        resignButton.setEnabled(false);
        gameInProgress = false;
    }




    /**
     * This is called by doNextMove() when a player clicks on the
     * Next button.  It has already checked
     * that a game is, in fact, in progress.
     */
    private void doSelectSquare(int row, int col, int r2, int c2) {

         /* If the player selected on one of the pieces that the player
          can move, mark this row and col as selected and return.  (This
          might change a previous selection.)  Reset the message, in
          case it was previously displaying an error message. */
        boolean valid = false;
        // System.out.println(validMoves.length);
        for (int i = 0; i < legalMoves.length; i++) {
            // System.out.println(validMoves[i].fromRow + " " + validMoves[i].fromCol);
            if (legalMoves[i].getFromRow() == row && legalMoves[i].getFromCol() == col) {
                selectedRow = row;
                selectedCol = col;
                valid = true;
                if (currentPlayer == CheckersData.RED)
                    message.setText("RED:  Make your move.");
                else
                    message.setText("BLACK:  Make your move.");
                repaint();
            }
        }
        if(!valid) {
            String[] keys = liveMoves.peek().split(",");
            JOptionPane.showMessageDialog(null, "Invalid Move From " + String.valueOf(matchValues.get(keys[0])) + " To " + String.valueOf(matchValues.get((keys[1]))));
            moveCount++;
        }
        for (int i = 0; i < legalMoves.length; i++)
            if (legalMoves[i].getFromRow() == selectedRow && legalMoves[i].getFromCol() == selectedCol
                    && legalMoves[i].getToRow() == r2 && legalMoves[i].getToCol() == c2) {
                doMakeMove(legalMoves[i]);
                moveCount++;
                return;
            }




    }  // end doClickSquare()


    /**
     * This is called when the current player has chosen the specified
     * move.  Make the move, and then either end or continue the game
     * appropriately.
     */
    private void doMakeMove(CheckersMove move) {
        board.makeMove(move);

         /* If the move was a jump, it's possible that the player has another
          jump.  Check for legal jumps starting from the square that the player
          just moved to.  If there are any, the player must jump.  The same
          player continues moving.
          */

        if (move.isJump()){
            legalMoves = board.getLegalJumpsFrom(currentPlayer,move.getToRow(),move.getToCol());
            if (legalMoves != null) {
                if (currentPlayer == CheckersData.RED)
                    message.setText("RED:  You must continue jumping.");
                else
                    message.setText("BLACK:  You must continue jumping.");
                selectedRow = move.getToRow();  // Since only one piece can be moved, select it.
                selectedCol = move.getToCol();
                repaint();
                return;
            }
        }


         /* The current player's turn is ended, so change to the other player.
          Get that player's legal moves.  If the player has no legal moves,
          then the game ends. */

        if (currentPlayer == CheckersData.RED) {
            currentPlayer = CheckersData.BLACK;
            legalMoves = board.getLegalMoves(currentPlayer);
            if (legalMoves == null)
                gameOver("BLACK has no moves.  RED wins.");
            else if (legalMoves[0].isJump())
                message.setText("BLACK:  Make your move.  You must jump.");
            else
                message.setText("BLACK:  Make your move.");
        }
        else {
            currentPlayer = CheckersData.RED;
            legalMoves = board.getLegalMoves(currentPlayer);
            if (legalMoves == null)
                gameOver("RED has no moves.  BLACK wins.");
            else if (legalMoves[0].isJump())
                message.setText("RED:  Make your move.  You must jump.");
            else
                message.setText("RED:  Make your move.");
        }

         /* Set selectedRow = -1 to record that the player has not yet selected
          a piece to move. */

        selectedRow = -1;



        /* Make sure the board is redrawn in its new state. */

        repaint();

    }  // end doMakeMove();


    /**
     * Draw  checkerboard pattern in gray and lightGray.  Draw the
     * checkers.  If a game is in progress, hi-light the legal moves.
     */
    protected void paintComponent(Graphics g) {

        /* Draw a two-pixel black border around the edges of the canvas. */

        g.setColor(Color.black);
        g.drawRect(0,0,getSize().width-1,getSize().height-1);
        g.drawRect(1,1,getSize().width-3,getSize().height-3);

        /* Draw the squares of the checkerboard and the checkers. */

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ( row % 2 == col % 2 )
                    g.setColor(Color.LIGHT_GRAY);
                else
                    g.setColor(Color.GRAY);
                g.fillRect(2 + col*20, 2 + row*20, 20, 20);
                switch (board.pieceAt(row,col)) {
                    case CheckersData.RED:
                        g.setColor(Color.RED);
                        g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                        break;
                    case CheckersData.BLACK:
                        g.setColor(Color.BLACK);
                        g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                        break;
                    case CheckersData.RED_KING:
                        g.setColor(Color.RED);
                        g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                        g.setColor(Color.WHITE);
                        g.drawString("K", 7 + col*20, 16 + row*20);
                        break;
                    case CheckersData.BLACK_KING:
                        g.setColor(Color.BLACK);
                        g.fillOval(4 + col*20, 4 + row*20, 15, 15);
                        g.setColor(Color.WHITE);
                        g.drawString("K", 7 + col*20, 16 + row*20);
                        break;
                }
            }
        }





    }  // end paintComponent()










}

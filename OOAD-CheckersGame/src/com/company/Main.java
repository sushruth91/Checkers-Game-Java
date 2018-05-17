package com.company;


import javax.swing.*;
import java.awt.*;

/**
 * This panel lets two uses play checkers against each other.
 * Red always starts the game.  If a player can jump an opponent's
 * piece, then the player must jump.  When a player can make no more
 * moves, the game ends.
 *
 * The class has a main() routine that lets it be run as a stand-alone
 * application.  The application just opens a window that uses an object
 * of type Checkers as its content pane.
 *
 */
public class Main extends JPanel {



    /**
     * Main routine makes it possible to run Checkers as a stand-alone
     * application.  Opens a window showing a Checkers panel; the program
     * ends when the user closes the window.
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Checkers");
        Main content = new Main();
        window.setContentPane(content);
        window.pack();
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        window.setLocation((screensize.width - window.getWidth()) / 2, (screensize.height - window.getHeight()) / 2);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setVisible(true);
    }


    /**
     * The constructor creates the Board (which in turn creates and manages
     * the buttons and message label), adds all the components, and sets
     * the bounds of the components.  A null layout is used.  (This is
     * the only thing that is done in the main Checkers class.)
     */
    public Main() {
        //Board b = new Board();

        //setLayout(null);  // I will do the layout myself.

        setPreferredSize(new Dimension(350, 250));

        setBackground(new Color(0, 150, 0));  // Dark green background.


        /* Create the components and add them to the applet. */

        Board board = new Board();  // Note: The constructor for the
        //   board also creates the buttons
        //   and label.
        add(board);
        add(board.getNewGameButton());
        add(board.getResignButton());
        add(board.getNextMoveButton());
        add(board.getPreviousButton());
        add(board.getMessage());

      /* Set the position and size of each component by calling
       its setBounds() method. */

        board.setBounds(20, 20, 164, 164); // Note:  size MUST be 164-by-164 !
        board.getNewGameButton().setBounds(210, 35, 120, 30);
        board.getNextMoveButton().setBounds(210,75,120,30);
        board.getPreviousButton().setBounds(210,115,120,30);
        board.getResignButton().setBounds(210, 155, 120, 30);
        board.getMessage().setBounds(0, 200, 350, 30);
        setLayout(new BorderLayout());
    } // end constructor
}

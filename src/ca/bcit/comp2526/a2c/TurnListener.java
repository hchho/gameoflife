package ca.bcit.comp2526.a2c;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
 * TurnListener class.
 * @author Henry
 * @version 2017
 */
public class TurnListener extends MouseAdapter {
    private GameFrame game;

    /**
     * Constructs TurnListener.
     * @param game for GameFrame
     */
    public TurnListener(final GameFrame game) {
        this.game = game;
    }
    
    /**
     * Makes game take a turn when mouse is clicked.
     * @param event for mouse click event
     */
    public final void mouseClicked(final MouseEvent event) {
        game.takeTurn();
    }

}

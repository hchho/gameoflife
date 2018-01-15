package ca.bcit.comp2526.a2c;

/**
 * CouldNotRemoveException Class.
 * @author Henry
 * @version 2017
 */
public class CouldNotRemoveException extends Exception {
    
    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs CouldNotRemoveException.
     * @param m for String message
     */
    public CouldNotRemoveException(String m) {
        super(m);
    }
}

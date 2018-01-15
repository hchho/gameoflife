package ca.bcit.comp2526.a2c;

/**
 * CouldNotAddException class.
 * @author Henry
 * @version 2017
 */
public class CouldNotAddException extends Exception {
    
    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs CouldNotAddException.
     * @param m for String message
     */
    public CouldNotAddException(String m) {
        super(m);
    }

}

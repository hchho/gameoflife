package ca.bcit.comp2526.a2c;

/**
 * Seedable Interface.
 * @author Henry
 * @version 2017
 */
public interface Reproduces {
   
    /**
     * Determines whether it is ready to seed.
     * @return true if it is ready to seed
     */
    boolean isReadyToBirth();
    
    /**
     * Seeds the organism to a new cell.
     */
    void reproduce();
}

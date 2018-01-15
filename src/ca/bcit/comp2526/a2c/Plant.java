package ca.bcit.comp2526.a2c;
import java.awt.Color;

/**
 * Plant class.
 * @author Henry
 * @version 2017
 */
public class Plant extends Organism implements HerbivoreEdible, OmnivoreEdible {

    /**
     * Minimum empty space to reproduce.
     */
    public static final int MINIMUM_EMPTY_SPACE = 3;

    /**
     * Minimum plant space to reproduce.
     */
    public static final int MINIMUM_MATE_SPACE = 2;
    
    /**
     * Minimum food space to reproduce.
     */
    public static final int MINIMUM_FOOD_SPACE = 0;
    
    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs plant.
     * @param location for Cell location
     */
    public Plant(Cell location) {
        super(location);
        setFoodNumber(MINIMUM_FOOD_SPACE);
        setMateNumber(MINIMUM_MATE_SPACE);
        setEmptyNumber(MINIMUM_EMPTY_SPACE);
    }
    
    /**
     * Initiates the Cell background to green.
     */
    public final void init() {
        getCellLocation().setBackground(Color.green);
    }

    /**
     * Creates Plant.
     * @param cellLocation for Cell location
     * @return Plant for new Organism
     */
    public final Organism createLife(Cell cellLocation) {
        return new Plant(cellLocation);
    }
    
    /**
     * Determines if an Organism is food.
     * @param org for prey Organism.
     * @return true if is food, false if it is not
     */
    public boolean isFood(Organism org) {
        boolean food = false;
        if (org instanceof PlantEdible) {
            food = true;
        } 
        return food;
    }
}

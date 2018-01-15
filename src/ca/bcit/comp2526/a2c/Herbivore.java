package ca.bcit.comp2526.a2c;

import java.awt.Color;

/**
 * Herbivore class.
 * @author Henry
 * @version 2017
 */
public class Herbivore extends Organism implements Animal, 
OmnivoreEdible, CarnivoreEdible {
    /**
     * Life span of Herbivore.
     */
    public static final int LIFE_SPAN = 10;
    
    /**
     * Minimum empty space to reproduce.
     */
    public static final int MINIMUM_EMPTY_SPACE = 1;

    /**
     * Minimum plant space to reproduce.
     */
    public static final int MINIMUM_MATE_SPACE = 2;
    
    /**
     * Minimum food space to reproduce.
     */
    public static final int MINIMUM_FOOD_SPACE = 2;

    /**
     * Serial version for Herbivore.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs Herbivore.
     * @param location for Cell location
     */
    public Herbivore(Cell location) {
        super(location);
        setFoodNumber(MINIMUM_FOOD_SPACE);
        setMateNumber(MINIMUM_MATE_SPACE);
        setEmptyNumber(MINIMUM_EMPTY_SPACE);
        setLifeSpan(LIFE_SPAN);
    }

    /**
     * Initiates the Cell background to yellow.
     */
    public final void init() {
        getCellLocation().setBackground(Color.yellow);
    }
    
    /**
     * Creates Herbivore.
     * @param cellLocation for Cell location
     * @return Herbivore for new Organism
     */
    public final Organism createLife(Cell cellLocation) {
        return new Herbivore(cellLocation);
    }
    
    /**
     * Determines if an Organism is food.
     * @param org for prey Organism.
     * @return true if is food, false if it is not
     */
    public boolean isFood(Organism org) {
        boolean food = false;
        if (org instanceof HerbivoreEdible) {
            food = true;
        } 
        return food;
    }
}

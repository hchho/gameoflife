package ca.bcit.comp2526.a2c;

import java.awt.Color;

/**
 * Omnivore class.
 * @author Henry
 * @version 2017
 */
public class Omnivore extends Organism implements Animal, CarnivoreEdible {

    /**
     * Life span of Omnivore.
     */
    public static final int LIFE_SPAN = 2;

    /**
     * Minimum empty space to reproduce.
     */
    public static final int MINIMUM_EMPTY_SPACE = 3;

    /**
     * Minimum plant space to reproduce.
     */
    public static final int MINIMUM_MATE_SPACE = 1;

    /**
     * Minimum food space to reproduce.
     */
    public static final int MINIMUM_FOOD_SPACE = 3;

    /**
     * Serial Verion UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs Omnivore.
     * @param location for Cell location
     */
    public Omnivore(Cell location) {
        super(location);
        setFoodNumber(MINIMUM_FOOD_SPACE);
        setMateNumber(MINIMUM_MATE_SPACE);
        setEmptyNumber(MINIMUM_EMPTY_SPACE);
        setLifeSpan(LIFE_SPAN);
    }

    /**
     * Initiates the Cell background to blue.
     */
    protected void init() {
        getCellLocation().setBackground(Color.blue);
    }

    /**
     * Creates Omnivore.
     * @param cellLocation for Cell location
     * @return Plant for new Organism
     */
    public final Organism createLife(Cell cellLocation) {
        return new Omnivore(cellLocation);
    }
    
    /**
     * Determines if an Organism is food.
     * @param org for prey Organism.
     * @return true if is food, false if it is not
     */
    public boolean isFood(Organism org) {
        boolean food = false;
        if (org instanceof OmnivoreEdible) {
            food = true;
        } 
        return food;
    }


}

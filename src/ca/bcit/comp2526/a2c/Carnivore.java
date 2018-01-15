package ca.bcit.comp2526.a2c;

import java.awt.Color;

/**
 * Carnivore class.
 * @author Henry
 * @version 2017
 */
public class Carnivore extends Organism implements Animal, OmnivoreEdible {

    /**
     * Life span of carnivore.
     */
    public static final int LIFE_SPAN = 7;
    
    /**
     * Minimum empty space to reproduce.
     */
    public static final int MINIMUM_EMPTY_SPACE = 2;

    /**
     * Minimum plant space to reproduce.
     */
    public static final int MINIMUM_MATE_SPACE = 1;
    
    /**
     * Minimum food space to reproduce.
     */
    public static final int MINIMUM_FOOD_SPACE = 2;
    
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs Carnivore.
     * @param location for Cell location
     */
    public Carnivore(Cell location) {
        super(location);
        setFoodNumber(MINIMUM_FOOD_SPACE);
        setMateNumber(MINIMUM_MATE_SPACE);
        setEmptyNumber(MINIMUM_EMPTY_SPACE);
        setLifeSpan(LIFE_SPAN);
    }
    
    /**
     * Initiates the Cell background to magenta.
     */
    protected void init() {
        getCellLocation().setBackground(Color.magenta);
    }
    
    /**
     * Creates Carnivore.
     * @param cellLocation for Cell location
     * @return Carnivore for new Organism
     */
    public final Organism createLife(Cell cellLocation) {
        return new Carnivore(cellLocation);
    }
    
    /**
     * Determines if an Organism is food.
     * @param org for prey Organism.
     * @return true if is food, false if it is not
     */
    public boolean isFood(Organism org) {
        boolean food = false;
        if (org instanceof CarnivoreEdible) {
            food = true;
        } 
        return food;
    }
}

package ca.bcit.comp2526.a2c;

import java.awt.Component;
import java.util.ArrayList;

/**
 * Organism abstract class.
 * @author Henry
 * @version 2017
 */
public abstract class Organism extends Component implements Mortal, Reproduces {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    private Cell location;
    private int ageInTurns;
    private int lifeSpan;
    private boolean isAlive;
    private boolean isFull;
    private int mateNumber;
    private int emptyNumber;
    private int foodNumber;

    /**
     * Constructs Organism.
     * @param location for Cell location
     */
    public Organism(Cell location) {
        this.location = location;
        ageInTurns = 0;
        isAlive = true;
        isFull = false;
        init();
    }  

    /**
     * Gets mate Cell requirement.
     * @return the mateNumber as an int
     */
    public final int getMateNumber() {
        return mateNumber;
    }

    /**
     * Sets mate Cell requirement.
     * @param mateNumber the mateNumber to set
     */
    public final void setMateNumber(int mateNumber) {
        this.mateNumber = mateNumber;
    }

    /**
     * Gets empty Cell requirement.
     * @return the emptyNumber as an int
     */
    public final int getEmptyNumber() {
        return emptyNumber;
    }

    /**
     * Sets empty Cell requirement.
     * @param emptyNumber the emptyNumber to set
     */
    public final void setEmptyNumber(int emptyNumber) {
        this.emptyNumber = emptyNumber;
    }

    /**
     * Gets food Cell requirement.
     * @return the foodNumber
     */
    public final int getFoodNumber() {
        return foodNumber;
    }

    /**
     * Sets food Cell requirement.
     * @param foodNumber the foodNumber to set
     */
    public final void setFoodNumber(int foodNumber) {
        this.foodNumber = foodNumber;
    }

    /**
     * Gets life span.
     * @return the lifeSpan as an int
     */
    public final int getLifeSpan() {
        return lifeSpan;
    }

    /**
     * Sets life span.
     * @param lifeSpan the lifeSpan to set
     */
    public final void setLifeSpan(int lifeSpan) {
        this.lifeSpan = lifeSpan;
    }

    /**
     * Gets isAlive.
     * @return isAlive as boolean
     */
    public final boolean getIsAlive() {
        return isAlive;
    }

    /**
     * Removes organism from the previous Cell.
     */
    public void dies() {
        isAlive = false;
    }

    /**
     * Gets Cell.
     * @return location as a Cell
     */
    public final Cell getCellLocation() {
        return location;
    };

    /**
     * Sets Cell.
     * @param newCell for new Cell
     */
    public final void setCellLocation(Cell newCell) {
        this.location = newCell;
    }

    /**
     * Gets age in turns.
     * @return age in turns as an int
     */
    public final int getAgeInTurns() {
        return ageInTurns;
    }

    /**
     * Eats an Organism in a Cell.
     * @param organism for prey Organism
     */
    public final void eats(final Organism organism) {  
        organism.dies();
        isFull = true;
    }

    /**
     * Moves the organism to a new Cell.
     */
    public final void move() {
        if (!isAlive) {
            return;
        }
        isFull = false;
        Cell c = choosePosition(getCellLocation().getAdjacentCells());
        //If Cell c is not null, then either eat an organism or move to an 
        //empty Cell
        if (c != null) {
            if (isFood(c.getOrganism())) {
                eats(c.getOrganism());
                isFull = true;
            } 
            Cell oldLocation = getCellLocation();
            setCellLocation(c);
            oldLocation.removeOrganism();
            c.addOrganism(this);
        }
        updateHealth();
    }

    /**
     * Determines if an Organism is food.
     * @param org for prey Organism.
     * @return true if is food, false if it is not
     */
    public abstract boolean isFood(Organism org);

    /**
     * Returns neighbouring Cells.
     * @param cellArray for Cell[]
     * @return emptyArray or foodArray as ArrayList<Cell>
     */
    public ArrayList<Cell> getNeighbours(final Cell[] cellArray) {
        ArrayList<Cell> foodArray = new ArrayList<Cell>();
        ArrayList<Cell> emptyArray = new ArrayList<Cell>();
        for (Cell c : cellArray) {
            if (c.getOrganism() == null) {
                emptyArray.add(c);
            } else if (
                    !(c.getOrganism().getClass().equals(this.getClass()))) {
                if (isFood(c.getOrganism())) {
                    foodArray.add(c);
                }
            }
        }

        //Returns foodArray if the organism instance of Hungry 
        //and food array is not empty; else, return array of 
        //empty cells
        if (foodArray.isEmpty() || !(this instanceof Hungry)) {
            return emptyArray;
        } else {
            return foodArray;
        }
    }

    /**
     * Chooses position for Organism.
     * @param cellArray for Cell[]
     * @return free cell as a Cell
     */
    public final Cell choosePosition(final Cell[] cellArray) {
        ArrayList<Cell> tempArray = new ArrayList<Cell>();
        Cell tempCell = null;

        tempArray = getNeighbours(cellArray);

        int arraySize = tempArray.size();

        //If the tempArray has a Cell, then randomly select a Cell;
        //else, return a null
        if (arraySize > 0) {
            tempCell = tempArray.get(RandomGenerator.nextNumber(arraySize));
        }
        return tempCell;
    }

    /**
     * Updates health for every turn. Increases the age if it did not eat, else
     * resets it to zero.
     */
    public final void updateHealth() {
        if (isFull) {
            ageInTurns = 0;
        } else {
            ageInTurns++;
            if (getAgeInTurns() > getLifeSpan()) {
                dies();
            }
        }
    }

    /**
     * Reproduces a new Organism in a free Cell location.
     */
    public final void reproduce() {
        if (!isAlive) {
            return;
        }
        Cell[] adjCells = getCellLocation().getAdjacentCells();
        ArrayList<Cell> emptySpaces = new ArrayList<Cell>();
        for (Cell c : adjCells) {
            if (c.getOrganism() == null) {
                emptySpaces.add(c);
            }
        }

        //If the emptySpace array contains an empty Cell, then 
        //reproduce into that Cell; else, do nothing.
        if (!emptySpaces.isEmpty()) {
            Cell newCell = emptySpaces.get(
                    RandomGenerator.nextNumber(emptySpaces.size()));

            //If a Cell is free and the organism is ready to reproduce,
            //adds a child in the Cell
            if (newCell != null) {
                if (isReadyToBirth() 
                        && !(newCell.getOrganism() instanceof Organism)) {
                    newCell.addOrganism(createLife(newCell));
                }
            }
        }
    }

    /**
     * Creates life in Cell.
     * @param cellLocation for Cell
     * @return Organism for new Organism
     */
    public abstract Organism createLife(Cell cellLocation);

    /**
     * Determines if the organism is ready to reproduce.
     * @return true if it is ready to reproduce, false if it is not
     */
    public final boolean isReadyToBirth() {
        if (!isAlive) {
            return false;
        }
        int emptySpace = 0;
        int mates = 0;
        int food = 0;
        boolean canReproduce = false;
        Cell[] adjCells = getCellLocation().getAdjacentCells();

        //Counts empty spaces, mates, and food in the Cell array
        for (Cell c : adjCells) {
            if (!(c.getOrganism() instanceof Organism)) {
                emptySpace++;
            } else if (isFood(c.getOrganism())) {
                food++;
            } else if (c.getOrganism().getClass().equals(this.getClass()) 
                    && c.getOrganism() instanceof Reproduces) {
                mates++;
            }
        }

        //If the conditions are right, this Organism is ready to reproduce
        if (emptySpace >= emptyNumber 
                && (mates >= mateNumber && food >= foodNumber)) {
            canReproduce = true;
        }

        return canReproduce;
    }

    /**
     * Initiates the organism.
     */
    protected abstract void init();

}

package ca.bcit.comp2526.a2c;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * World class.
 * @author Henry
 * @version 2017
 */
public class World implements Serializable {

    /**
     * Maximum int range for random generator.
     */
    public static final int MAX_INT = 100; 

    /**
     * Plant percentage.
     */
    public static final int PLANT_INT = 50;

    /**
     * Herbivore percentage.
     */
    public static final int HERBIVORE_INT = 80;

    /**
     * Carnivore percentage.
     */
    public static final int CARNIVORE_INT = 40;

    /**
     * Omnivore percentage.
     */
    public static final int OMNIVORE_INT = 32;
    
    /**
     * Serial Version ID.
     */
    private static final long serialVersionUID = 1L;

    private int rows;
    private int columns;
    private Cell[][] cells;
    private ArrayList<Organism> organisms;

    /**
     * Constructs World.
     * @param rows for number of rows
     * @param columns for number of columns
     */
    public World(final int rows, final int columns) {
        this.rows = rows;
        this.columns = columns;
        cells = new Cell[columns][rows];
        organisms = new ArrayList<Organism>();
    }

    /**
     * Adds organism to Organism ArrayList.
     * @param newOrganism for Organism
     */
    public final void addOrganism(final Organism newOrganism) {
        organisms.add(newOrganism);
    }

    /**
     * Gets row count.
     * @return row count as an int.
     */
    public final int getRowCount() {
        return rows;
    }

    /**
     * Sets row count.
     * @param newRowCount for number of rows
     */
    public final void setRowCount(final int newRowCount) {
        rows = newRowCount;
    }

    /**
     * Gets number of columns.
     * @return columns as an int
     */
    public final int getColumnCount() {
        return columns;
    }

    /**
     * Sets number of columns.
     * @param newColumnCount for new column count
     */
    public final void setColumnCount(final int newColumnCount) {
        columns = newColumnCount;
    }

    /**
     * Puts cells and adds Organisms.
     */
    public final void init() {
        RandomGenerator.reset();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cells[i][j] == null) {
                    cells[i][j] = new Cell(this, i, j);
                } else {
                    cells[i][j].removeOrganism();
                }
                populateCell(cells[i][j]);
            }
        }
        refreshOrganisms();
    }
    
    /**
     * Populates Cell with organism.
     * @param cell for Cell
     */
    public final void populateCell(Cell cell) {
        int determinate = RandomGenerator.nextNumber(MAX_INT);

        //Adds Herbiveore or Plant based on probability
        if (determinate >= HERBIVORE_INT) {
            cell.addOrganism(new Herbivore(cell));
        } else if (determinate >= PLANT_INT) {
            cell.addOrganism(new Plant(cell));
        } else if (determinate >= CARNIVORE_INT) {
            cell.addOrganism(new Carnivore(cell));
        } else if (determinate >= OMNIVORE_INT) {
            cell.addOrganism(new Omnivore(cell));
        }
    }

    /**
     * Gets cell from requested location.
     * @param row for row location
     * @param column for column location
     * @return cell as a Cell
     */
    public final Cell getCellAt(final int row, final int column) {
        return cells[row][column];
    }
    
    /**
     * Removes dead Herbivores and moves them. Checks if Plant 'seeds'
     */
    public final void takeTurn() {
        RandomGenerator.reset();
        for (Organism organism : organisms) {     
            if (organism instanceof Movable) {
                organism.move();
            }
            organism.reproduce();
        }

        //Refreshes the Organism ArrayList
        refreshOrganisms();
    }

    /**
     * Clears and refills the organism ArrayList.
     */
    public final void refreshOrganisms() {
        organisms = new ArrayList<Organism>();

        //Repopulates Organism ArrayList
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell c = cells[i][j];
                if (c.getOrganism() != null && c.getOrganism().getIsAlive()) {
                    organisms.add(c.getOrganism());
                } else {
                    c.removeOrganism();
                }
            }
        }
    }
}

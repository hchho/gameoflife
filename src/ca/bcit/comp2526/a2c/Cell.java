package ca.bcit.comp2526.a2c;
import java.awt.Point;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

/**
 * Cell class.
 * @author Henry
 * @version 2017
 */
public class Cell extends JPanel {

    /**
     * Serial version.
     */
    private static final long serialVersionUID = 1L;

    //Scenario numbers for this Cell's position
    //relative to the World
    private static final int TOP_LEFT = 1;
    private static final int TOP_RIGHT = 2;
    private static final int TOP_EDGE = 3;
    private static final int BOTTOM_LEFT = 4;
    private static final int BOTTOM_RIGHT = 5;
    private static final int BOTTOM_EDGE = 6;
    private static final int LEFT_EDGE = 7;
    private static final int RIGHT_EDGE = 8;
    private static final int MIDDLE = 9;

    //Number of adjacent Cells based on this Cell's location
    private static final int MIDDLE_NUMBER = 8;
    private static final int SIDE_NUMBER = 5;
    private static final int CORNER_NUMBER = 3;

    private World world;
    private int row;
    private int column;
    private Organism organism;
    private int scenario;
    private Point cellLocation;

    /**
     * No-arg constructor for Cell.
     */
    public Cell() { }

    /**
     * Constructs Cell.
     * @param world for world
     * @param row for row
     * @param column for column
     */
    public Cell(World world, int row, int column) {
        this.world = world;
        this.row = row;
        this.column = column;
        this.cellLocation = new Point(row, column);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
    
    /**
     * Gets point.
     * @return cellLocation as Point.
     */
    public Point getPoint() {
        return cellLocation;
    }

    /**
     * Gets world.
     * @return world as World
     */
    public final World getWorld() {
        return world;
    }

    /**
     * Returns Cell location.
     * @return p as a Point
     */
    public final Point getLocation() {
        Point p = new Point(row, column);
        return p;
    }

    /**
     * Adds organism to Cell.
     * @param o for organism
     */
    public final void addOrganism(final Organism o) {
        this.organism = o;
        this.organism.init();
    };

    /**
     * Gets organism in cell.
     * @return organism as an Organism
     */
    public final Organism getOrganism() {
        return organism;
    };

    /**
     * Sets organism and background color to null.
     */
    public final void removeOrganism() {
        organism = null;
        this.setBackground(null);
    }

    /**
     * Sets the scenario if the Cell is at the top.
     * @param endColumn for the final column
     */
    private void topScenario(final int endColumn) {
        if (column == 0) {
            //Top left
            scenario = TOP_LEFT;
        } else if (column == endColumn) {
            //Top right
            scenario = TOP_RIGHT;
        } else {
            //Top edge
            scenario = TOP_EDGE;
        }
    }

    /**
     * Sets the scenario if Cell is at the bottom.
     * @param endColumn for final column
     */
    private void bottomScenario(final int endColumn) {
        if (column == 0) {
            //Bottom left
            scenario = BOTTOM_LEFT;
        } else if (column == endColumn) {
            //Bottom right
            scenario = BOTTOM_RIGHT;
        } else {
            //Bottom edge
            scenario = BOTTOM_EDGE;
        }
    }

    /**
     * Sets scenario to determine cell array size.
     * @param endRow for final row
     * @param endColumn for final column
     */
    private void setScenario(final int endRow, final int endColumn) {
        if (row == 0) {
            topScenario(endColumn);
        } else if (row == endRow) {
            bottomScenario(endColumn);
        } else if (column == 0) {
            //Left edge
            scenario = LEFT_EDGE;
        } else if (column == endColumn) {
            //Right edge
            scenario = RIGHT_EDGE;
        } else {
            //Middle
            scenario = MIDDLE;
        } 
    }

    /**
     * Sets the Cell array size in getAdjacentCells method.
     * @return arraySize as an int
     */
    private int setArraySize() {
        int arraySize;
        if ((scenario == LEFT_EDGE || scenario == RIGHT_EDGE) 
                || (scenario == BOTTOM_EDGE || scenario == TOP_EDGE)) {
            arraySize = SIDE_NUMBER;
        } else if (scenario == MIDDLE) {
            arraySize = MIDDLE_NUMBER;
        } else {
            arraySize = CORNER_NUMBER;
        }
        return arraySize;
    }

    /**
     * Populates Cell array with Cell.
     * @param cellArray for Cell[]
     * @param lowerRow for lower row bound
     * @param upperRow for upper row bound
     * @param lowerColumn for lower column bound
     * @param upperColumn for upper column bound
     * @return cellArray as a Cell[]
     */
    private Cell[] populateCellArray(final Cell[] cellArray, final int lowerRow,
            final int upperRow, final int lowerColumn, final int upperColumn) {
        int begin = 0;

        //Goes through adjacent Cells and adds them to Cell array
        for (int i = lowerRow; i <= upperRow; i++) {
            for (int j = lowerColumn; j <= upperColumn; j++) {
                if (!(i == row && j == column)) {
                    cellArray[begin] = world.getCellAt(i, j);
                    begin++;
                }
            }
        }        
        return cellArray;
    }

    /**
     * Gets adjacent cells.
     * @return adjacent cells as a Cell[]
     */
    public final Cell[] getAdjacentCells() {
        int cellSize;
        int lowerRowBound = row;
        int upperRowBound = row;
        int lowerColumnBound = column;
        int upperColumnBound = column;        
        int endColumn = world.getColumnCount() - 1;
        int endRow = world.getRowCount() - 1;

        //Sets scenario based on Cell's location
        setScenario(endRow, endColumn);

        //Sets Cell array size
        cellSize = setArraySize();

        //Set boundaries for adjacent cells
        if (scenario < BOTTOM_LEFT || scenario > BOTTOM_EDGE) {
            upperRowBound++;
        }
        if (scenario > TOP_EDGE) {
            lowerRowBound--;
        }
        if (scenario != TOP_LEFT 
                && (scenario != LEFT_EDGE && scenario != BOTTOM_LEFT)) {
            lowerColumnBound--;
        }
        if (scenario != TOP_RIGHT 
                && (scenario != BOTTOM_RIGHT && scenario != RIGHT_EDGE)) {
            upperColumnBound++;
        }

        Cell[] adjCells = new Cell[cellSize];

        //Populate Cell array with cells
        adjCells = populateCellArray(adjCells, lowerRowBound, 
                upperRowBound, lowerColumnBound, upperColumnBound);

        return adjCells;
    }

}

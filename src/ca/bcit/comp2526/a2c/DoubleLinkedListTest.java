package ca.bcit.comp2526.a2c;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

/**
 * DoubleLinkedListTest class.
 * @author Henry
 * @version 2017
 */
public class DoubleLinkedListTest {

    /**
     * Dimension for World.
     */
    public static final int DIMENSION = 25;

    private World world;
    private Cell cellOne;
    private DoubleLinkedList<Organism> orgs;

    /**
     * Sets up the test.
     */
    @Before
    public void setUp() {
        world = new World(DIMENSION, DIMENSION);
        cellOne = new Cell(world, 0, 0);     
        System.out.println("setting up");
    }

    /**
     * Tests creating new DoubleLinkedList.
     */
    @Test
    public void testDoubleLinkedList() {
        orgs = new DoubleLinkedList<>();
        assertNotNull(orgs);
    }

    /**
     * Tests adding to front of empty DoubleLinkedList.
     */
    @Test
    public void testAddToFront1() {
        try {
            orgs = new DoubleLinkedList<>();
            orgs.addToFront(new Herbivore(cellOne));
        } catch (CouldNotAddException e) {
            System.out.println(e);
        } finally {
            System.out.println("Tested addToFront1");
        }
    }

    /**
     * Tests adding to front of non-empty DoubleLinkedList.
     */
    @Test
    public void testAddToFront2() {
        try {
            orgs = new DoubleLinkedList<>();
            Cell cellTwo = new Cell(world, 1, 1);
            Cell cellThree = new Cell(world, 2, 2);
            orgs.addToFront(new Herbivore(cellTwo));
            orgs.addToFront(new Plant(cellOne));
            orgs.addToFront(new Omnivore(cellThree));
        } catch (CouldNotAddException e) {
            System.out.println(e);
        } finally {
            System.out.println("Tested addToFront2");
        }
    }

    /**
     * Tests size with more than one organism.
     */
    @Test
    public void testsize1() {
        try {
            int counter = 0;
            orgs = new DoubleLinkedList<>();
            Cell cellTwo = new Cell(world, 1, 1);
            Cell cellThree = new Cell(world, 2, 2);
            orgs.addToFront(new Herbivore(cellTwo));
            counter++;
            orgs.addToFront(new Plant(cellOne));
            counter++;
            orgs.addToFront(new Omnivore(cellThree));
            counter++;
            assertEquals(orgs.size(), counter);
        } catch (CouldNotAddException e) {
            System.out.println(e);
        } finally {
            System.out.println("Tested size1");
        }
    }

    /**
     * Tests size with one organism. 
     */
    @Test
    public void testsize2() {
        try {
            orgs = new DoubleLinkedList<>();
            orgs.addToFront(new Herbivore(cellOne));
            assertEquals(orgs.size(), 1);
        } catch (CouldNotAddException e) {
            System.out.println(e);
        } finally {
            System.out.println("Tested size2");
        }
    }

    /**
     * Tests adding to end of empty DoubleLinkedList. 
     */
    @Test
    public void testAddToEnd1() {
        try {
            orgs = new DoubleLinkedList<>();
            orgs.addToEnd(new Carnivore(cellOne));
        } catch (CouldNotAddException e) {
            System.out.println(e);
        } finally {
            System.out.println("Tested addToEnd1");
        }
    }

    /**
     * Tests adding to end of non-empty DoubleLinkedList.
     */
    @Test
    public void testAddToEnd2() {
        try {
            orgs = new DoubleLinkedList<>();
            Cell cellTwo = new Cell(world, 1, 1);
            Cell cellThree = new Cell(world, 2, 2);
            orgs.addToEnd(new Plant(cellOne));
            orgs.addToEnd(new Herbivore(cellTwo));
            orgs.addToEnd(new Omnivore(cellThree));
        } catch (CouldNotAddException e) {
            System.out.println(e);
        } finally {
            System.out.println("Tested addToEnd2");
        }
    }

    /**
     * Tests removing an DoubleLinkedList from front with more than one entry. 
     */
    @Test
    public void testRemoveFromFront1() {
        try {
            orgs = new DoubleLinkedList<>();
            Cell cellThree = new Cell(world, 2, 2);
            orgs.addToFront(new Herbivore(cellOne));
            orgs.addToFront(new Plant(cellThree));
            Organism org = orgs.removeFromFront();
            assertEquals(org.getClass().getSimpleName(), 
                    "Plant");
            assertEquals(orgs.size(), 1);
        } catch (CouldNotAddException e) {
            System.out.println(e);
        } catch (CouldNotRemoveException f) {
            System.out.println(f);
        } finally {
            System.out.println("Tested removeFromFront1");
        }
    }

    /**
     * Tests removing an DoubleLinkedList from front with one entry. 
     */
    @Test
    public void testRemoveFromFront2() {
        try {
            orgs = new DoubleLinkedList<>();
            orgs.addToFront(new Herbivore(cellOne));
            assertEquals(orgs.removeFromFront().getClass().getSimpleName(), 
                    "Herbivore");
            assertEquals(orgs.size(), 0);
        } catch (CouldNotAddException e) {
            System.out.println(e);
        } catch (CouldNotRemoveException f) {
            System.out.println(f);
        } finally {
            System.out.println("Tested removeFromFront2");
        }
    }

    /**
     * Tests removing an DoubleLinkedList from front with no entry. 
     */
    @Test
    public void testRemoveFromFront3() {
        try {
            orgs = new DoubleLinkedList<>();
            System.out.println(orgs.removeFromFront());
        } catch (CouldNotRemoveException f) {
            System.out.println("Catch for CouldNotRemoveException successful.");
        } finally {
            System.out.println("Tested removeFromFront3");
        }
    }

    /**
     * Tests removing an DoubleLinkedList from end with more than one entry. 
     */
    @Test
    public void testRemoveFromEnd() {
        try {
            orgs = new DoubleLinkedList<>();
            Cell cellThree = new Cell(world, 2, 2);
            orgs.addToFront(new Herbivore(cellOne));
            orgs.addToFront(new Plant(cellThree));
            assertEquals(orgs.removeFromEnd().getClass().getSimpleName(), 
                    "Herbivore");
            assertEquals(orgs.size(), 1);
        } catch (CouldNotAddException e) {
            System.out.println(e);
        } catch (CouldNotRemoveException f) {
            System.out.println(f);
        } finally {
            System.out.println("Tested removeFromEnd1");
        }
    }

    /**
     * Tests removing an DoubleLinkedList from end with one entry. 
     */
    @Test
    public void testRemoveFromEnd2() {
        try {
            orgs = new DoubleLinkedList<>();
            orgs.addToFront(new Herbivore(cellOne));
            assertEquals(orgs.removeFromEnd().getClass().getSimpleName(), 
                    "Herbivore");
            assertEquals(orgs.size(), 0);
        } catch (CouldNotAddException e) {
            System.out.println(e);
        } catch (CouldNotRemoveException f) {
            System.out.println(f);
        } finally {
            System.out.println("Tested removeFromEnd2");
        }
    }

    /**
     * Tests removing an DoubleLinkedList from end with no entry. 
     */
    @Test
    public void testRemoveFromEnd3() {
        try {
            orgs = new DoubleLinkedList<>();
            System.out.println(orgs.removeFromFront());
        } catch (CouldNotRemoveException f) {
            System.out.println("Catch for CouldNotRemoveException successful.");
        } finally {
            System.out.println("Tested removeFromEnd3");
        }
    }

    /**
     * Tests getting first element in DoubleLinkedList with empty list.
     */
    @Test
    public void testgetFirst1() {
        orgs = new DoubleLinkedList<>();
        assertEquals(orgs.getFirst(), null);
    }
    
    /**
     * Tests getting first element in DoubleLinkedList with non-empty list.
     */
    @Test
    public void testgetFirst2() {
        try {
        orgs = new DoubleLinkedList<>();
        Cell cellTwo = new Cell(world, 1, 1);
        Cell cellThree = new Cell(world, 2, 2);
        orgs.addToEnd(new Plant(cellOne));
        orgs.addToEnd(new Herbivore(cellTwo));
        orgs.addToEnd(new Omnivore(cellThree));
        assertEquals(orgs.getFirst().getClass().getSimpleName(), "Plant");
        } catch (CouldNotAddException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Tested testgetFirst2");
        }
    }
    
    /**
     * Tests getting last element in DoubleLinkedList with empty list.
     */
    @Test
    public void testGetLast1() {
        orgs = new DoubleLinkedList<>();
        assertEquals(orgs.getLast(), null);
    }
    
    /**
     * Tests getting last element in DoubleLinkedList with non-empty list.
     */
    @Test
    public void testGetLast2() {
        try {
        orgs = new DoubleLinkedList<>();
        Cell cellTwo = new Cell(world, 1, 1);
        Cell cellThree = new Cell(world, 2, 2);
        orgs.addToEnd(new Plant(cellOne));
        orgs.addToEnd(new Herbivore(cellTwo));
        orgs.addToEnd(new Omnivore(cellThree));
        assertEquals(orgs.getLast().getClass().getSimpleName(), "Omnivore");
        } catch (CouldNotAddException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Tested testgetFirst2");
        }
    }
}

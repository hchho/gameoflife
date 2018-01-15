package ca.bcit.comp2526.a2c;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Main class.
 * @author BCIT
 * @version 2017
 */
public final class Main {
    private static final int SIZE = 25;
    private static final float SCREEN_AREA = 0.8f;
    private static final Toolkit TOOLKIT;
    private static final float ONE_HUNDRED_PERCENT = 100.0f;

    static {
        TOOLKIT = Toolkit.getDefaultToolkit();
    }

    /**
     * Default constructor for Main.
     */
    private Main() {
    }

    /**
     * Drives the program.
     * @param argv for String arguments
     */
    public static void main(final String[] argv) {
        final GameFrame frame;
        final World world;
        RandomGenerator.reset();
        world = new World(SIZE, SIZE);
        world.init();
        frame = new GameFrame(world);
        position(frame);
        frame.init();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Sets position of GameFrame.
     * @param frame for GameFrame.
     */
    private static void position(final GameFrame frame) {
        final Dimension size;

        size = calculateScreenArea(SCREEN_AREA, SCREEN_AREA);
        frame.setSize(size);
        frame.setLocation(centreOnScreen(size));
    }

    /**
     * Gets the center point of screen.
     * @param size for Dimension size
     * @return new Point
     */
    public static Point centreOnScreen(final Dimension size) {
        final Dimension screenSize;

        if (size == null) {
            throw new IllegalArgumentException("Size cannot be null");
        }

        screenSize = TOOLKIT.getScreenSize();

        return (new Point((screenSize.width - size.width) / 2, 
                (screenSize.height - size.height) / 2));
    }

    /**
     * Calculates screen area.
     * @param widthPercent for width percent
     * @param heightPercent for height percent
     * @return screen area as a Dimension
     */
    public static Dimension calculateScreenArea(final float widthPercent, 
            final float heightPercent) {
        final Dimension screenSize;
        final Dimension area;
        final int width;
        final int height;
        final int size;

        if ((widthPercent <= 0.0f) || (widthPercent > ONE_HUNDRED_PERCENT)) {
            throw new IllegalArgumentException("widthPercent cannot be " 
        + "<= 0 or > 100 - got: " + widthPercent);
        }

        if ((heightPercent <= 0.0f) || (heightPercent > ONE_HUNDRED_PERCENT)) {
            throw new IllegalArgumentException("heightPercent cannot be " 
        + "<= 0 or > 100 - got: " + heightPercent);
        }

        screenSize = TOOLKIT.getScreenSize();
        width = (int) (screenSize.width * widthPercent);
        height = (int) (screenSize.height * heightPercent);
        size = Math.min(width, height);
        area = new Dimension(size, size);

        return (area);
    }
}

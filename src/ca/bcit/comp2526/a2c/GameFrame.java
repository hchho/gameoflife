package ca.bcit.comp2526.a2c;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JSlider;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.EOFException;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * GameFrame Class.
 * @author Henry
 * @version 2017
 */
public class GameFrame extends JFrame implements ChangeListener, 
ActionListener {

    /**
     * Shortest frames per second.
     */
    private static final int FPS_MIN = 250;

    /**
     * Longest frames per second.
     */
    private static final int FPS_MAX = 2000;

    /**
     * Initial frames per second for animation.
     */
    private static final int FPS_INIT = 1000;

    /**
     * Serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * File extension.
     */
    private static final String EXT = "ser";

    /**
     * Length of file extension including period.
     */
    private static final int EXT_LENGTH = 4;
    
    private World world;

    private int timeInterval;
    private JSlider fps;
    private Timer timer;
    private JPanel worldPanel;
    private JPanel gamePanel;
    private JPanel controlPanel;
    private JButton playButton;
    private JButton pauseButton;
    private JButton stepButton;
    private JButton resetButton;
    private JButton saveButton;
    private JButton loadButton;
    private JFileChooser fileChooser;
    

    /**
     * Constructs GameFrame.
     * @param w for World
     */
    public GameFrame(final World w) {
        world = w;
        fps = new JSlider(JSlider.HORIZONTAL,
                FPS_MIN, FPS_MAX, FPS_INIT);
        gamePanel = new JPanel();
        worldPanel = new JPanel();
        controlPanel = new JPanel();
        timer = new Timer((int) fps.getValue(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                takeTurn();
            }
        });
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        resetButton = new JButton("Reset");
        stepButton = new JButton("Step");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = 
                new FileNameExtensionFilter("SER FILES", EXT);
        fileChooser.setFileFilter(filter);
    }

    /**
     * Listens for range slider to change. Sets the interval to slider value.
     * @param e for ChangeEvent
     */
    public void stateChanged(ChangeEvent e) {
        timeInterval = (int) fps.getValue();
        timer.setDelay(timeInterval);
    }

    /**
     * Initiates GameFrame.
     */
    public void init() {
        setTitle("Game of Life");

        //Sets the layout for the JPanels
        gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.PAGE_AXIS));
        worldPanel.setLayout(new GridLayout(world.getRowCount(), 
                world.getColumnCount()));
        createWorld();
        controlPanel.setLayout(new BoxLayout(controlPanel, 
                BoxLayout.LINE_AXIS));
        setButtons();
        addButtonsToControlPanel();
        gamePanel.add(worldPanel);
        gamePanel.add(Box.createHorizontalGlue());
        gamePanel.add(controlPanel, BorderLayout.PAGE_END);
        add(gamePanel);
    }

    /**
     * Draws world to layout.
     */
    public void createWorld() {
        for (int row = 0; row < world.getRowCount(); row++) {
            for (int col = 0; col < world.getColumnCount(); col++) {
                worldPanel.add(world.getCellAt(row, col));
            }
        }
    }

    /**
     * Acts take turn methods.
     */
    public void takeTurn() {
        world.takeTurn();
        repaint();
    }

    /**
     * Sets actions when buttons are pressed.
     * @param ae for Action Event
     */
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == playButton) {
            timer.start();
        } else {
            timer.stop();
        }

        if (ae.getSource() == resetButton) {
            world.init();
            repaint();
        } else if (ae.getSource() == loadButton) {
            openLoadDialog();
        } else if (ae.getSource() == saveButton) {
            openSaveDialog();
        }
    }

    /**
     * Sets the control panel.
     */
    private void setButtons() {
        //Play button will start the timer
        playButton.addActionListener(this);

        //Pause button will stop the timer
        pauseButton.addActionListener(this);

        //Reset button will stop the timer and reset the world
        resetButton.addActionListener(this);

        //Step button will move the game one turn
        stepButton.addMouseListener(new TurnListener(this));

        saveButton.addActionListener(this);

        loadButton.addActionListener(this);

        fps.addChangeListener(this);
    }

    /**
     * Adds buttons to Control Panel.
     */
    public void addButtonsToControlPanel() {
        controlPanel.add(fps);
        controlPanel.add(playButton);
        controlPanel.add(pauseButton);
        controlPanel.add(stepButton);
        controlPanel.add(resetButton);
        controlPanel.add(saveButton);
        controlPanel.add(loadButton);
    }

    /**
     * Opens save dialog.
     */
    public void openSaveDialog() {
        timer.stop();
        int returnVal = fileChooser.showSaveDialog(GameFrame.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = new File(fileChooser.getSelectedFile() + "." + EXT);
            saveList(file);
        } 
    }

    /**
     * Opens load dialog.
     */
    public void openLoadDialog() {
        int returnVal = fileChooser.showOpenDialog(GameFrame.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            int nameLength = file.getName().length();
            String extName = file.getName().substring(nameLength - EXT_LENGTH);
            if (extName.equals("." + EXT)) {
                loadList(file);    
            } else {
                JOptionPane.showMessageDialog(this,
                        "File must have a .ser extension",
                        "Inane error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } 
    }

    /**
     * Saves the World.
     * @param fileName for File
     */
    public void saveList(File fileName) {
        DoubleLinkedList<Cell> cellList = new DoubleLinkedList<>();
        //Adds Cells to DoubleLinkedList
        for (int i = 0; i < world.getRowCount(); i++) {
            for (int j = 0; j < world.getColumnCount(); j++) {
                try {
                    cellList.addToEnd(world.getCellAt(i, j));
                } catch (CouldNotAddException d) {
                    System.out.println(d);
                }
            }
        }
        // save the object to file
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            //Writes out the saved Cell list to file
            out.writeObject(cellList);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException f) {
            f.printStackTrace();
        }
    }

    /**
     * Loads the World.
     * @param fileName for File
     */
    public void loadList(File fileName) {
        timer.stop();
        // read the object from file
        Object obj = null;
        try {
            FileInputStream fis = null;
            ObjectInputStream in = null;
            try {
                fis = new FileInputStream(fileName);
                in = new ObjectInputStream(fis);
                //Loads saved Cells into current World
                obj = in.readObject();
                DoubleLinkedList<Cell> tempList = (DoubleLinkedList<Cell>) obj;
                Iterator<Cell> it = tempList.iterator();
                for (int i = 0; i < world.getRowCount(); i++) {
                    for (int j = 0; j < world.getColumnCount(); j++) {
                        if (it.hasNext()) {
                            Cell tempCell = it.next();
                            Organism o = tempCell.getOrganism();
                            world.getCellAt(i, j).removeOrganism();
                            if (o != null) {
                                cellReloadActions(world.getCellAt(i, j), o);
                            }
                        }
                    }
                }
            } catch (EOFException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException f) {
                f.printStackTrace();
            } finally {
                fis.close();
                in.close();
            }
        } catch (IOException g) {
            g.printStackTrace();
        }
        RandomGenerator.reset();
        world.refreshOrganisms();
        repaint();
    }

    /**
     * Reloads Cell organism properties.
     * @param cell for target Cell
     * @param newOrg for new Organism
     */
    public void cellReloadActions(Cell cell, Organism newOrg) {
        cell.addOrganism(newOrg);
        cell.getOrganism().setCellLocation(cell);
        cell.getOrganism().init();
    }
}

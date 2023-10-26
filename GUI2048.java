// imports necessary packages for project
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GUI2048 extends JFrame implements KeyListener
{
    // instance variables for class
    private Container window;
    private Blocks[][] grid = new Blocks[4][4];
    private Timer timer;    // attempted to use timer for animations
    private boolean gameOver = false;

    /**
     * Sets up GUI and elements related to it
     */
    public GUI2048()
    {
        setupGrid();
        setUpMainWindow();
        addGUIElements();
        addKeyListener(this);
    }

    /**
     * Sets up window and with properties
     */
    public void setUpMainWindow()
    {
        window = getContentPane();
        window.setLayout(null);
        window.setBackground(Color.DARK_GRAY);
        this.setSize(660, 680);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("2048");
        setResizable(false);
    }

    /**
     * Adds starter GUI elements to window
     */
    public void addGUIElements()
    {
        populateGUI();
        populateGUI();
        populateGUI();
        populateGUI();

        findPlacements();

        // attempted to use timer for animations
        timer = new Timer(1, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
    }

    /**
     * sets up initial values for grid with empty Blocks objects
     */
    private void setupGrid()
    {
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[0].length; col++)
            {
                grid[row][col] = new Blocks();
            }
        }
    }

    /**
     * Adds Blocks objects to a random location on board that's not occupied
     */
    private void populateGUI()
    {
        int row = (int)(Math.random() * 4);
        int col = (int)(Math.random() * 4);
        boolean isPlaced = false;

        while (!isPlaced)
        {
            if(grid[row][col].getValue() == 0)
            {
                // initializes and places new blocks on grid
                grid[row][col] = new Blocks(2);
                window.add(grid[row][col]);
                grid[row][col].setBackgroundColor();
                isPlaced = true;
            }
            else
            {
                row = (int)(Math.random() * 4);
                col = (int)(Math.random() * 4);
            }
        }

        findPlacements();
    }

    /**
     * Finds location of Blocks on GUI based on where they are on the grid
     */
    private void findPlacements()
    {
        int countCol = 10;

        for (int col = 0; col < grid[0].length; col++)
        {
            int countRow = 10;
            for (int row = 0; row < grid.length; row++)
            {
                grid[row][col].setBounds(countCol, countRow);
                if (grid[row][col].getValue() > 0)
                    grid[row][col].setText(String.valueOf(grid[row][col].getValue()));  //  prints value that the Blocks objects hold

                else
                    window.add(grid[row][col]);

                countRow += 160;
            }
            countCol += 160;
        }
        // makes sure changes are present
        window.revalidate();
        window.repaint();
    }

    /**
     * used to copy changes to grid on other 2D arrays
     * @param dummyGrid
     */
    public void copyGrid(Blocks[][] dummyGrid)
    {
        for (int row = 0; row < grid.length; row++)
            for (int col = 0; col < grid[0].length; col++)
                dummyGrid[row][col] = grid[row][col];
    }

    /**
     * used to make sure 2 2D arrays weren't the same
     * checks to see if there were any changes in grid when buttons were pressed
     * @param checkGrid
     * @return boolean
     */
    public boolean checkIfGridEqual(Blocks[][] checkGrid)
    {
        for (int row = 0; row < grid.length; row++)
            for (int col = 0; col < grid[0].length; col++)
                if (grid[row][col] != checkGrid[row][col])
                    return false;
        return true;
    }

    /**
     * prints grid for troubleshooting purposes
     */
    public void printGrid()
    {
        for(Blocks[] arr : grid)
        {
            for (Blocks b : arr)
                System.out.print(b.getValue() + "\t");
            System.out.println();
        }
    }

    /**
     * default method that comes with Key Listener
     * @param e the event to be processed
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // checks to see if game is Over
        if(gameOver)
            return;

        // initializes variables needed for arrow buttons
        int count = 0;
        Blocks[][] dummyGrid = new Blocks[4][4];

        // copies values of grid over to dummyGrid
        copyGrid(dummyGrid);
        boolean move = false;

        // runs if right arrow button is pressed
        if (e.getKeyCode() == KeyEvent.VK_RIGHT )
        {
            for (int row = 0; row < grid.length; row++)
            {
                for (int col = grid[0].length - 2; col >= 0; col--)
                {
                    // runs if value contained in object is greater than 0
                    if(grid[row][col].getValue() > 0)
                    {
                        move = false;
                        count = 1;

                        // finds how many places Blocks have to move over if any
                        while (col + count < grid[0].length && grid[row][count + col].getValue() == 0)
                            count++;

                        // checks to see if adjacent values are equal and adds them if so
                        if (col + count < grid[0].length && grid[row][count + col].getValue() == grid[row][col].getValue())
                        {
                            window.remove(grid[row][col]);
                            grid[row][count + col].addValue(grid[row][col].getValue());
                            grid[row][col] = new Blocks();
                        }

                        // moves Blocks over and deletes them from original spot
                        if (grid[row][col + count - 1].getValue() == 0)
                        {
                            grid[row][col + count - 1] = grid[row][col];
                            grid[row][col] = new Blocks();
                        }

                        // finds placement on GUI
                        findPlacements();
                    }
                }
            }
        }

        // runs if left arrow button is pressed
        else if (e.getKeyCode() == KeyEvent.VK_LEFT )
        {
            for (int row = 0; row < grid.length; row++)
            {
                for (int col = 1; col < grid[0].length; col++)
                {
                    // runs if value contained in object is greater than 0
                    if (grid[row][col].getValue() > 0)
                    {
                        move = false;
                        count = 1;

                        // finds how many places Blocks have to move over if any
                        while (col - count >= 0 && grid[row][col - count].getValue() == 0)
                            count++;

                        // checks to see if adjacent values are equal and adds them if so
                        if (col - count >= 0 && grid[row][col - count].getValue() == grid[row][col].getValue())
                        {
                            window.remove(grid[row][col]);
                            grid[row][col - count].addValue(grid[row][col].getValue());
                            grid[row][col] = new Blocks();
                        }

                        // moves Blocks over and deletes them from original spot
                        if (col - count >= -1 && grid[row][col - count + 1].getValue() == 0)
                        {
                            grid[row][col - count + 1] = grid[row][col];
                            grid[row][col] = new Blocks();
                            findPlacements();
                        }

                        // finds placement on GUI
                        findPlacements();
                    }
                }
            }
        }

        // runs when up arrow button is pressed
        else if (e.getKeyCode() == KeyEvent.VK_UP )
        {
            for (int col = 0; col < grid[0].length; col++)
            {
                for (int row = 0; row < grid.length; row++)
                {
                    // runs if value contained in object is greater than 0
                    if(grid[row][col].getValue() > 0)
                    {
                        move = false;
                        count = 1;

                        // finds how many places Blocks have to move over if any
                        while (row - count >= 0 && grid[row - count][col].getValue() == 0)
                            count++;

                        // checks to see if adjacent values are equal and adds them if so
                        if (row - count >= 0 && grid[row - count][col].getValue() == grid[row][col].getValue())
                        {
                            window.remove(grid[row][col]);
                            grid[row - count][col].addValue(grid[row][col].getValue());
                            grid[row][col] = new Blocks();
                        }

                        // moves Blocks over and deletes them from original spot
                        if (row - count >= -1 && grid[row - count + 1][col].getValue() == 0)
                        {
                            grid[row - count + 1][col] = grid[row][col];
                            grid[row][col] = new Blocks();
                        }

                        // finds placement on GUI
                        findPlacements();
                    }
                }
            }
        }

        // runs if down arrow button is pressed
        else if (e.getKeyCode() == KeyEvent.VK_DOWN )
        {
            for (int col = 0; col < grid[0].length; col++)
            {
                for (int row = grid.length - 2; row >= 0; row--)
                {
                    // runs if value contained in object is greater than 0
                    if(grid[row][col].getValue() > 0)
                    {
                        move = false;
                        count = 1;

                        // finds how many places Blocks have to move over if any
                        while (row + count < grid.length && grid[row + count][col].getValue() == 0)
                            count++;

                        // checks to see if adjacent values are equal and adds them if so
                        if (row + count < grid.length && grid[row + count][col].getValue() == grid[row][col].getValue())
                        {
                            window.remove(grid[row][col]);
                            grid[row + count][col].addValue(grid[row][col].getValue());
                            grid[row][col] = new Blocks();
                        }

                        // moves Blocks over and deletes them from original spot
                        if (grid[row + count - 1][col].getValue() == 0)
                        {
                            grid[row + count - 1][col] = grid[row][col];
                            grid[row][col] = new Blocks();
                        }

                        // finds placement on GUI
                        findPlacements();
                    }
                }
            }
        }

        // checks if both 2D arrays are equal and checks if there needs to be a new Blocks to populate GUI
        if(!checkIfGridEqual(dummyGrid))
            move = true;

        // populates GUI with new Blocks objects
        if (move)
            populateGUI();

        // Otherwise, checks mutiple conditions
        // assisted by Ryan Strobel
        if(!move)
        {
            // checks to see if there are moves available
            for (int i = 0; i < grid.length; i++)
            {
                for (int j = 0; j < grid[i].length - 1; j++)
                {
                    if(grid[i][j + 1].getValue() == grid[i][j].getValue())
                        return;
                }
            }
            for (int i = 0; i < grid.length - 1; i++)
            {
                for (int j = 0; j < grid[i].length; j++)
                {
                    if(grid[i + 1][j].getValue() == grid[i][j].getValue())
                        return;
                }
            }

            // sets gameOver to true
            gameOver = true;

            // dulls color of GUI
            // color changes are assisted by Ryan Strobel
            for (Blocks[] i : grid)
            {
                for (Blocks j : i)
                {
                    j.setBackground(new Color(
                            j.getBackground().getRed()/5,
                            j.getBackground().getGreen()/5,
                            j.getBackground().getBlue()/5
                    ));
                }
            }
            int strIdx = 0;
            for (int i = 1; i < grid.length - 1; i++)
            {
                for (int j = 0; j < grid[i].length; j++)
                {
                    grid[i][j].setText("GameOver".charAt(strIdx) + "");
                    strIdx++;
                    grid[i][j].setBackground(new Color(
                            grid[i][j].getBackground().getRed()/2,
                            grid[i][j].getBackground().getGreen()/2,
                            grid[i][j].getBackground().getBlue()/2
                    ));
                }
            }

        }
    }

    /**
     * default method that comes with KeyListener
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args)
    {
        // creates new GUI2048 object and sets it visible
        GUI2048 game = new GUI2048();
        game.setVisible(true);
    }
}
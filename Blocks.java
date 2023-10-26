// imports necessary variables needed for class
import javax.swing.*;
import java.awt.*;

public class Blocks extends JLabel
{
    // initializes instance variables for class
    private int value = 2;

    /**
     * constructor to creates Blocks class
     * @param val
     */
    public Blocks(int val)
    {
        super(String.valueOf(val));
        value = val;
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("Sans Serif", Font.BOLD, 24));
        setForeground(Color.WHITE);
    }

    /**
     * empty constructor for filling in blank spaces
     */
    public Blocks()
    {
        super();
        value = 0;
    }

    /**
     * sets bounds of Blocks object on GUI
     * @param x
     * @param y
     */
    public void setBounds(int x, int y)
    {
        super.setBounds(x, y, 150, 150);
    }

    /**
     * sets background color to a default value
     */
    public void setBackgroundColor()
    {
        super.setBackground(Color.gray);
        super.setOpaque(true);
    }

    /**
     * returns amount of value
     * @return value
     */
    public int getValue()
    {
        return value;
    }

    /**
     * adds num to value and changes color accordingly
     * color changes is assisted by Ryan Strobel
     * @param num
     */
    public void addValue(int num)
    {
        value += num;
        double color = (Math.pow(num, 1/2048.0) - 1) * 100;
        color = color % .33;
        setBackground(new Color(Color.HSBtoRGB((float) color, .75F,.75F)));
    }
}
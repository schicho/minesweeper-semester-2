package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * a extended button class to represent one tile as a button,
 * so one can click on this little mf
 */
public class TileButton extends JButton {

    //what tile the button is 'bound' to
    private int m;
    private int n;

    /**
     * creates a new tile button
     */
    public TileButton() {
        setContentAreaFilled(false);
        setBorderPainted(true);
        Border border = BorderFactory.createLineBorder(Color.gray);
        this.setBorder(border);
    }

    /**
     * binds this button to a new position / tile
     *
     * @param m column index
     * @param n row index
     */
    public void setCoordinates(int m, int n) {
        this.m = m;
        this.n = n;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setPaint(new GradientPaint(
                new Point(0, 0),
                getBackground(),
                new Point(0, getHeight()),
                Color.darkGray));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(new GradientPaint(
                new Point(0, getHeight() / 5),
                Color.lightGray,
                new Point(0, getHeight()),
                getBackground()));
        g2.fillRect(0, getHeight() / 4, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}

package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class TileButton extends JButton {
    private int m;
    private int n;

    public TileButton(){
        setContentAreaFilled(false);
        setBorderPainted(true);
        Border border = BorderFactory.createLineBorder(Color.gray);
        this.setBorder(border);
    }

    public void setCoordinates(int m,int n){
        this.m=m;
        this.n=n;
    }

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setPaint(new GradientPaint(
                new Point(0, 0),
                getBackground(),
                new Point(0, getHeight()),
                Color.darkGray));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setPaint(new GradientPaint(
                new Point(0, getHeight()/5),
                Color.lightGray,
                new Point(0, getHeight()),
                getBackground()));
        g2.fillRect(0, getHeight()/4, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}

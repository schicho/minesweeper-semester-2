package view;

import javax.swing.*;

public class TileButton extends JButton {
    private int m;
    private int n;
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
}

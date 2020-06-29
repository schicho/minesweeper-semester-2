package view;

import javax.swing.*;
import java.awt.*;

public class Gui {

    /**
     * creates a new Gui instance
     */
    public Gui(){
        createWindow(1280, 720, "Minesweeper");
    }

    /**
     * creates a new window
     * @param width width of the window
     * @param height height of the window
     * @param title whatever strange name you wanna call your window
     */
    public void createWindow(int width, int height, String title){
        JFrame window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, width, height);
        window.setVisible(true);
    }
}

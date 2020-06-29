package view;

import controller.Controller;
import model.Model;
import model.enums.GameState;

import javax.swing.*;
import java.awt.*;

public class Gui {

    private JFrame window;
    private JPanel mainMenu;

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
        window = new JFrame(title);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setBounds(0, 0, width, height);
        window.setVisible(true);
    }

    /**
     * loads a specific scene, which is determined by the given state
     * @param state the state to tell the function what to load
     */
    public void loadScene(GameState state, Model model){

        switch(state){

            case MAIN_MENU:
            {
                mainMenu = new JPanel();
                JButton play = new JButton("Play");
                play.addActionListener(Controller.getListener());
                mainMenu.add(play);
                window.getContentPane().add(mainMenu);

                window.setVisible(true);
            }break;
        }
    }
}

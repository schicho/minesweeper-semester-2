package controller;

import model.enums.GameState;
import view.Gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Listener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == Gui.getLastInput()[1]){
            Controller.getModel().setGameState(GameState.MAIN_MENU);
        }
    }
}

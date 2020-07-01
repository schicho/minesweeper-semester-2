package controller;

import view.Gui;
import view.TileButton;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseHandler implements MouseListener {
    @Override
    public void mouseClicked(MouseEvent e) {

        if (e.getSource() instanceof JButton){
            String whatsItDo = buttonInfo((JButton) e.getSource());
            if(whatsItDo.equals("Exit")){
                if(SwingUtilities.isRightMouseButton(e)){
                    System.out.println("tixE");
                }
                else {System.out.println("Exit");}
            }
            else if(whatsItDo.equals("Play")){
                if(SwingUtilities.isRightMouseButton(e)){
                    System.out.println("yalP");
                }
                else System.out.println("Play");
            }
            else {
                System.out.println(whatsItDo);
            }
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     *
     * @param button JButton oder Tilebutton
     * @return a string, identifing the button
     */
    private String buttonInfo(JButton button){
        if(button instanceof TileButton){
            return ((TileButton) button).getM()+":"+((TileButton) button).getN();
        }
        else {return button.getText();}
    }
}

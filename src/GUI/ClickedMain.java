package GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class ClickedMain implements MouseListener {


    private final clicked salutationInterface;

    public ClickedMain(clicked click){
        this.salutationInterface = click;
    }
    

    public void mouseClicked(MouseEvent me){
        this.salutationInterface.click();
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
}
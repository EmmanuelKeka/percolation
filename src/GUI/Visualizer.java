package GUI;

import javax.swing.*;
import java.awt.*;

public class Visualizer extends JFrame{
    private final JPanel [][] panels;
    public Visualizer ( boolean [][] tables, UpdateConnection updateLink){
        int h = tables.length;
        int w = tables[1].length;
        this.panels = new JPanel [h][w];

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                boolean sell = tables[i][j];
                JPanel panel = new JPanel();
                panel.setDoubleBuffered(true);
                panel.setBackground(sell ? Color.white : Color.black);
                panel.setBorder(BorderFactory.createLineBorder(Color.red));

                int finalI = i;
                int finalJ = j;
                panels[i][j] = panel;
                add(panel);

                clicked click = () ->
                {
                    boolean newSellValue = !tables[finalI][finalJ];
                    panels[finalI][finalJ].setBackground(newSellValue ? Color.white : Color.black);
                    updateLink.update(finalI, finalJ);
                };

                ClickedMain clickedMain = new ClickedMain(click);
                panel.addMouseListener(clickedMain);
            }
        }

        setLayout(new GridLayout(tables.length,tables[0].length));
        setVisible(true);
        setSize(500,500);
    }

    public void updatePanel (int i, int j, Color color){
        this.panels[i][j].setBackground(color);
    }

    public void render (boolean [][] tables) {
        for (int i = 0; i < tables.length; i++) {
            for (int j = 0; j < tables[0].length; j++) {
                this.panels[i][j].setBackground(tables[i][j] ? Color.white : Color.black);
            }
        }
    }
}

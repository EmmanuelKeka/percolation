import GUI.UpdateConnection;
import Logic.UF;
import GUI.Visualizer;

import java.util.ArrayList;

public class Main {
    private final boolean [][] tables;
    private final int h;
    private final int w;
    private UF uf;
    Visualizer visualizer;

    Main () {
        this.tables = new boolean[][]{
                {false, false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, false, false},
                {false, false, false, false, false, false},
        };

        this.uf = new UF(tables[0].length,tables.length);
        this.h = tables.length;
        this.w = tables[1].length;
        UpdateConnection updateConnection = this::updateConnections;
        this.visualizer = new Visualizer(tables, updateConnection);
        render();

    }

    private void updateConnections(int i, int j){
        this.tables[i][j] = !this.tables[i][j];
        render();
    }

    private void render (){
        this.uf = new UF(tables[0].length, tables.length);
        for (int i = 0; i < this.h; i++) {
            for (int j = 0; j < this.w; j++) {
                boolean currentSell = this.tables[i][j];
                if (!currentSell) {
                    continue;
                }
                makeConnection(i,j);
            }
        }

        if(this.uf.percolating()){
            setColor();
        } else {
            this.visualizer.render(this.tables);
        }
    }
    public void makeConnection(int i, int j) {
        int currentNode = (this.w * i) + j + 1;

        boolean bottom = i < this.h - 1 && tables[i + 1][j];
        boolean right = j < this.w - 1 && tables[i][j + 1];
        boolean top = i > 0 && tables[i - 1][j];
        boolean left = j > 0 && tables[i][j - 1];

        if (bottom){
            int buttonNodeId = (this.w * (i+1)) + j + 1;
            uf.union(buttonNodeId, currentNode);
        }

        if (right){
            int rightNodeId = (this.w * i) + j + 1 + 1;
            uf.union(rightNodeId, currentNode);
        }

        if (top) {
            int topNodeId = (this.w * i) + j + 1 + 1;
            uf.union(topNodeId, currentNode);
        }

        if (left) {
            int leftNodeId = (this.w * i) + j + 1 + 1;
            uf.union(leftNodeId, currentNode);
        }
    }

    private void setColor(){
        int root = uf.findRoot(0);
        ArrayList<Integer> children = uf.getChildOf(root);
        for (Integer child : children) {
            if(child == 0 || child == (this.h * this.w) + 1)
                continue;

            child -= 1;
            int i = child / this.w;
            int j = child % this.w;

            if (this.tables[i][j]) {
                this.visualizer.updatePanel(i, j);
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
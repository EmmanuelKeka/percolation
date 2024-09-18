package Logic;

import java.util.ArrayList;
import java.util.HashMap;

public class UF {
    private final int [] weights;
    private final int [] nodes;
    private final HashMap<Integer, ArrayList<Integer>> nodeToChildren;
    private final int col;

    public UF (int col, int row)
    {
        int totalNodes = row * col;
        this.nodes = new int[totalNodes + 2];
        this.weights = new int[totalNodes + 2];
        this.nodeToChildren = new HashMap<>();
        this.col = col;

        for (int i = 1; i <= totalNodes; i++) {
            this.weights[i] = 1;
            this.nodes[i] = i;
            ArrayList<Integer> children = new ArrayList<>();
            children.add(i);
            this.nodeToChildren.put(i, children);
        }

        connectTop();
        connectButton();
    }

    public int findRoot (int id)
    {
        while (nodes[id] != id){
            nodes[id] = nodes[nodes[id]];
            id = nodes[id];
        }
        return id;
    }

    public void union (int id1, int id2)
    {
        int root1 = findRoot(id1);
        int root2 = findRoot(id2);

        if (root1 == root2){
            return;
        }

        int weight1 = weights[root1];
        int weight2 =  weights[root2];

        if (weight1 >= weight2) {
            nodes[root2] = root1;
            weights[root1] += weights[root2];
            setChildren(root2, root1);
        } else {
            nodes[root1] = root2;
            weights[root2] += weights[root1];
            setChildren(root1, root2);
        }
    }

    private void setChildren (int from, int to) {
        ArrayList<Integer> children = getChildOf(from);
        for (Integer child : children) {
            if(!nodeToChildren.get(to).contains(child))
                nodeToChildren.get(to).add(child);
        }
    }

    public ArrayList<Integer> getChildOf (int nodeId) {
        return nodeToChildren.get(nodeId);
    }

    private void connectTop () {
        this.nodes[0] = 0;
        this.weights[0] = 1;
        ArrayList<Integer> children = new ArrayList<>();
        children.add(0);
        nodeToChildren.put(0, children);
        for (int i = 1; i < this.col + 1; i++){
            union(0, i);
        }
    }

    private void connectButton () {
        int buttonParent = this.nodes.length - 1;
        ArrayList<Integer> children = new ArrayList<>();
        children.add(buttonParent);
        nodeToChildren.put(buttonParent, children);
        this.nodes[buttonParent] = buttonParent;
        this.weights[buttonParent] = 1;
        for (int i = (buttonParent + 1) - this.col; i < buttonParent; i++){
            union(buttonParent, i);
        }
    }

    public boolean percolating(){
        return findRoot(0) == findRoot(this.nodes.length - 1);
    }
}

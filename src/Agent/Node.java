package Agent;

public class Node {

    int state;
    int action;
    int pathCost;
    Node parent;

    public Node(int state, int action, int pathCost, Node parent) {
        this.state = state;
        this.action = action;
        this.pathCost = pathCost;
        this.parent = parent;
    }

    Node() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int getState() {
        return state;
    }

    public int getAction() {
        return action;
    }

    public int getPathCost() {
        return pathCost;
    }

    public Node getParent() {
        return parent;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setPathCost(int pathCost) {
        this.pathCost = pathCost;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
    
    

}

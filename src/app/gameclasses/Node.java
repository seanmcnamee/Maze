package app.gameclasses;

import java.awt.Point;
import java.util.ArrayList;
/**
 * Node
 */
public class Node {
    public enum Directions{
        N, S, E, W;
    }
    private int xPos, yPos;
    private Node north, south, east, west;

    public Node(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        north = south = east = west = null;
    }

    public Node(int xPos, int yPos, Node north, Node south, Node east, Node west) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
    }
    
    public int getX() {
         return xPos;
    }

    public int getY() {
        return yPos;
    }

    private void setNorth(Node n) {
        this.north = n;
    }

    private void setSouth(Node n) {
        this.south = n;
    }

    private void setEast(Node n) {
        this.east = n;
    }

    private void setWest(Node n) {
        this.west = n;
    }

    public void addInDirection(Node n, Directions d) {
        switch(d) {
            case N:
                setNorth(n);
                break;
            case S:
                setSouth(n);
                break;
            case E:
                setEast(n);
                break;
            case W:
                setWest(n);
                break;
        }
    }

    public Node getFromDirection(Directions d) {
        switch(d) {
            case N:
                return north;
            case S:
                return south;
            case E:
                return east;
            case W:
                return west;
            default:
                return null;
        }
    }

    public static Directions oppositeOf(Directions d) {
        switch(d) {
            case N:
                return Directions.S;
            case S:
                return Directions.N;
            case E:
                return Directions.W;
            case W:
                return Directions.E;
            default:
                return null;
        }
    }

    public int nodeCount() { 
        return ((north != null)? 1:0) + ((south != null)? 1:0) + ((east != null)? 1:0) + ((west != null)? 1:0);
    }

    public ArrayList<Directions> getChoices() {
        //System.out.println("surrounding nodes: " + this.getNorth() + " - " + this.getSouth() + " - " + this.getEast() + " - " + this.getWest());
        //System.out.println("Getting node choices...");
        ArrayList<Directions> choices = new ArrayList<Directions>();
        if (getFromDirection(Directions.N) == null) {
            choices.add(Directions.N);
            //System.out.print("Adding N - ");
        }
        if (getFromDirection(Directions.S) == null) {
            choices.add(Directions.S);
            //System.out.print("Adding S - ");
        }
        if (getFromDirection(Directions.E) == null) {
            choices.add(Directions.E);
            //System.out.print("Adding E - ");
        }
        if (getFromDirection(Directions.W) == null) {
            choices.add(Directions.W);
            //System.out.print("Adding W - ");
        }
        //System.out.println();
        return choices;
    }

    public Node getNodeTowards(Directions direction) {
        int xNew = xPos;
        int yNew = yPos;
        Node nNode = null;
        Node sNode = null;
        Node eNode = null;
        Node wNode = null;
        switch(direction) {
            case N :
                yNew--;
                sNode = this;
                break;
            case S :
                yNew++;
                nNode = this;
                break;
            case E :
                xNew++;
                wNode = this;
                break;
            case W :
                xNew--;
                eNode = this;
                break;
        }
        //System.out.println("surrounding nodes for " + xNew + ", " + yNew + ": " + nNode + " - " + sNode + " - " + eNode + " - " + wNode);

        return new Node(xNew, yNew, nNode, sNode, eNode, wNode);
    }

    public boolean equals(Node other) {
        return (xPos == other.xPos) && (yPos == other.yPos);
    }

    public String toString() { 
        return "Node at " + xPos + ", " + yPos;
    }
}
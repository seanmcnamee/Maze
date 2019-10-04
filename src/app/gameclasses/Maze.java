package app.gameclasses;

import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;

import app.supportclasses.GameValues;
/**
 * Maze
 */
public class Maze {
    ArrayList<Wall> pathWalls = new ArrayList<Wall>();
    ArrayList<Wall> realWalls = new ArrayList<Wall>();
    GameValues gameValues;

    public Maze(GameValues gameValues) {
        this.gameValues = gameValues;
    }

    /**
     * Creates a 'size'x'size' maze
     * TODO add percent change of creating cycle
     */
    public void randomWalkMaze(int size) {
        System.out.println("Starting random walk");
        //Full list of all nodes
        ArrayList<Node> nodes = new ArrayList<Node> ();

        Node n0 = new Node(0, 0);
        Node n1 = new Node(0, 1);
        Node n2 = new Node(1, 1);
        Node n3 = new Node(1, 0);

        
        createMiddleSquare(n0, n1, n2, n3);
        
        //System.out.println("Node 0's surrounding nodes: " + n0.getNorth() + " - " + n0.getSouth() + " - " + n0.getEast() + " - " + n0.getWest());


        nodes.add(n0);
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);

        Node rndStart = nodes.get((int)(Math.random()*nodes.size()));
        final int movement = 6;
        int rndCenterX = (int)((Math.random()*movement)-movement/2);
        int rndCenterY = (int)((Math.random()*movement)-movement/2);

        //Stack of nodes that will branch to find paths (starting at (1, 1)) (because of how things are centered)
        Stack<Node> path = new Stack<Node>();
        path.push(rndStart);

        while (path.size() > 0) {
            //System.out.print("Path is size: " + path.size());
            ArrayList<Node.Directions> directions = path.peek().getChoices();
            boolean found = false;

            while (!found && directions.size() > 0) {
                Node.Directions chosenDirection = directions.get((int)(Math.random()*directions.size()));
                Node possibleNext = path.peek().getNodeTowards(chosenDirection);

                double radius = (size)/2;
                //If the possible Node isn't out of bounds
                if (Math.abs(possibleNext.getX()+rndCenterX) <= (radius) && Math.abs(possibleNext.getY()+rndCenterY) <= (radius)) {
                    //If its not forming a loop
                    if (nodeWithin(nodes, possibleNext) == null) {
                        path.peek().addInDirection(possibleNext, chosenDirection);
                        nodes.add(possibleNext);
                        path.push(possibleNext);
                        found = true;
                    //Certain percent chance of forming a loop (which shouldn't be re explored)
                    }   else if (Math.random() < .01) {
                        System.out.println("Creating a loop!");
                        Node loopNode = nodeWithin(nodes, possibleNext);
                        path.peek().addInDirection(loopNode, chosenDirection);
                        loopNode.addInDirection(path.peek(), Node.oppositeOf(chosenDirection));
                        //Purposely don't say you're found yet so that more paths from this same node can be found
                    }
                    //System.out.println("\tNext Node is located at: " + possibleNext.getX() + ", " + possibleNext.getY());
                }
                if (!found) {
                    directions.remove(chosenDirection);
                }
            }
            
            if (!found) {
                path.pop();
            }
        }
        createWalls(nodes);
        directNodesToWalls(nodes);

    }


    private Node nodeWithin(ArrayList<Node> nodes, Node node) {
        for (Node n : nodes) {
            if (n.equals(node)) {
                return n;
            }
        }
        return null;
    }

    /**
     * n0 | n3
     * n1 | n2
     * @param n0
     * @param n1
     * @param n2
     * @param n3
     */
    private void createMiddleSquare(Node n0, Node n1, Node n2, Node n3) {
        n0.addInDirection(n3, Node.Directions.E);
        n0.addInDirection(n1, Node.Directions.S);

        n1.addInDirection(n0, Node.Directions.N);
        n1.addInDirection(n2, Node.Directions.E);

        n2.addInDirection(n1, Node.Directions.W);
        n2.addInDirection(n3, Node.Directions.N);

        n3.addInDirection(n2, Node.Directions.S);
        n3.addInDirection(n0, Node.Directions.W);
    }

    private void directNodesToWalls(ArrayList<Node> nodes) {
        ArrayList<Wall> temp = new ArrayList<Wall>();
        while (nodes.size() >0) {
            Node node = nodes.get(0);
            if (node.getFromDirection(Node.Directions.N) != null) {
                temp.add(new Wall(node.getX(), node.getY(), node.getFromDirection(Node.Directions.N).getX(), node.getFromDirection(Node.Directions.N).getY(), gameValues));
            }
            if (node.getFromDirection(Node.Directions.S) != null) {
                temp.add(new Wall(node.getX(), node.getY(), node.getFromDirection(Node.Directions.S).getX(), node.getFromDirection(Node.Directions.S).getY(), gameValues));
            }
            if (node.getFromDirection(Node.Directions.E) != null) {
                temp.add(new Wall(node.getX(), node.getY(), node.getFromDirection(Node.Directions.E).getX(), node.getFromDirection(Node.Directions.E).getY(), gameValues));
            }
            if (node.getFromDirection(Node.Directions.W) != null) {
                temp.add(new Wall(node.getX(), node.getY(), node.getFromDirection(Node.Directions.W).getX(), node.getFromDirection(Node.Directions.W).getY(), gameValues));
            }
            nodes.remove(0);
        }
        this.pathWalls = temp;
    }

    private void createWalls(ArrayList<Node> nodes) {
        ArrayList<Wall> temp = new ArrayList<Wall>();
        for (Node n : nodes) {
            ArrayList<Node.Directions> wallSides = n.getChoices();
            int x = n.getX();
            int y = n.getY();
            //System.out.print("\nNode at: " + x + ", " + y + ": ");
            //for (Node.Directions d : wallSides) {
            //    System.out.print(d + ", ");
            //}
            //System.out.println();
            if (wallSides.contains(Node.Directions.N)) {
                //North is for some reason West?
                temp.add(new Wall(x, y, x+1, y, gameValues));
               
               // System.out.print("Made wall Above - ");
            }
            if (wallSides.contains(Node.Directions.W)) {
                //West is for some reason north?
                temp.add(new Wall(x, y, x, y+1, gameValues));
                
                //System.out.print("Made wall Left - ");
            }
            if (wallSides.contains(Node.Directions.S)) {
                //South is for some reason East?
                temp.add(new Wall(x, y+1, x+1, y+1, gameValues));
                
                //System.out.print("Made wall Below - ");
            }
            if (wallSides.contains(Node.Directions.E)) {
                //East is for some reason South?
                temp.add(new Wall(x+1, y, x+1, y+1, gameValues));
                
                //System.out.println("Made wall Right");
            }
           this.realWalls = temp;
        }
    }

    
    public void render(Graphics g) {
        if (gameValues.realWalls%3 ==0) {
            for (Wall w : this.pathWalls) {
                w.render(g, Color.BLUE);
            }
        }   else if (gameValues.realWalls%3 ==1) {
            for (Wall w : realWalls) {
                w.render(g, Color.RED);
            }
        }   else {
            for (Wall w : this.pathWalls) {
                w.render(g, Color.BLUE);
            }
            for (Wall w : realWalls) {
                w.render(g, Color.RED);
            }
        }
    }
}
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


    /**
     * Creates a 'size'x'size' maze
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

        //Pick a single random path from the center
        Node rndStart = nodes.get((int)(Math.random()*nodes.size()));

        //Place the center a random spot within 'movement' from the very center
        final int movement = 6;
        int rndCenterX = (int)((Math.random()*movement)-movement/2);
        int rndCenterY = (int)((Math.random()*movement)-movement/2);
        System.out.println("Random center: " + rndCenterX + ", " + rndCenterY);

        //Use these centers to make sure the displayed map is always centered
        centerDispalyInScreen(rndCenterX, rndCenterY);

        //Pick a random corner for the start
        int minX = -(size-1)/2 + rndCenterX;
        int maxX = (size)/2 + rndCenterX;
        int minY = -(size-1)/2 + rndCenterY;
        int maxY = (size)/2 + rndCenterY;

        int rndXCorner = (Math.random() <.5)? minX: maxX;
        int rndYCorner = (Math.random() <.5)? minY: maxY;
        Node corner = new Node(rndXCorner, rndYCorner);

        nodes.add(corner);

        //Stack of nodes that will branch to find paths (starting at (1, 1)) (because of how things are centered)
        Stack<Node> path = new Stack<Node>();
        path.push(rndStart);

        while (path.size() > 0) {
            //System.out.print("Path is size: " + path.size());
            Node current = path.peek();
            ArrayList<Node.Directions> directions = current.getChoices();
            boolean found = false;

            while (!found && directions.size() > 0) {
                Node.Directions chosenDirection = directions.get((int)(Math.random()*directions.size()));
                Node possibleNext = current.getNodeTowards(chosenDirection);

                //If the possible Node isn't out of bounds
                if (possibleNext.getX() >= minX && possibleNext.getY() >=  minY && possibleNext.getX() <= maxX && possibleNext.getY() <= maxY) {
                    //If its not forming a loop
                    if (nodeWithin(nodes, possibleNext) == null) {
                        current.addInDirection(possibleNext, chosenDirection);
                        nodes.add(possibleNext);
                        path.push(possibleNext);
                        found = true;
                    //Certain percent chance of forming a loop (which shouldn't be re explored)
                    }   else if (Math.random() < gameValues.LOOP_PROBABILITY && !possibleNext.equals(corner)) {
                        //Make sure that this loop with not be a false finish
                        Node loopNode = nodeWithin(nodes, possibleNext);
                        if (isSafeLoop(current, loopNode, chosenDirection)) {
                            System.out.println("Creating a loop!");
                            current.addInDirection(loopNode, chosenDirection);
                            loopNode.addInDirection(current, Node.oppositeOf(chosenDirection));
                            //Purposely don't say you're found yet so that more paths from this same node can be found
                        }
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
        //Force the corner to be connected in ONLY one spot
        boolean startCornerConnected = false;
        //Node.getDirection((int)Math.signnum(-corner.getX()), (int)Math.signnum(-corner.getY()))
        //new Node(corner.getX()+(int)Math.signum(-corner.getX()), corner.getY()+(int)Math.signum(-corner.getY()), corner.getY())
        Node possibleConnect1 = new Node(corner.getX()+(int)Math.signum(-corner.getX()), corner.getY());
        Node.Directions d1 = Node.getDirection((int)Math.signum(-corner.getX()), 0);

        Node possibleConnect2 = new Node(corner.getX(), corner.getY()+(int)Math.signum(-corner.getY()));
        Node.Directions d2 = Node.getDirection(0, (int)Math.signum(-corner.getY()));
        
        if (isSafeLoop(corner, nodeWithin(nodes, possibleConnect1), d1)){
            startCornerConnected = true;
            corner.addInDirection(nodeWithin(nodes, possibleConnect1), d1);
            nodeWithin(nodes, possibleConnect1).addInDirection(corner, Node.oppositeOf(d1));
        }
        if (!startCornerConnected && isSafeLoop(corner, nodeWithin(nodes, possibleConnect2), d2)) {
            startCornerConnected = true;
            corner.addInDirection(nodeWithin(nodes, possibleConnect2), d2);
            nodeWithin(nodes, possibleConnect2).addInDirection(corner, Node.oppositeOf(d2));
        }

        if (startCornerConnected) {
            createWalls(nodes);
            directNodesToWalls(nodes);
            System.out.println("Start corner... " + Node.oppositeOf(d2) + Node.oppositeOf(d1));
        }   else {
            randomWalkMaze(size);
        }
    }

    private void centerDispalyInScreen(int centerX, int centerY) {
        double singleLengthPercent = gameValues.zoomScale/gameValues.MAX_WALLS;
        System.out.println("Believed length percent: " + singleLengthPercent);

        gameValues.percentDisplayOriginX = -centerX*singleLengthPercent+gameValues.INITIAL_PERCENT__DISPLAY_ORIGIN_X;
        gameValues.percentDisplayOriginY = -centerY*singleLengthPercent+gameValues.INITIAL_PERCENT__DISPLAY_ORIGIN_Y;
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

    private boolean isSafeLoop(Node current, Node possible, Node.Directions chosenDirection) {
        //System.out.println("\nChecking for a Bad Loop from " + current.getX() + ", " + current.getY() + " " + chosenDirection.toString() + " to " + possible.getX() + ", " + possible.getY());
        ArrayList<Node.Directions> connections = current.getConnections();
        for (Node.Directions direction : connections) {
            //System.out.println("Connection " + direction.toString());
            if (Node.areAdjacent(chosenDirection, direction)) {
                //System.out.println("They're adjacent!");
                if (current.getFromDirection(direction).getFromDirection(chosenDirection) == possible.getFromDirection(direction)) {
                    //System.out.println("Found a bad loop!");
                    return false;
                }
            }
        }

        //Make sure that the first loop is not being entered...
        if ((possible.getX() == 0 || possible.getX() == 1) && (possible.getY() == 0 || possible.getY() == 1)) {
            return false;
        }

        return true;
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

}
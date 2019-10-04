package app.gameclasses;

import java.awt.Color;

import app.supportclasses.GameValues;

import java.awt.Graphics;
/**
 * Wall
 */
public class Wall {
    private int x1Pos, y1Pos, x2Pos, y2Pos;
    private GameValues gameValues;

    /**
     * Start position (x1Pos, y1Pos) to end position (x2Pos, y2Pos)
     * Automatically sets start position as the topleft-most of the two points
     */
    public Wall(int x1Pos, int y1Pos, int x2Pos, int y2Pos, GameValues gameValues) {
        this.x1Pos = Math.min(x1Pos,x2Pos);
        this.y1Pos = Math.min(y1Pos, y2Pos);
        this.x2Pos = Math.max(x1Pos,x2Pos);
        this.y2Pos = Math.max(y1Pos, y2Pos);
        this.gameValues = gameValues;
    }

    public void render(Graphics g, Color c) {
        double singleLength = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.zoomScale/gameValues.MAX_WALLS;
        //System.out.println("Actual single length percent: " + singleLength/gameValues.HEIGHT_SCALE_1);
        double singleWidth = singleLength*.25;
        double lengthUnits;
        double xSize;
        double ySize;
        //Figures out how the wall is facing

        //Horizontal Wall (Running East-West)
        if (this.x2Pos > this.x1Pos) {
            lengthUnits = this.x2Pos - this.x1Pos;
            ySize = singleWidth;
            xSize = singleLength*lengthUnits+singleWidth;
            
            //Vertical Wall (Running North-South)
        }   else {
            lengthUnits = this.y2Pos - this.y1Pos;
            xSize = singleWidth;
            ySize = singleLength*lengthUnits+singleWidth;
        }

        g.setColor(c);

        //The center of the window is the default origin
        double pixelOriginX = gameValues.WIDTH_SCALE_1*gameValues.gameScale*gameValues.percentDisplayOriginX;
        double pixelOriginY = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.percentDisplayOriginY;

        //A position is however far from the pixel origin (minus a small change in pixels so things match up)
        double xStart = pixelOriginX + (x1Pos+((c == Color.BLUE)? .5:0)-gameValues.theoreticalOriginX)*singleLength - singleWidth*.5;
        double yStart = pixelOriginY + (y1Pos+((c == Color.BLUE)? .5:0)-gameValues.theoreticalOriginY)*singleLength - singleWidth*.5;


        //System.out.println("At " + xStart + ", " + yStart);
        //System.out.println("\tSize: " + width + ", " + height);
        g.fillRect((int)xStart, (int)yStart, (int)xSize, (int)ySize);

    }

    public boolean equals(Wall other) {
        return (x1Pos == other.x1Pos) && (x2Pos == other.x2Pos) && (y1Pos == other.y1Pos) && (y2Pos == other.y2Pos);
    }
    
}
package app.gameclasses;

import java.awt.geom.Point2D.Double;
import java.awt.Color;

import app.supportclasses.GameValues;

import java.awt.Graphics;
/**
 * Wall
 */
public class Wall {
    private double xPos, yPos;
    private boolean isNorthSouth;
    private GameValues gameValues;

    public Wall(double xPos, double yPos, boolean isNorthSouth, GameValues gameValues) {
        this.xPos = xPos/gameValues.MAX_WALLS;
        this.yPos = yPos/gameValues.MAX_WALLS;
        this.isNorthSouth = isNorthSouth;
        this.gameValues = gameValues;
    }

    public void render(Graphics g) {
        double width = gameValues.WIDTH_SCALE_1*gameValues.gameScale*(isNorthSouth? gameValues.WALL_WIDTH: gameValues.WALL_LENGTH);
        double height = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*(isNorthSouth? gameValues.WALL_LENGTH: gameValues.WALL_WIDTH);
        g.setColor(Color.red);

        double xStart = gameValues.WIDTH_SCALE_1*gameValues.gameScale*xPos-width*.5;
        double yStart = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*yPos-height*.5;
        //System.out.println("At " + xStart + ", " + yStart);
        //System.out.println("\tSize: " + width + ", " + height);
        g.fillRect((int)xStart, (int)yStart, (int)width, (int)height);

    }
    
}
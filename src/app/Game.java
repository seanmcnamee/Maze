package app;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.ComponentEvent;
import java.awt.Color;

import app.gameclasses.Maze;
import app.gameclasses.Wall;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;

/**
 * Main playing screen of the App
 */
public class Game extends DisplayScreen{

    GameValues gameValues;
    //Wall[] walls;
    Maze maze;
    App app;

    public Game(JFrame frame, GameValues gameValues, App app) {
        super(frame);
        this.gameValues = gameValues;
        this.app = app;
        maze = new Maze(gameValues);
        maze.randomWalkMaze(16);
        //walls = new Wall[3];
        //walls[0] = new Wall(1, 1, 1, 2, gameValues);
        //walls[1] = new Wall(1, 2, 2, 2, gameValues);
        //walls[2] = new Wall(2, 2, 2, 1, gameValues);
    }

    public void tick() {

    }

    public void render(Graphics g) {
       // g.setColor(Color.DARK_GRAY);
        //g.fillRect(0, 0, (int)(gameValues.WIDTH_SCALE_1*gameValues.gameScale), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale));
        //for (Wall w : walls) {
        //    w.render(g);
        //}
        maze.render(g);
    }

    public void keyTyped(KeyEvent e){
        if (e.getKeyChar() == KeyEvent.VK_SPACE) {
            System.out.println("Pausing Game...");
            gameValues.gameState = GameValues.GameState.PAUSED;
            maze.randomWalkMaze(16);
            System.out.println("Resuming Game...");
            gameValues.gameState = GameValues.GameState.RUNNING;
            new Thread(app).start();
        }
    }

    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            gameValues.realWalls++;
        }
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        
        double singleLength = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.zoomScale/gameValues.MAX_WALLS;
        double oldPixelOriginX = gameValues.WIDTH_SCALE_1*gameValues.gameScale*gameValues.percentDisplayOriginX;
        double oldPixelOriginY = gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.percentDisplayOriginY;

        gameValues.theoreticalOriginX += (e.getX()-oldPixelOriginX)/singleLength;
        gameValues.theoreticalOriginY += (e.getY()-oldPixelOriginY)/singleLength;

        gameValues.zoomScale -= gameValues.zoomChange*e.getWheelRotation();
        gameValues.percentDisplayOriginX = e.getX()/(gameValues.WIDTH_SCALE_1*gameValues.gameScale);
        gameValues.percentDisplayOriginY = e.getY()/(gameValues.HEIGHT_SCALE_1*gameValues.gameScale);
        
    }
    
}
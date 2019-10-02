package app;

import java.awt.Graphics;

import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.Color;

import app.gameclasses.Wall;
import app.supportclasses.BufferedImageLoader;
import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;

/**
 * Main playing screen of the App
 */
public class Game extends DisplayScreen{

    GameValues gameValues;
    Wall[] walls;

    public Game(JFrame frame, GameValues gameValues) {
        super(frame);
        this.gameValues = gameValues;
        walls = new Wall[2];
        walls[0] = new Wall(12, 12, false, gameValues);
        walls[1] = new Wall(10, 9, true, gameValues);
    }

    public void tick() {

    }

    public void render(Graphics g) {
       // g.setColor(Color.DARK_GRAY);
        //g.fillRect(0, 0, (int)(gameValues.WIDTH_SCALE_1*gameValues.gameScale), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale));
        for (Wall w : walls) {
            w.render(g);
        }
    }

    public void mouseMoved(MouseEvent e) {
    }
    
}
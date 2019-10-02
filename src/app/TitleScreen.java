package app;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JFrame;

import app.supportclasses.BufferedImageLoader;
import app.supportclasses.Button;
import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;
import app.supportclasses.Input;
import app.supportclasses.SpriteSheet;

import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * MainScreen of the App
 */
public class TitleScreen extends DisplayScreen {

    private final BufferedImageLoader background;
    private Button btnStart, btnOptions;
    private GameValues gameValues;
    private DisplayScreen game;
    private DisplayScreen optionScreen;
    private Font font;

    public TitleScreen(JFrame frame, GameValues gameValues, DisplayScreen game) {
        super(frame);
        background = new BufferedImageLoader(gameValues.MAIN_MENU_FILE);
        SpriteSheet buttons = new SpriteSheet(gameValues.MAIN_MENU_BUTTONS);
        
        btnStart = new Button(buttons.shrink(buttons.grabImage(0, 0, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.START_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.START_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        btnOptions = new Button(buttons.shrink(buttons.grabImage(1, 1, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.OPTIONS_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.OPTIONS_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        
        this.gameValues = gameValues;
        this.game = game;

        font = setFont(gameValues);

        optionScreen = new OptionsScreen(frame, gameValues, this);
    }

    
    private Font setFont(GameValues gameValues) {
        Font returningFont = null;
        try {
            InputStream myStream = new BufferedInputStream(new FileInputStream(gameValues.GAME_FONT_FILE));
            Font temp = Font.createFont(Font.TRUETYPE_FONT, myStream);
            returningFont = temp.deriveFont(Font.PLAIN, 50);          
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("Font not loaded.");
        }
        return returningFont;
    }
    

    @Override
    public void render(Graphics g) {
        g.drawImage(background.getImage(), 0, 0, (int)(gameValues.WIDTH_SCALE_1*gameValues.gameScale), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale), null);
        g.setColor(Color.black);
        g.fillRect((int)(gameValues.WIDTH_SCALE_1*(gameValues.gameScale-gameValues.BLACK_BOX_WIDTH)*.5), (int)(gameValues.HEIGHT_SCALE_1*(gameValues.gameScale-gameValues.BLACK_BOX_HEIGHT)*.5), (int)(gameValues.WIDTH_SCALE_1*gameValues.gameScale*gameValues.BLACK_BOX_WIDTH), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.BLACK_BOX_HEIGHT));
        btnStart.render(g);
        btnOptions.render(g);

        //g.setFont(font)
        //g.drawString("Black color", 20, 20);
    }

    public void mouseClicked(MouseEvent e){
        if (btnStart.contains(e.getPoint())) {
            System.out.print("Setting currentScreen to 'game' - ");
            System.out.println("Game: " + game);
            gameValues.currentScreen = game;
        }   else if (btnOptions.contains(e.getPoint())) {
            System.out.print("Setting currentScreen to 'options' - ");
            System.out.println("Options: " + optionScreen);
            gameValues.currentScreen = optionScreen;
        }
    }

    public void mouseMoved(MouseEvent e) {
        //Set hovering effect for the following buttons...
        //btnStart
        if (!btnStart.isHovering() && btnStart.contains(e.getPoint())) {
            btnStart.setHovering(true);
        }   else if (btnStart.isHovering() && !btnStart.contains(e.getPoint())) {
            btnStart.setHovering(false);
        //btnOptions
        }   else if (!btnOptions.isHovering() && btnOptions.contains(e.getPoint())) {
            btnOptions.setHovering(true);
        }   else if (btnOptions.isHovering() && !btnOptions.contains(e.getPoint())) {
            btnOptions.setHovering(false);
        } 
        
    }
    
}
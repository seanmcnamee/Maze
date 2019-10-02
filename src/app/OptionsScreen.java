package app;

import java.awt.Graphics;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JFrame;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

import app.supportclasses.BufferedImageLoader;
import app.supportclasses.Button;
import app.supportclasses.DisplayScreen;
import app.supportclasses.GameValues;
import app.supportclasses.SpriteSheet;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
/**
 * OptionsScreen
 */
public class OptionsScreen extends DisplayScreen {

    private final BufferedImageLoader background;
    private Button btnBack;
    private GameValues gameValues;
    private DisplayScreen mainMenu;
    private Font font;

    public OptionsScreen(JFrame frame, GameValues gameValues, DisplayScreen mainMenu) {
        super(frame);
        background = new BufferedImageLoader(gameValues.OPTIONS_MENU_FILE);

        SpriteSheet buttons = new SpriteSheet(gameValues.MAIN_MENU_BUTTONS);

        btnBack = new Button(buttons.shrink(buttons.grabImage(2, 1, 1, 1, gameValues.MENU_BUTTON_SIZE)), (int)(gameValues.BACK_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.BACK_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        
        this.gameValues = gameValues;
        this.mainMenu = mainMenu;

        font = setFont(gameValues);
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
        g.fillRect((int)(gameValues.WIDTH_SCALE_1*(gameValues.gameScale-gameValues.OPTIONS_BLACK_BOX_WIDTH)*.5), (int)(gameValues.HEIGHT_SCALE_1*(gameValues.gameScale-gameValues.OPTIONS_BLACK_BOX_HEIGHT)*.5), (int)(gameValues.WIDTH_SCALE_1*gameValues.gameScale*gameValues.OPTIONS_BLACK_BOX_WIDTH), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.OPTIONS_BLACK_BOX_HEIGHT));
        btnBack.render(g);
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            returnToMain();
        }
    }

    public void mouseClicked(MouseEvent e){
        if (btnBack.contains(e.getPoint())) {
            returnToMain();
        }
    }

    public void mouseMoved(MouseEvent e) {
        //Set hovering effect for the following buttons...
        //btnBack
        if (!btnBack.isHovering() && btnBack.contains(e.getPoint())) {
            btnBack.setHovering(true);
        }   else if (btnBack.isHovering() && !btnBack.contains(e.getPoint())) {
            btnBack.setHovering(false);
        }
    }

    private void returnToMain() {
        System.out.print("Setting currentScreen to 'TitleScreen' - ");
        System.out.println("Menu: " + mainMenu);
        gameValues.currentScreen = mainMenu;
    }
    
}
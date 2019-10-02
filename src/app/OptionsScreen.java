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
//import app.supportclasses.StringButton;

/**
 * OptionsScreen
 */
public class OptionsScreen extends DisplayScreen {

    private final BufferedImageLoader background;
    private Button btnBack, btnNodeFocus, btnRoadFocus;
    private GameValues gameValues;
    private DisplayScreen mainMenu;
    private Font font;

    public OptionsScreen(JFrame frame, GameValues gameValues, DisplayScreen mainMenu, Font font) {
        super(frame);
        background = new BufferedImageLoader(gameValues.OPTIONS_MENU_FILE);

        SpriteSheet buttons = new SpriteSheet(gameValues.OPTIONS_MENU_BUTTONS);

        btnBack = new Button(buttons.shrink(buttons.grabImage(3, 0, 1, 1, gameValues.OPTIONS_BUTTON_SIZE)), (int)(gameValues.BACK_BUTTON_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.BACK_BUTTON_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        btnNodeFocus = new Button(buttons.shrink(buttons.grabImage(0, 0, 1, 1, gameValues.OPTIONS_BUTTON_SIZE)), (int)(gameValues.CHOICES_X*gameValues.WIDTH_SCALE_1), (int)(gameValues.CHOICES_START_Y*gameValues.HEIGHT_SCALE_1), gameValues);
        btnRoadFocus =new Button(buttons.shrink(buttons.grabImage(1, 0, 1, 1, gameValues.OPTIONS_BUTTON_SIZE)), (int)(gameValues.CHOICES_X*gameValues.WIDTH_SCALE_1), (int)((gameValues.CHOICES_START_Y+gameValues.CHOICES_HEIGHT)*gameValues.HEIGHT_SCALE_1), gameValues);

        //To create any new picture files...
        //StringButton temp = new StringButton("String here", font, (int)(gameValues.gameScale*gameValues.CHOICES_X), (int)(gameValues.gameScale*gameValues.CHOICES_START_Y), gameValues);

        this.gameValues = gameValues;
        this.mainMenu = mainMenu;

        this.font = font;
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(background.getImage(), 0, 0, (int)(gameValues.WIDTH_SCALE_1*gameValues.gameScale), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale), null);
        g.setColor(Color.black);
        g.fillRect((int)(gameValues.WIDTH_SCALE_1*(gameValues.gameScale-gameValues.OPTIONS_BLACK_BOX_WIDTH)*.5), (int)(gameValues.HEIGHT_SCALE_1*(gameValues.gameScale-gameValues.OPTIONS_BLACK_BOX_HEIGHT)*.5), (int)(gameValues.WIDTH_SCALE_1*gameValues.gameScale*gameValues.OPTIONS_BLACK_BOX_WIDTH), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.OPTIONS_BLACK_BOX_HEIGHT));
        btnBack.render(g);
        btnNodeFocus.render(g);
        btnRoadFocus.render(g);

        g.setFont(font);
        g.setColor(Color.white);
        g.drawString(gameValues.OPTIONS_TITLE, (int)((gameValues.WIDTH_SCALE_1*gameValues.gameScale*gameValues.CHOICES_X)-(g.getFontMetrics().stringWidth(gameValues.OPTIONS_TITLE))/2), (int)(gameValues.HEIGHT_SCALE_1*gameValues.gameScale*gameValues.TITLE_Y));
        

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
        //btnNodeFocus
        }   else if (!btnNodeFocus.isHovering() && btnNodeFocus.contains(e.getPoint())) {
            btnNodeFocus.setHovering(true);
        }   else if (btnNodeFocus.isHovering() && !btnNodeFocus.contains(e.getPoint())) {
            btnNodeFocus.setHovering(false);
        //btnRoadFocus
        }   else if (!btnRoadFocus.isHovering() && btnRoadFocus.contains(e.getPoint())) {
            btnRoadFocus.setHovering(true);
        }   else if (btnRoadFocus.isHovering() && !btnRoadFocus.contains(e.getPoint())) {
            btnRoadFocus.setHovering(false);
        }
    }

    private void returnToMain() {
        System.out.print("Setting currentScreen to 'TitleScreen' - ");
        System.out.println("Menu: " + mainMenu);
        gameValues.currentScreen = mainMenu;
    }
    
}
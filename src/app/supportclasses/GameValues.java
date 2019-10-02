package app.supportclasses;

import java.awt.Point;
/**
 * GameValues for the game
 */
public class GameValues {

    public enum GameState {
        NOTSTARTED, RUNNING, WON, LOST, QUIT;
    }

    //Overall Application Values
    public double gameScale = 1;
    public final int WIDTH_SCALE_1 = 790;//960;//300;
    public final int HEIGHT_SCALE_1 = 590;//WIDTH_SCALE_1 / 12 * 9;

    public final double NANO_SECONDS_PER_TICK = 1000000000d / 60d; // (NanoSeconds/1 seconds) * (1 second/nanoseconds in 1 tick)
    public final int ONE_SEC_IN_MILLIS = 1000;
    public int ticksPerSeconds = 0;
    public int framesPerSecond = 0;

    public final String NAME = "Maze Simulator - Sean McNamee";
    public GameState gameState = GameState.NOTSTARTED;
    public DisplayScreen currentScreen;

    
    public final String GAME_FONT_FILE = "bin/MainScreenFont.ttf";
    public final String GAME_BACKGROUND_FILE = "bin/background.png";

    //TitleScreen values
    public final String MAIN_MENU_FILE = "bin//emptyMainMenu.png";
    public final String MAIN_MENU_BUTTONS = "bin/MenuButtonsSpriteSheet.png";
    public final int MENU_BUTTON_SIZE = 170;
    public final float DARKEN_VALUE = .8f;
    public final float LIGHTEN_VALUE = 1.26f;
    public final double START_BUTTON_Y = .4;
    public final double START_BUTTON_X = .5;
    public final double OPTIONS_BUTTON_Y = .7;
    public final double OPTIONS_BUTTON_X = .5;
    public final double BLACK_BOX_WIDTH = .3;
    public final double BLACK_BOX_HEIGHT = .6;

    //OptionsScreen Values
    public final String OPTIONS_MENU_FILE = "bin//emptyMainMenu.png";
    public final String BACK_BUTTON = "bin//backArrow.png";
    public final double BACK_BUTTON_X = .1;
    public final double BACK_BUTTON_Y = .1;
    public final double OPTIONS_BLACK_BOX_WIDTH = .5;
    public final double OPTIONS_BLACK_BOX_HEIGHT = .8;

    //Game Values
    public final double WALL_LENGTH = .05;
    public final double WALL_WIDTH = .01;
    public final int MAX_WALLS = 100;
    public double zoomScale = 1;
    public Point zoomLocation = new Point(0, 0);

}
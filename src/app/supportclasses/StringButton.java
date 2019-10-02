package app.supportclasses;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Font;

/**
 * StringButton
 */
public class StringButton extends Button {

    String text;
    Font font;
    Color color;

    public StringButton(String string, Font font, Color color, int x, int y, GameValues gameValues) {
        super(null, x, y, gameValues);
        this.text = string;
        this.font = font;
        this.color = color;
        saveAsPicture();
        SpriteSheet thisButtonSS = new SpriteSheet(text+".png");
        image = thisButtonSS.shrink(thisButtonSS.grabImage(0, 0, 1, 1, Math.min(thisButtonSS.getWidth(), thisButtonSS.getHeight())));
    }

    /**
     * Convert String and save as picture
     */
    private void saveAsPicture()    {
        //create String object to be converted to image
        String sampleText = text;
        
        //create a File Object
        File newFile = new File("./" + sampleText + ".png");
        
        //create the FontRenderContext object which helps us to measure the text
        FontRenderContext frc = new FontRenderContext(null, true, true);
        
        //get the height and width of the text
        Rectangle2D bounds = font.getStringBounds(sampleText, frc);
        int w = (int) bounds.getWidth();
        int h = (int) bounds.getHeight();
        int square = Math.max(h, w);
        
        //create a BufferedImage object
        BufferedImage image = new BufferedImage(square, square, BufferedImage.TYPE_INT_ARGB);
        
        //calling createGraphics() to get the Graphics2D
        Graphics2D g = image.createGraphics();
        
        //set color and other parameters
        //g.setColor(Color.WHITE);
        //g.fillRect(0, 0, w, h);
        g.setColor(this.color);
        g.setFont(font);
            
        g.drawString(sampleText, (float) bounds.getX(), (float) -bounds.getY());
        
        //releasing resources
        g.dispose();
        
        //creating the file
        try {
            ImageIO.write(image, "png", newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
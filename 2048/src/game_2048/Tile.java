package game_2048;

import java.awt.*;
import java.awt.image.*;

public class Tile {

    public static final int WIDTH = 100; //the pixels of the tile
    public static final int HEIGHT = 100;
    public static final int SLIDE_SPEED = 30;//speed when you slide the block
    public static final int ARC_WIDTH = 15; //rounded rectangule so we will need arc the game board need to know them too so public
    public static final int ARC_HEIGHT = 15;

    private int value;
    private BufferedImage tileImage;
    private Color background;
    private Color text;
    private Font font;
    private Point slideTo;
    private int x;
    private int y;

    private boolean canCombine = true;

    public Tile(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
        slideTo = new Point(x, y);
        tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        drawImage();
    }

    private void drawImage() {
        Graphics2D g = (Graphics2D) tileImage.getGraphics();
        if (value == 2) { //this part is for the color of the blocks
            background = new Color(0xEEE4DA);   //gray
            text = new Color(0x000000); //black
        } else if (value == 4) {
            background = new Color(0xEDE0C8);   //gray
            text = new Color(0x000000); //black            
        } else if (value == 8) {
            background = new Color(0xF2B179);   //gray
            text = new Color(0xFFFFFF); //black            
        } else if (value == 16) {
            background = new Color(0xF59563);   //gray
            text = new Color(0xFFFFFF); //black            
        } else if (value == 32) {
            background = new Color(0xF67C5F);   //gray
            text = new Color(0xFFFFFF); //black            
        } else if (value == 64) {
            background = new Color(0xF65E3B);   //gray
            text = new Color(0xFFFFFF); //black            
        } else if (value == 128) {
            background = new Color(0xEDCF72);   //gray
            text = new Color(0xFFFFFF); //black            
        } else if (value == 256) {
            background = new Color(0xEDCC61);   //gray
            text = new Color(0xFFFFFF); //black            
        } else if (value == 512) {
            background = new Color(0xEDC850);   //gray
            text = new Color(0xFFFFFF); //black            
        } else if (value == 1024) {
            background = new Color(0xEDC53F);   //gray
            text = new Color(0xFFFFFF); //black            
        } else if (value == 2048) {
            background = new Color(0xEDC22E);   //gray
            text = new Color(0xFFFFFF); //black            
        } else {
            background = Color.black;
            text = Color.white;
        }

        g.setColor(new Color(0, 0, 0, 0)); //complete transparent
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(background);
        g.fillRoundRect(0, 0, WIDTH, HEIGHT, ARC_WIDTH, ARC_HEIGHT);
        g.setColor(text);

        if (value <= 64) {
            font = Game.main.deriveFont(36f);
        } else {
            font = Game.main;
        }
        g.setFont(font);

        int drawX = WIDTH / 2 - DrawUtils.getMenssageWidth("" + value, font, g) / 2;  //its going to de centerof all and to the left half of the mesage size
        int drawY = HEIGHT / 2 + DrawUtils.getMessageHeight("" + value, font, g) / 2; //center of the height as the same as above (in java the labels starts like cartesian botton left)
        g.drawString("" + value, drawX, drawY);
        g.dispose();

    }

    public void update() {

    }

    public void render(Graphics2D g) {
        g.drawImage(tileImage, x, y, null);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        drawImage();
    }

    public boolean canCombine() {
        return canCombine;
    }

    public void setCanCombine(boolean canCombine) {
        this.canCombine = canCombine;
    }

    public game_2048.Point getSlideTo() {
        return slideTo;
    }

    public void setSlideTo(game_2048.Point slideTo) {
        this.slideTo = slideTo;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}

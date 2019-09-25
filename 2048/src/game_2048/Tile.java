package game_2048;

import java.awt.*;
import java.awt.image.*;

public class Tile {

    //Informações do Bloco (Tile)
    public static final int WIDTH = 100; 
    public static final int HEIGHT = 100;
    public static final int SLIDE_SPEED = 30;
    
    //Para os blocos "arredondados"
    public static final int ARC_WIDTH = 15; 
    public static final int ARC_HEIGHT = 15;

    //Variáveis específica da classe Tile
    private int value;  
    private BufferedImage tileImage;
    private Color background;
    private Color text;
    private Font font;
    private Point slideTo;
    //Posiçoes na tela
    private int x;
    private int y;

    private boolean canCombine = true;

    //========================================================================//
    
    public Tile(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
        slideTo = new Point(x, y);
        
        //Nova imagem do bloco
        tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB); 
        drawImage(); //Desenha número desejado
    }

    //========================================================================//
    //Desenha as peças 
    private void drawImage() {
        Graphics2D g = (Graphics2D) tileImage.getGraphics();
        if (value == 2) { 
            background = new Color(0xEEE4DA);   //Cor do bloco  
            text = new Color(0x000000);         //Cor do numero do bloco (2, 4, 8, 16, ...)  
        } else if (value == 4) {
            background = new Color(0xEDE0C8); 
            text = new Color(0x000000);         
        } else if (value == 8) {
            background = new Color(0xF2B179);  
            text = new Color(0xFFFFFF);            
        } else if (value == 16) {
            background = new Color(0xF59563);   
            text = new Color(0xFFFFFF);           
        } else if (value == 32) {
            background = new Color(0xF67C5F);   
            text = new Color(0xFFFFFF);            
        } else if (value == 64) {
            background = new Color(0xF65E3B);  
            text = new Color(0xFFFFFF);            
        } else if (value == 128) {
            background = new Color(0xEDCF72);  
            text = new Color(0xFFFFFF);            
        } else if (value == 256) {
            background = new Color(0xEDCC61);   
            text = new Color(0xFFFFFF);       
        } else if (value == 512) {
            background = new Color(0xEDC850); 
            text = new Color(0xFFFFFF);           
        } else if (value == 1024) {
            background = new Color(0xEDC53F); 
            text = new Color(0xFFFFFF);      
        } else if (value == 2048) {
            background = new Color(0xEDC22E);   
            text = new Color(0xFFFFFF);           
        } 
   
        //Desenha informações do bloco
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(background);
        g.fillRoundRect(0, 0, WIDTH, HEIGHT, ARC_WIDTH, ARC_HEIGHT);
        g.setColor(text);

        
        //Fontes dos blocos
        if (value <= 64) {
            font = Game.main.deriveFont(36f);
        } else {
            font = Game.main;
        }
        g.setFont(font);

        //Centralizando texto
        int drawX = WIDTH / 2 - DrawUtils.getMenssageWidth("" + value, font, g) / 2; 
        int drawY = HEIGHT / 2 + DrawUtils.getMessageHeight("" + value, font, g) / 2; 
        g.drawString("" + value, drawX, drawY);
        g.dispose();

    }

    //========================================================================//
    
    public void update() {
    }

    //========================================================================//
    
    public void render(Graphics2D g) {
        g.drawImage(tileImage, x, y, null);
    }

    //========================================================================//
    
    //Getters e Setters
    
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        drawImage();
    }

    public boolean iscanCombine() {
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

package game_2048;

import java.awt.*;
import java.awt.geom.AffineTransform;
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

    //========================================================================//
    //ABOUT aNIMATION
    private boolean beginningAnimation = true;
    private double scaleFirst = 0.1;    //growing effect (melhorar comentario) 
    private BufferedImage beginningImage;
    
    private boolean combineAnimation = false;
    private double scaleCombine = 1.2;
    private BufferedImage combineImage;
    //========================================================================//
    
    private boolean canCombine = true;

    //========================================================================//
    
    public Tile(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
        slideTo = new Point(x, y);
        
        //Nova imagem do bloco
        tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB); 
        
        beginningImage = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);
        combineImage = new BufferedImage(WIDTH*2,HEIGHT*2,BufferedImage.TYPE_INT_ARGB);
        
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
    
    public void update() {  //update all the animation
        if(beginningAnimation){
            AffineTransform transform = new AffineTransform();
            transform.translate(WIDTH/2 - scaleFirst * WIDTH/2,HEIGHT/2 - scaleFirst * HEIGHT/2);   //it will go into the middle and say how big is the new transformed image,than find half that image and move over that much
            transform.scale(scaleFirst, scaleFirst);
            Graphics2D g2d = (Graphics2D)beginningImage.getGraphics();//do the drawing
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0,0,0,0));
            g2d.fillRect(0, 0, WIDTH, HEIGHT);
            g2d.drawImage(tileImage, transform, null);
            scaleFirst += 0.1;
            g2d.dispose(); 
            if(scaleFirst >= 1) beginningAnimation = false;
        }else if(combineAnimation){
            AffineTransform transform = new AffineTransform();
            transform.translate(WIDTH/2 - scaleCombine * WIDTH/2,HEIGHT/2 - scaleCombine * HEIGHT/2);   //it will go into the middle and say how big is the new transformed image,than find half that image and move over that much
            transform.scale(scaleCombine, scaleCombine);
            Graphics2D g2d = (Graphics2D)combineImage.getGraphics();//do the drawing
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0,0,0,0));
            g2d.fillRect(0, 0, WIDTH, HEIGHT);
            g2d.drawImage(tileImage, transform, null);
            scaleCombine -= 0.05;
            g2d.dispose(); 
            if(scaleCombine <= 1) combineAnimation = false;
        }
    }

    //========================================================================//
    
    public void render(Graphics2D g) {
        if(beginningAnimation){ //se eh o inicio
            g.drawImage(beginningImage,x,y,null);
        }else if(combineAnimation){ //se esta combinando
            g.drawImage(combineImage,(int)(x + WIDTH/2 - scaleCombine * WIDTH/2),(int)(y + HEIGHT/2 - scaleCombine * HEIGHT/2),null);
        }else{      //se nao eh nenhum dos dois, portanto provavelmente se nao esta combinando (choque de duas tiles diferentes) - checar e analisar
            g.drawImage(tileImage, x, y, null); 
        }
        
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

    public boolean isCombineAnimation() {
        return combineAnimation;
    }

    public void setCombineAnimation(boolean combineAnimation) {
        this.combineAnimation = combineAnimation;
        if(combineAnimation)scaleCombine = 1.2; //reset the scale combine the fist (the spawn) dont need to be reseted because tiles oonly spawn once
    }

    
    
}

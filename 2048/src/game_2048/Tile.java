package game_2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

//========================================================================//
public class Tile {

    //Informações da peça
    public static final int WIDTH = 100; //Largura
    public static final int HEIGHT = 100; //Altura
    public static final int SPEED = 35; //Velocidade da peça

    //Valor da peça -> 2, 4, 8 ...
    private int tileValue;

    //Usada para desenhar a peça
    private BufferedImage tileImage;

    //Cores
    private Color tileBackground;
    private Color tileText;

    //Fontes
    private Font tileFont;
    private Point slideTo;

    //Posiçoes na tela
    private int x;
    private int y;

    //Para animações
    private boolean startAnimation = true;
    private double renderSpeed = 0.1; //Velocidade da animação
    private BufferedImage startImage;
    private boolean combineAnimation = false;
    private double inicialSize = 0.3; //Tamanho inicial do bloco na animação
    private BufferedImage combineImage;

    //Para checar a combinação das peças
    private boolean canCombine = true;

    //====================TILE CONSTRUCTOR====================================//
    public Tile(int value, int x, int y) {
        this.tileValue = value;
        this.x = x;
        this.y = y;
        slideTo = new Point(x, y);

        //Nova imagem do bloco
        tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);

        //Animações
        startImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        combineImage = new BufferedImage(WIDTH * 2, HEIGHT * 2, BufferedImage.TYPE_INT_ARGB);

        drawTile(); //Desenha número desejado
    }

    //===========================UPDATE=======================================//
    public void update() {
        animation();
    }

    //============================ANIMATION===================================//
    public void animation() {
        if (startAnimation) {
            AffineTransform transform = new AffineTransform();
            transform.translate(WIDTH / 2 - renderSpeed * WIDTH / 2, HEIGHT / 2 - renderSpeed * HEIGHT / 2);
            transform.scale(renderSpeed, renderSpeed);
            Graphics2D g2d = (Graphics2D) startImage.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0, 0, 0, 0));
            g2d.fillRect(0, 0, WIDTH, HEIGHT);
            g2d.drawImage(startImage, transform, null);
            renderSpeed = renderSpeed + 0.1;
            g2d.dispose();

            if (renderSpeed >= 2) {
                startAnimation = false;
            }
        } else if (combineAnimation) {
            AffineTransform transform = new AffineTransform();
            transform.translate(WIDTH / 2 - inicialSize * WIDTH / 2, HEIGHT / 2 - inicialSize * HEIGHT / 2);
            transform.scale(inicialSize, inicialSize);
            Graphics2D g2d = (Graphics2D) combineImage.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0, 0, 0, 0));
            g2d.fillRect(0, 0, WIDTH, HEIGHT);
            g2d.drawImage(tileImage, transform, null);
            inicialSize = inicialSize + 0.1;
            g2d.dispose();

            if (inicialSize >= 1) {
                combineAnimation = false;
            }
        }
    }

    //============================DRAW========================================//
    public void draw(Graphics2D g) {
        if (startAnimation) {
            g.drawImage(startImage, x, y, null);
        } else if (combineAnimation) {
            g.drawImage(combineImage, (int) (x + WIDTH / 2 - inicialSize * WIDTH / 2), (int) (y + HEIGHT / 2 - inicialSize * HEIGHT / 2), null);
        } else {
            g.drawImage(tileImage, x, y, null);
        }
    }

    //======================DRAW TILE=========================================//
    //Desenha as peças 
    private void drawTile() {
        Graphics2D g = (Graphics2D) tileImage.getGraphics();

        //Atribuição das cores das peças
        switch (tileValue) {
            case 2:
                tileBackground = new Color(0xEEE4DA);   //Cor da peça
                tileText = new Color(0x000000);         //Cor do numero da peça
                break;
            case 4:
                tileBackground = new Color(0xEDE0C8);
                tileText = new Color(0x000000);
                break;
            case 8:
                tileBackground = new Color(0xF2B179);
                tileText = new Color(0xFFFFFF);
                break;
            case 16:
                tileBackground = new Color(0xF59563);
                tileText = new Color(0xFFFFFF);
                break;
            case 32:
                tileBackground = new Color(0xF67C5F);
                tileText = new Color(0xFFFFFF);
                break;
            case 64:
                tileBackground = new Color(0xF65E3B);
                tileText = new Color(0xFFFFFF);
                break;
            case 128:
                tileBackground = new Color(0xEDCF72);
                tileText = new Color(0xFFFFFF);
                break;
            case 256:
                tileBackground = new Color(0xEDCC61);
                tileText = new Color(0xFFFFFF);
                break;
            case 512:
                tileBackground = new Color(0xEDC850);
                tileText = new Color(0xFFFFFF);
                break;
            case 1024:
                tileBackground = new Color(0xEDC53F);
                tileText = new Color(0xFFFFFF);
                break;
            case 2048:
                tileBackground = new Color(0xEDC22E);
                tileText = new Color(0xFFFFFF);
                break;

        }

        //Desenha informações do bloco
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(tileBackground);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(tileText);

        //Fontes dos blocos
        tileFont = Game.main.deriveFont(40f);
        g.setFont(tileFont);

        //Centralizando texto
        int drawX = WIDTH / 2 - MessageSize.getMessageWidth("" + tileValue, tileFont, g) / 2;
        int drawY = HEIGHT / 2 + MessageSize.getMessageHeight("" + tileValue, tileFont, g) / 2;
        g.drawString("" + tileValue, drawX, drawY);
        g.dispose();

    }

    //========================GETTERS e SETTERS===============================//
    public int getValue() {
        return tileValue;
    }

    public void setValue(int value) {
        this.tileValue = value;
        drawTile();
    }

    public boolean iscanCombine() {
        return canCombine;
    }

    public void setCanCombine(boolean canCombine) {
        this.canCombine = canCombine;
    }

    public Point getSlideTo() {
        return slideTo;
    }

    public void setSlideTo(Point slideTo) {
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
        if (combineAnimation) {
            inicialSize = 0.5;
        }
    }

    //========================================================================//
}

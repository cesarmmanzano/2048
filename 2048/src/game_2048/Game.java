package game_2048;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, Runnable, MouseListener, MouseMotionListener {

    //implements -> por conta da thread
    private static final long serialVersionUID = 1L;

    //Inicia variáveis de altura, largura e fonte
    public static final int WIDTH = 500;
    public static final int HEIGHT = 560;
    public static final Font main = new Font("Algerian", Font.PLAIN, 28);

    private Thread game;
    private boolean running; //Checa se o jogo está rodando

    //Usado para desenhar no JPanel
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    private Color background;
    private GUIScreen screen;

    //========================================================================//
    public Game() {
        setFocusable(true);

        //Determina tamanho do frame
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        screen = GUIScreen.getInstance();
        screen.add("Menu", new GUIMainMenuPanel());
        screen.add("Jogar", new PlayPanel());
        screen.setCurrentPanel("Menu");
    }

    //========================================================================//
    private void update() {
        screen.update();
        Keyboard.update();
    }

    //========================================================================//
    private void render() {
        Graphics2D g = (Graphics2D) image.getGraphics();
        background = new Color(0xDCDCDC);
        g.setColor(background);
        g.fillRect(0, 0, WIDTH, HEIGHT); //"Clear screen" - tela branca no fundo
        screen.render(g);

        g.dispose();

        //JPanel graphics - mostra no JPanel
        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g.dispose();

    }

    //========================================================================//
    @Override
    public void run() {
        int fps = 0, updates = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000.0 / 60;

        //Ultimo update em nanoseg
        double then = System.nanoTime();

        //Updates necessários - caso der erro em render()
        double unprocessed = 0;

        while (running) {

            boolean shouldRender = false;

            double now = System.nanoTime();
            unprocessed = unprocessed + (now - then) / nsPerUpdate;
            then = now;

            while (unprocessed >= 1) {
                updates++;
                update();
                unprocessed--;
                shouldRender = true;

            }

            if (shouldRender) {
                fps++;
                render();
                shouldRender = false;
            } else {
                try {
                    Thread.sleep(1); //Sleep thread -> shouldRender = false
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //FPS Timer
        if (System.currentTimeMillis() - fpsTimer > 1000) {
            System.out.printf("%d fps %d updates", fps, updates);
            System.out.println();
            fps = 0;
            updates = 0;
            fpsTimer += 1000;
        }
    }

    //========================================================================//
    //Start Thread
    public synchronized void start() {
        if (running) {
            return;
        }

        running = true;
        game = new Thread(this, "game");
        game.start();
    }

    //Stop Thread
    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        System.exit(0);
    }

    //========================================================================//
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Keyboard.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Keyboard.keyReleased(e);
    }

    //========================================================================//
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        screen.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        screen.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        screen.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        screen.mouseMoved(e);

    }
}

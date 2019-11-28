package pkg2048;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import static java.awt.Font.ITALIC;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, Runnable, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;

    //Inicia variáveis de altura, largura e fonte
    public static final int WIDTH = 500;
    public static final int HEIGHT = 560;

    //Fonte usada
    public static final Font main = new Font("Algerian", ITALIC, 30);

    //Para que as atualizações/desenhos sejam feitos ao mesmo tempo do que o gui e swing
    private Thread game;

    //Checa se o jogo está rodando
    private boolean running;

    //Usado para desenhar no JPanel
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    private Color background;
    private Screen screen;

    //========================================================================//
    public Game() {

        //Para poder usar o teclado
        setFocusable(true);

        //Determina tamanho do frame
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        screen = Screen.getInstance();
        screen.add("Menu", new MenuPanel());
        screen.add("Jogar", new PlayPanel());
        screen.setCurrentPanel("Menu");
    }

    //========================================================================//
    private void update() { //will be callde 60times/seg
        screen.update();
        KeyboardInput.update();
    }

    //========================================================================//
    private void draw() { 
        Graphics2D g = (Graphics2D) image.getGraphics();    //local onde vamos fazer os desenhos
        background = new Color(0xDCDCDC);
        g.setColor(background);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        screen.draw(g);

        g.dispose();    //libera os recursos dos sistema relacionados a janela

        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g.dispose();

    }

    //========================================================================//

    @Override
    public void run() {
        int fpsControl = 0, updates = 0;
        long fpsTemp = System.currentTimeMillis();
        double fps = 1000000000.0 / 70; //acompanha quantos nanos segundos tem entre updates
        
        //Tempo do ultimo update, em nanoseg
        double previousTemp = System.nanoTime();

        //Updates necessários - caso der erro em draw()/renderização
        double notProcessed = 0;

        //enquanto estiver rodando
        while (running){

            boolean deveRenderizar = false;

            double actualTemp = System.nanoTime(); //tempo atual
            notProcessed = notProcessed + (actualTemp - previousTemp) / fps; //gera a quantidade de updates necessarios baseado em quanto tempo passou
            previousTemp = actualTemp;

            while (notProcessed >= 1) {  //enquanto houver o que processar
                updates++;
                update();
                notProcessed--;
                deveRenderizar = true;
            }

            if (deveRenderizar) { //se precisa renderizar/desenhar
                fpsControl++;
                draw();
                deveRenderizar = false;
            } else {    //Se nao esta renderizando
                try {
                    Thread.sleep(1); //Sleep thread -> shouldRender = false
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //FPS Timer, debug e reset de variaveis caso o tempo atual menos quanto tempo se passou for maior que um segundo 
        if (System.currentTimeMillis() - fpsTemp > 1000) {  
            fpsControl = 0;
            updates = 0;
            fpsTemp = fpsTemp + 1000;
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
        KeyboardInput.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KeyboardInput.keyReleased(e);
    }

    //========================================================================//
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
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

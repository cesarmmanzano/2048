package game_2048;

import java.awt.*;
import java.awt.event.*;        // For buttons
import java.awt.image.*;        //para criacao e modificacao de imagens
import javax.swing.JPanel;      //para colocar na tela

public class Game extends JPanel implements KeyListener, Runnable {  //used to draw on

    private static final long serialVersionUID = 1L;
    public static final int WIDTH = 500;    //aqui setamos o que eh imutavel como largura e altura da janela e a fonte de texto
    public static final int HEIGHT = 560;
    public static final Font main = new Font("Bebas Nue Regular", Font.PLAIN, 28);
    private Thread game;  //creio que seja da inicializacao propriamente dita do jogo
    private boolean running;    //para acompanhar se o jogo esta rodando
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); //ALL DRAWING WILL BE DONE ON image e ai desenhado no jpanel
    private Gameboard board;

    private long startTime;
    private long elapsed;
    private boolean set;

    //construtor
    public Game() {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); //determinat how big the frame is
        addKeyListener(this);

        board = new Gameboard(WIDTH / 2 - Gameboard.BOARD_WIDTH / 2, HEIGHT - Gameboard.BOARD_HEIGHT - 10); //middle in x 10 pixel from the bottom
    }

    private void update() {  //called 60 times a second
        board.update();
        Keyboard.update();
    }

    private void render() {  //called 60 times a second
        /*graphic object is basically how do u draw to a image, the currently drawing of the image labels etc will be draw in the jpanel*/
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, WIDTH, HEIGHT); //WHITE RECTANGAL AROUND THE SCREEN, U BASC CLEARED THE SCREEN
        board.render(g);
        //render board
        g.dispose();

        Graphics2D g2d = (Graphics2D) getGraphics();
        g2d.drawImage(image, 0, 0, null);
        g.dispose();

    }

    @Override
    public void run() {
        /*when u first run will set this variables*/
        int fps = 0, updates = 0;
        long fpsTimer = System.currentTimeMillis();
        double nsPerUpdate = 1000000000.0 / 60; //nano segundos por update

        //last update time in nano seg
        double then = System.nanoTime();
        double unprocessed = 0; //how many updates we need to do

        while (running) {
            boolean shouldRender = false;
            //while running we get the new time
            double now = System.nanoTime();
            unprocessed += (now - then) / nsPerUpdate; //this will count the updates we need to do
            then = now; //o que foi recebe o agora para q no futuro seja usado da forma necessaria

            //update queue
            //enquanto houver algo para processar
            while (unprocessed >= 1) {
                updates++;
                update();
                unprocessed--;
                shouldRender = true;

            }

            //render 
            if (shouldRender) {
                fps++;
                render();
                shouldRender = false;
            } else {
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //Fps timer
        if (System.currentTimeMillis() - fpsTimer > 1000) {
            System.out.printf("%d fps %d updates", fps, updates);
            System.out.println();
            //reset the variables
            fps = 0;
            updates = 0;
            fpsTimer += 1000;
        }
    }

    //this has to be synchronized so it wont change threads in the middle of it
    public synchronized void start() {
        /*if the game is alredy running it returns if not it will inicialize a new game thread*/
        if (running) {
            return;
        }
        running = true;
        game = new Thread(this, "game");
        game.start();
    }

    public synchronized void stop() {
        /*if the game is alredy stoped just return, else set it false and exit the program*/
        if (!running) {
            return;
        }
        running = false;
        System.exit(0);
    }

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

}

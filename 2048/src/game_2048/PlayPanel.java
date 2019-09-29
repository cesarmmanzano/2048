package game_2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class PlayPanel extends Panel {

    private Gameboard board;
    private BufferedImage info;
    //private ScoreManager scores;
    private Font scoreFont;

    // Game Over
    private Button tryAgain;
    private Button mainMenu;
    private int smallButtonWidth = 160;
    private int spacing = 20;
    private int largeButtonWidth = smallButtonWidth * 2 + spacing;
    private int buttonHeight = 50;
    private boolean added;
    private int alpha = 0;
    private Font gameOverFont;

    public PlayPanel() {
        gameOverFont = Game.main.deriveFont(70f);
        board = new Gameboard(Game.WIDTH / 2 - Gameboard.BOARD_WIDTH / 2, Game.HEIGHT - Gameboard.BOARD_HEIGHT - 20);
        info = new BufferedImage(Game.WIDTH, 200, BufferedImage.TYPE_INT_RGB);

        mainMenu = new Button(Game.WIDTH / 2 - largeButtonWidth / 2, 450, largeButtonWidth, buttonHeight);
        tryAgain = new Button(Game.WIDTH / 2 - largeButtonWidth / 2, 380, largeButtonWidth, buttonHeight);

        tryAgain.setText("Tentar Novamente");
        mainMenu.setText("Voltar para o Menu");

        tryAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.reset();
                alpha = 0;
                remove(tryAgain);
                remove(mainMenu);
                added = false;
            }
        });

        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.reset();
                alpha = 0;
                remove(tryAgain);
                remove(mainMenu);
                added = false;
                Screen.getInstance().setCurrentPanel("Menu");
            }
        });
    }

    //========================================================================//
    public void drawGameOver(Graphics2D g) {
        g.setColor(new Color(222, 222, 222, alpha));
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.setColor(Color.red);
        g.drawString("FIM DE JOGO", Game.WIDTH / 2 - MessageSize.getMessageWidth("FIM DE JOGO!", gameOverFont, g) / 2, 250);
    }

    //========================================================================//
    @Override
    public void update() {
        board.update();
        if (board.checkDead()) {
            alpha++;
            if (alpha > 170) {
                alpha = 170;
            }
        }
    }

    //========================================================================//
    @Override
    public void draw(Graphics2D g) {
        //drawGui(g);
        board.draw(g);

        if (board.checkDead()) {
            if (!added) {
                added = true;
                add(mainMenu);
                add(tryAgain);
            }
            drawGameOver(g);
        }
        super.draw(g);
    }
}

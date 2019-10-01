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
    private Font scoreFont;

    //Botões usados
    private Button playAgain;
    private Button backToMenu;
    private Button newGame;
    private Button easterEgg;

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

        backToMenu = new Button(Game.WIDTH / 2 - largeButtonWidth / 2, 450, largeButtonWidth, buttonHeight);
        playAgain = new Button(Game.WIDTH / 2 - largeButtonWidth / 2, 380, largeButtonWidth, buttonHeight);
        newGame = new Button(Game.WIDTH / 2 - 130 / 2 + 150, 20, 130, buttonHeight);
        easterEgg = new Button(Game.WIDTH / 2 - 150 / 2 + 150, 20, 150, buttonHeight);

        backToMenu.setText("Voltar para o Menu");
        playAgain.setText("Tentar Novamente");
        newGame.setText("Novo Jogo");
        easterEgg.setText("");

        //Ações ao clicar sobre o botão de jogar novamente
        playAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.resetBoard();
                alpha = 0;
                remove(playAgain);
                remove(backToMenu);              
                add(easterEgg);
                add(newGame);
                added = false;
            }
        });

        //Ações ao clicar sobre o botão de voltar ao menu
        backToMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.resetBoard();
                alpha = 0;
                remove(playAgain);
                remove(backToMenu);   
                added = false;
                Screen.getInstance().setCurrentPanel("Menu");
                add(easterEgg);
                add(newGame);
            }
        });

        //Ações ao clicar sobre o botão de easterEgg
        easterEgg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.resetBoard();
            }
        });
        add(easterEgg);
        
        //Ações ao clicar sobre o botão de iniciar novo jogo        
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.resetBoard();
            }
        });
        add(newGame);

    }

    //========================================================================//
    public void drawGameOver(Graphics2D g) {
        g.setColor(new Color(222, 222, 222, alpha));
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.setColor(Color.red);
        g.drawString("FIM DE JOGO", Game.WIDTH / 2 - MessageSize.getMessageWidth("FIM DE JOGO", gameOverFont, g) / 2, 250);
    }

    //========================================================================//
    public void drawGameWin(Graphics2D g) {
        g.setColor(new Color(222, 222, 222, alpha));
        g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
        g.setColor(Color.red);
        g.drawString("VITORIA", Game.WIDTH / 2 - MessageSize.getMessageWidth("VITORIA", gameOverFont, g) / 2, 250);
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
        board.draw(g);

        if (board.checkDead()) {
            if (!added) {
                added = true;
                add(backToMenu);
                add(playAgain);
                remove(newGame);
                remove(easterEgg);
            }
            drawGameOver(g);
        }
        
        super.draw(g);
    }
}

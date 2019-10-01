package game_2048;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends Panel {

    private Font titleFont = Game.main.deriveFont(80f);
    public String title = "2048";
    private Game game;

    private Color titleColor = new Color(0x4B0082);
    
    //Classe responsavel pela tela e features do menu.
    public MenuPanel() {
        super();     
        Button playButton = new Button(Game.WIDTH / 2 - 150 / 2 - 100, 220, 150, 60);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Screen.getInstance().setCurrentPanel("Jogar");
            }
        });
        playButton.setText("Jogar");
        add(playButton);

        Button gitButton = new Button(Game.WIDTH / 2 - 150 / 2 + 100, 220, 150, 60);
        gitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desk = java.awt.Desktop.getDesktop();
                try {
                    desk.browse(new java.net.URI("https://github.com/cesarmmanzano/2048"));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        gitButton.setText("GitHub");
        add(gitButton);

        Button quitButton = new Button(Game.WIDTH / 2 - 350 / 2, 320, 350, 60);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        quitButton.setText("Sair");
        add(quitButton);
    }

    @Override
    public void draw(Graphics2D g) {
        super.draw(g);
        g.setFont(titleFont);
        g.setColor(titleColor);
        g.drawString(title, Game.WIDTH / 2 - MessageSize.getMessageWidth(title, titleFont, g) / 2, 125);
    }
}

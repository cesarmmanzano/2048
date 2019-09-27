package game_2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMainMenuPanel extends GUIPanel {

    private Font titleFont = Game.main.deriveFont(80f);
    public String title = "MENU 2048";
    private int buttonWidth = 200;
    private int buttonHeight = 60;

    public GUIMainMenuPanel() {
        super();
        GUIButton playButton = new GUIButton(buttonWidth / 2 - 80, 220, buttonWidth, buttonHeight);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIScreen.getInstance().setCurrentPanel("Jogar");
            }
        });
        playButton.setText("Jogar");
        add(playButton);

        GUIButton gitButton = new GUIButton(buttonWidth / 2 + 180, 220, buttonWidth, buttonHeight);
        gitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIScreen.getInstance().setCurrentPanel("Repositorio GitHub");
            }
        });
        gitButton.setText("GitHub");
        add(gitButton);

        /*
         GUIButton quitButton = new GUIButton(Game.WIDTH / 2 - buttonWidth / 2, 400, buttonWidth, buttonHeight);
         quitButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
         System.exit(0);
         }
         });
         quitButton.setText("Sair");
         add(quitButton);
         */
    }

    @Override
    public void render(Graphics2D g) {
        super.render(g);
        g.setFont(titleFont);
        g.setColor(Color.black);
        g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, 150);
    }
}

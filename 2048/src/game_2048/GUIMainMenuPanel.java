package game_2048;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUIMainMenuPanel extends GUIPanel {

    private Font titleFont = Game.main.deriveFont(80f);
    public String title = "2048";
    private int buttonWidth = 150;
    private int buttonHeight = 60;
    private Game game;

    private Color titleColor = new Color(0x4B0082);

    public GUIMainMenuPanel() {
        super();
        GUIButton playButton = new GUIButton(Game.WIDTH / 2 - buttonWidth / 2, 220, buttonWidth, buttonHeight);
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GUIScreen.getInstance().setCurrentPanel("Jogar");
            }
        });
        playButton.setText("Jogar");
        add(playButton);

        GUIButton gitButton = new GUIButton(Game.WIDTH / 2 - buttonWidth / 2, 320, buttonWidth, buttonHeight);
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

        GUIButton quitButton = new GUIButton(Game.WIDTH / 2 - buttonWidth / 2, 420, buttonWidth, buttonHeight);
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
    public void render(Graphics2D g) {
        super.render(g);
        g.setFont(titleFont);
        g.setColor(titleColor);
        g.drawString(title, Game.WIDTH / 2 - DrawUtils.getMessageWidth(title, titleFont, g) / 2, 125);
    }
}

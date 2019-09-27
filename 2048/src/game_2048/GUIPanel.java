package game_2048;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GUIPanel {

    private ArrayList<GUIButton> buttons;

    //========================================================================//
    public GUIPanel() {
        buttons = new ArrayList<GUIButton>();
    }

    //========================================================================//
    public void update() {
        for (GUIButton b : buttons) {
            b.update();
        }
    }

    //========================================================================//
    public void render(Graphics2D g) {
        for (GUIButton b : buttons) {
            b.render(g);
        }
    }

    //========================================================================//
    public void add(GUIButton button) {
        buttons.add(button);
    }

    public void remove(GUIButton button) {
        buttons.remove(buttons);
    }

    //========================================================================//
    public void mousePressed(MouseEvent e) {
        for (GUIButton b : buttons) {
            b.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e) {
        for (GUIButton b : buttons) {
            b.mouseReleased(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        for (GUIButton b : buttons) {
            b.mouseDragged(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
        for (GUIButton b : buttons) {
            b.mouseMoved(e);
        }
    }
}

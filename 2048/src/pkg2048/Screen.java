package pkg2048;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class Screen {

    //A classe identificará em qual tela estamospar podermos fazer os links corretos
    
    private static Screen screen;

    /*
     O HashMap trabalha com o conceito key-value pairs
     Cada elemento possui uma chave(String) eu seu valor associado (Panel)
     */
    private HashMap<String, Panel> panels;
    private String currentPanel = "";

    private Screen() {
        panels = new HashMap<String, Panel>();
    }

    public static Screen getInstance() {
        if (screen == null) {
            screen = new Screen();
        }
        return screen;
    }

    public void update() {
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).update();
        }
    }

    public void draw(Graphics2D g) {
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).draw(g);
        }
    }

    public void add(String panelName, Panel panel) {
        panels.put(panelName, panel);
    }

    public void setCurrentPanel(String panelName) {
        currentPanel = panelName;
    }

    public void mouseReleased(MouseEvent e) {
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).mouseReleased(e);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).mouseDragged(e);
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).mouseMoved(e);
        }
    }
}

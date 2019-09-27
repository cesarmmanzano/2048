package game_2048;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class GUIScreen {

    private static GUIScreen screen;
    private HashMap<String, GUIPanel> panels;
    private String currentPanel = "";

    private GUIScreen() {
        panels = new HashMap<String, GUIPanel>();
    }

    public static GUIScreen getInstance() {
        if (screen == null) {
            screen = new GUIScreen();
        }
        return screen;
    }

    public void update() {
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).update();
        }
    }

    public void render(Graphics2D g) {
        if (panels.get(currentPanel) != null) {
            panels.get(currentPanel).render(g);
        }
    }

    public void add(String panelName, GUIPanel panel) {
        panels.put(panelName, panel);
    }
    
    public void setCurrentPanel(String panelName){
        currentPanel = panelName;
    }
    
    public void mousePressed(MouseEvent e){
        if(panels.get(currentPanel) != null){
            panels.get(currentPanel).mousePressed(e);
        }
    }
    
    public void mouseReleased(MouseEvent e){
        if(panels.get(currentPanel) != null){
            panels.get(currentPanel).mouseReleased(e);
        }
    }
    
    public void mouseDragged(MouseEvent e){
        if(panels.get(currentPanel) != null){
            panels.get(currentPanel).mouseDragged(e);
        }
    }
    
    public void mouseMoved(MouseEvent e){
        if(panels.get(currentPanel) != null){
            panels.get(currentPanel).mouseMoved(e);
        }
    }
}

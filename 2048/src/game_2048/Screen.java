package game_2048;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class Screen {

    private static Screen screen;
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

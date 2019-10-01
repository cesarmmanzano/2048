package game_2048;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

public class Button {

    private State currentState = State.RELEASED;
    private Rectangle clickBox;
    private LinkedList<ActionListener> actionListeners;
    private String text = "";

    private Color released = new Color(0x00008B);
    private Color hover = new Color(0x008000);
    private Color pressed = new Color(0x008000);

    private Font font = Game.main.deriveFont(22f);

    //========================================================================//
    private enum State {
        RELEASED, HOVER, PRESSED
    }

    //========================================================================//
    public Button(int x, int y, int width, int height) {
        clickBox = new Rectangle(x, y, width, height);
        actionListeners = new LinkedList<ActionListener>();
    }

    //========================================================================//
    public void update() {
    }

    //========================================================================//
    //Seta a cor do botao de acordo com o estado atual
    public void draw(Graphics2D g) {
        if (currentState == State.RELEASED) {   //released state
            g.setColor(released);
            g.fill(clickBox);
        } else if (currentState == State.PRESSED) { //pressed state
            g.setColor(pressed);
            g.fill(clickBox);
        } else {                //hover state
            g.setColor(hover);
            g.fill(clickBox);
        }

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(text, clickBox.x + clickBox.width / 2 - MessageSize.getMessageWidth(text, font, g) / 2, clickBox.y + clickBox.height / 2 + MessageSize.getMessageHeight(text, font, g) / 2);
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    //========================================================================//
    //Setar o estado atual do botao
    public void mousePressed(MouseEvent e) {
        if (clickBox.contains(e.getPoint())) {
            currentState = State.PRESSED;
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (clickBox.contains(e.getPoint())) {
            for (ActionListener al : actionListeners) {
                al.actionPerformed(null);
            }
        }
        currentState = State.RELEASED;
    }

    public void mouseDragged(MouseEvent e) {
        if (clickBox.contains(e.getPoint())) {
            currentState = State.PRESSED;
        } else {
            currentState = State.RELEASED;
        }
    }

    public void mouseMoved(MouseEvent e) {
        if (clickBox.contains(e.getPoint())) {
            currentState = State.HOVER;
        } else {
            currentState = State.RELEASED;
        }
    }

    //========================================================================//
    //Getters e Setters
    public int getX() {
        return clickBox.x;
    }

    public int getY() {
        return clickBox.y;
    }

    public int getWidth() {
        return clickBox.width;
    }

    public int getHeight() {
        return clickBox.height;
    }

    public void setText(String text) {
        this.text = text;
    }
}

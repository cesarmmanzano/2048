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
    
    //Linked List de ações do botão
    private LinkedList<ActionListener> actionListeners;
    private String text = "";

    private Color released = new Color(0x00008B);
    private Color hover = new Color(0x008000);

    private Font font = Game.main.deriveFont(22f);

    //========================================================================//
    //Estados do botao
    private enum State {

        RELEASED, HOVER
    }

    //========================================================================//
    //Construtor do botao
    public Button(int x, int y, int width, int height) {
        clickBox = new Rectangle(x, y, width, height);
        actionListeners = new LinkedList<ActionListener>();

    }

    //========================================================================//
    public void update() {
    }

    //========================================================================//
    //Desenha o botao de acordo com o estado
    public void draw(Graphics2D g) {
        if (currentState == State.RELEASED) {
            g.setColor(released);
            g.fill(clickBox);
        } else {
            g.setColor(hover);
            g.fill(clickBox);
        }

        g.setColor(Color.white);
        g.setFont(font);
        g.drawString(text, clickBox.x + clickBox.width / 2 - MessageSize.getStringWidth(text, font, g) / 2, clickBox.y + clickBox.height / 2 + MessageSize.getStringHeight(text, font, g) / 2);
    }

    public void addActionListener(ActionListener listener) {
        actionListeners.add(listener);
    }

    //========================================================================//
    //Set dos estados do botao
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

    //========================GETTERS e SETTERS===============================//
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

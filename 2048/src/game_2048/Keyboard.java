package game_2048;

import java.awt.event.*;

public class Keyboard {

    //Classe checa quais teclas foram clicadas
    
    public static boolean[] pressed = new boolean[256];
    public static boolean[] previous = new boolean[256];

    private Keyboard() {
    }

    //========================================================================//
    
    public static void update() {
        for (int i = 0; i < 8; i++) {   
            //Setas
            if (i == 0) {
                previous[KeyEvent.VK_LEFT] = pressed[KeyEvent.VK_LEFT];
            }
            if (i == 1) {
                previous[KeyEvent.VK_RIGHT] = pressed[KeyEvent.VK_RIGHT];
            }
            if (i == 2) {
                previous[KeyEvent.VK_UP] = pressed[KeyEvent.VK_UP];
            }
            if (i == 3) {
                previous[KeyEvent.VK_DOWN] = pressed[KeyEvent.VK_DOWN];
            }
            
            //WASD
            if (i == 4) {
                previous[KeyEvent.VK_A] = pressed[KeyEvent.VK_A];
            }
            if (i == 5) {
                previous[KeyEvent.VK_D] = pressed[KeyEvent.VK_D];
            }
            if (i == 6) {
                previous[KeyEvent.VK_W] = pressed[KeyEvent.VK_W];
            }
            if (i == 7) {
                previous[KeyEvent.VK_S] = pressed[KeyEvent.VK_S];
            }
        }
    }

    //========================================================================//
    
    public static void keyPressed(KeyEvent e) {
        pressed[e.getKeyCode()] = true;
    }

    public static void keyReleased(KeyEvent e) {
        pressed[e.getKeyCode()] = false;
    }

    public static boolean typed(int keyEvent) {
        return !pressed[keyEvent] && previous[keyEvent];
    }

}

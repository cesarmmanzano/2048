package game_2048;

import java.awt.event.KeyEvent;

public class Keyboard {

    public static boolean[] pressed = new boolean[256];
    public static boolean[] prev = new boolean[256];

    private Keyboard() {
    } //construtor privado

    public static void update() {
        for (int i = 0; i < 4; i++) //here hes only doing with for keys maybe we will have to change,or just put an OR to the if`s
        {
            if (i == 0) {
                prev[KeyEvent.VK_LEFT] = pressed[KeyEvent.VK_LEFT];
            }
            if (i == 1) {
                prev[KeyEvent.VK_RIGHT] = pressed[KeyEvent.VK_RIGHT];
            }
            if (i == 2) {
                prev[KeyEvent.VK_UP] = pressed[KeyEvent.VK_UP];
            }
            if (i == 3) {
                prev[KeyEvent.VK_DOWN] = pressed[KeyEvent.VK_DOWN];
            }
        }
    }

    /*this way allow more than just one key pressed without problems*/
    public static void keyPressed(KeyEvent e) {
        pressed[e.getKeyCode()] = true;
    }

    public static void keyReleased(KeyEvent e) {
        pressed[e.getKeyCode()] = false;
    }

    public static boolean typed(int keyEvent) {
        return !pressed[keyEvent] && prev[keyEvent];
    }

}

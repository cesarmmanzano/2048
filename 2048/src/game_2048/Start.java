package game_2048;

import javax.swing.*;

public class Start extends JPanel {

    public static void main(String[] args) {
        Game game = new Game();

        JFrame window = new JFrame("2048");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //coloca como defalt a saida com o x "se nao me engano"
        window.setResizable(false); //nao permite mudancao de tamanho na janela
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null); //centro da tela
        window.setVisible(true); //deixa a janela visivel

        game.start();
    }
}

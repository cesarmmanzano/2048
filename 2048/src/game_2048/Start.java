package game_2048;

import javax.swing.*;

public class Start extends JPanel {

    public static void main(String[] args) {
        
        //Cria novo jogo
        Game game = new Game();

        //Cria janela do JFrame
        JFrame window = new JFrame("2048");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //Exit on close
        window.setResizable(false); 
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null); 
        window.setVisible(true); //Deixa a janela visível

        game.start();   //Start game
    }
}

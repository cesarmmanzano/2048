package game_2048;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Start extends JPanel {

    public static void main(String[] args) {

        //Cria novo jogo
        Game game2048 = new Game();

        //Cria janela do JFrame
        try {
            JFrame window = new JFrame("2048"); //Cria janela
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
            window.setResizable(false);
            window.add(game2048);
            window.pack();
            window.setLocationRelativeTo(null);
            window.setVisible(true); //Deixa a janela visível
        } catch (Exception e) {
            System.out.println("Erro na abertura do JFrame");
        }

        game2048.start();   //Inicia o JOGO
    }
}

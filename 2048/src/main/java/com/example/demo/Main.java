package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.swing.JFrame;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
   

        //Cria novo jogo
        Game game2048 = new Game();

        //Cria janela do JFrame
        try {
            JFrame newGame = new JFrame("2048"); //Cria janela
            newGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newGame.setResizable(false);
            newGame.add(game2048);
            newGame.pack();
            newGame.setLocationRelativeTo(null);
            newGame.setVisible(true); //Deixa a janela visível
        } catch (Exception e) {
            System.out.println("Erro na abertura do JFrame");
        }

        game2048.start();   //Inicia o JOGO
        SpringApplication.run(Main.class, args);
    }

}

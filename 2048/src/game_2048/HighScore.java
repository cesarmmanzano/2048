package game_2048;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;

public class HighScore {

    //Controla score do jogo
    public int currentScore;
    public int highScore;

    //Cores do score
    public Color scoreColor = new Color(0xA020F0);
    public Color bestColor = new Color(0x191970);

    //Fonte do score
    public Font scoreFont;

    //Caminho para salvar
    public String saveFile;

    //Nome do arquivo
    public String fileName = "SaveHighScore";

    public HighScore() {
    }

    /*
     Toda manipulção com arquivos necessita try-catch
     Exemplo: não existencia do arquivo
     */
    //========================================================================//
    //Função para criar o arquivo
    public void createScore() {
        try {
            //Cria arquivo com sua localização e nome
            File file = new File(saveFile, fileName); 
            
            //Para poder escrever no arquivo
            FileWriter fw = new FileWriter(file); 
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("" + 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //========================================================================//
    //Função para pegar o valor do arquivo
    public void loadScore() {
        try {
            File file = new File(saveFile, fileName);

            if (!file.isFile()) {
                createScore();
            }
            
            //Leitura do arquivo
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            highScore = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //========================================================================//
    //Função para escrever o novo 'highScore' no arquivo
    public void setScore() {
        FileWriter output = null;
        try {

            File file = new File(saveFile, fileName);
            output = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(output);
            bw.write("" + highScore);
            bw.close(); 

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

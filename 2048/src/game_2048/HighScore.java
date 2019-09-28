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
    public String saveFile;
    public Font scoreFont;
    public String fileName = "SaveHighScore";
    public Color scoreColor = new Color(0xA020F0);
    public Color bestColor = new Color(0x191970);

    public HighScore() {
    }

    //========================================================================//
    public void createHighScore() {
        try {
            File file = new File(saveFile, fileName);

            FileWriter output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("" + 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //========================================================================//
    public void loadHighScore() {
        try {
            File file = new File(saveFile, fileName);
            if (!file.isFile()) {
                createHighScore();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            highScore = Integer.parseInt(reader.readLine());
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //========================================================================//
    public void setHighScore() {
        FileWriter output = null;

        try {
            File file = new File(saveFile, fileName);
            output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);

            writer.write("" + highScore);

            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //========================================================================//
}

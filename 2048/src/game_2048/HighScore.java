package game_2048;

import java.awt.*;
import java.io.*;

public class HighScore {

    //Controla score do jogo
    public int currentScore = 0;
    public int highScore = 0;
    public String saveFile;
    public Font scoreFont;
    public String fileName = "SaveHighScore";

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

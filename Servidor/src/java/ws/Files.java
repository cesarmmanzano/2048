package ws;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Formatter;
import java.util.Scanner;


//Cria arquivos para salvar a direcao desejada
public class Files {

    public Files() {

    }

    //Caminho que será salvo o arquivo
    static String path = "C:\\Users\\cesar\\Desktop\\2048-master\\Servidor\\src\\java\\direction.txt";
    //Cria arquivo
    public static void createDir() {
        try {
            //Cria arquivo com sua localização e nome
            File file = new File(path);

            //Para poder escrever no arquivo
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("" + 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    //Pega a string do arquivo
    public static String loadDir() {
        String dir;
        try {
            File file = new File(path);
            Scanner sc = new Scanner(file);
            
            if (!file.isFile()) {
                createDir();
            }
            dir = sc.next();
            return dir;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    
    //Modifica a direção
    public static boolean setDir(String Texto) {
        FileWriter output = null;
        try {
            Formatter file = new Formatter(path);
             
            file.format("" + Texto);
            file.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}

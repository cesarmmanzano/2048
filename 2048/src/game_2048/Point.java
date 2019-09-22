package game_2048;

public class Point {
    /*java ja tem uma classe point porem no nosso programa ela pode considerar coluna como x e linha como y o que confunde*/
    /*essa classe so guarda a posicao certa nas variaveis corretas para nao dar problemas futuros*/

    public int row;
    public int col;

    public Point(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

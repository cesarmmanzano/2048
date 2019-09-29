package game_2048;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.swing.*;

//========================================================================//
public class Gameboard extends HighScore {

    public enum Direction {

        LEFT, RIGHT, UP, DOWN
    }

    //Board será 4x4
    public static final int ROWS = 4;
    public static final int COLUMNS = 4;

    //Numero de blocos iniciais 
    private int startingTile = 2;

    //Matriz para o jogo
    private Tile[][] board;

    //Verifica derrotae e vitoria
    private boolean loseGame;
    private boolean winGame;

    //Para criar Gameboard
    private BufferedImage gameBoard;

    //private BufferedImage finalBoard;
    //Cores de fundo do board e do bloco
    private Color backgroundBoard = new Color(0x776E65);
    private Color backgroundRect = new Color(0xD8BFD8);

    //Posição para desenhar na tela
    private int x;
    private int y;

    //Espaçamento entre as peças
    private static int SPACING = 10;

    //Altura e largura em pixel do board
    public static int BOARD_WIDTH = (COLUMNS + 1) * SPACING + COLUMNS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

    //Verifica se o jogo iniciou
    private boolean gameStarted;

    //========================================================================//
    public Gameboard(int x, int y) {
        try {
            //Pegar a localização para salvar o arquivo
            saveFile = Gameboard.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        scoreFont = Game.main.deriveFont(30f);

        this.x = x;
        this.y = y;

        board = new Tile[ROWS][COLUMNS];   //Board 4x4

        gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        //finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        loadScore();
        createBoardImage();
        start();
    }

    //========================================================================//
    public void update() {
        checkTypedKeys();

        if (currentScore >= highScore) {
            highScore = currentScore;
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Tile current = board[row][col];
                if (current == null) {
                    continue;
                }
                current.update();
                //reset the position
                resetPosition(current, row, col);
                if (current.getValue() == 2048) {
                    winGame = true;
                }
            }
        }
    }

    //========================================================================//
    public void draw(Graphics2D g) {

        BufferedImage finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
        g2d.setColor(new Color(0, 0, 0, 0));
        g2d.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g2d.drawImage(gameBoard, 0, 0, null);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Tile currently = board[row][col];
                if (currently == null) {
                    continue;
                }
                currently.draw(g2d);
            }
        }

        g.drawImage(finalBoard, x, y, null);
        g2d.dispose();

        g.setColor(scoreColor);
        g.setFont(scoreFont);
        g.drawString("Score: " + currentScore, 30, 40);   //g.drawString(score, vertical, horizontal);
        g.setColor(bestColor);
        g.drawString("Best: " + highScore, Game.WIDTH - MessageSize.getMessageWidth("", scoreFont, g) - 470, 80);
    }

    //==============================RESET=====================================//
    //Reseta informações quando o usuário selecionar jogar novamente
    public void resetBoard() {
        board = new Tile[ROWS][COLUMNS];
        start();
        loseGame = false;
        winGame = false;
        gameStarted = false;
        currentScore = 0;
    }

    //========================================================================//
    //Cria background do Board
    private void createBoardImage() {
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();

        //Cor de fundo do Board
        g.setColor(backgroundBoard);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);

        //Cor de fundo de cada bloco 
        g.setColor(backgroundRect);
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                int x = SPACING + SPACING * col + Tile.WIDTH * col;
                int y = SPACING + SPACING * row + Tile.HEIGHT * row;
                g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, 0, 0);
            }
        }

    }

    //========================================================================//
    private void start() {
        for (int i = 0; i < startingTile; i++) {
            spawnRandomTile();
        }

    }

    //=======================SPAWN TILE=======================================//
    //Spawn randomico de tiles
    private void spawnRandomTile() {
        Random random = new Random();
        int location = random.nextInt(ROWS * COLUMNS);
        int row = 0, col = 0;

        do {
            location = (location + 1) % (ROWS * COLUMNS);
            row = location / ROWS;
            col = location % COLUMNS;
        } while (board[row][col] != null);

        //80% de chance de spawnar 2 / 20% de chance de spawnar 4
        int value = random.nextInt(10) < 8 ? 2 : 4;

        //Posição recebe a tile
        board[row][col] = new Tile(value, getTileX(col), getTileY(row));

    }

    //========================================================================//
    private void resetPosition(Tile current, int row, int col) {
        if (current == null) {
            return;
        }

        int x = getTileX(col);
        int y = getTileY(row);
        int distX = current.getX() - x;
        int distY = current.getY() - y;

        if (Math.abs(distX) < Tile.SPEED) {
            current.setX(current.getX() - distX); //para nao ter flic na animacao 
        }
        if (Math.abs(distY) < Tile.SPEED) {
            current.setY(current.getY() - distY); //para nao ter flic na animacao 
        }

        if (distX < 0) {
            current.setX(current.getX() + Tile.SPEED);
        }
        if (distY < 0) {
            current.setY(current.getY() + Tile.SPEED);
        }
        if (distX > 0) {
            current.setX(current.getX() - Tile.SPEED);
        }
        if (distY > 0) {
            current.setY(current.getY() - Tile.SPEED);
        }
    }

    //========================================================================//
    //Checa se é possivel mover as peças
    private boolean move(int row, int col, int horizontalDirection, int verticalDirection, Direction dir) {
        boolean canMove = false;

        Tile current = board[row][col];
        if (current == null) {
            return false;
        }
        boolean move = true;
        int newCol = col;
        int newRow = row;

        while (move) {
            newCol = newCol + horizontalDirection;
            newRow = newRow + verticalDirection;
            if (outOfBounds(dir, newRow, newCol)) {
                break;
            }
            if (board[newRow][newCol] == null) { //se esta vazio
                board[newRow][newCol] = current;
                board[newRow - verticalDirection][newCol - horizontalDirection] = null;

                board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
                canMove = true;
            } else if (board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].iscanCombine()) { //se da pra combinar
                board[newRow][newCol].setCanCombine(false);
                board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);
                canMove = true;
                board[newRow - verticalDirection][newCol - horizontalDirection] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow, newCol));

                //if (board[newRow][newCol].getValue() == 4) {
                board[newRow][newCol].setCombineAnimation(true);
                //}

                currentScore = currentScore + board[newRow][newCol].getValue();
            } else {
                move = false;
            }
        }

        return canMove;
    }

    //========================================================================//
    //Checa limite do board
    private boolean outOfBounds(Direction dir, int row, int col) {
        if (dir == Direction.LEFT) {
            return col < 0;
        } else if (dir == Direction.RIGHT) {
            return col > COLUMNS - 1;
        } else if (dir == Direction.UP) {
            return row < 0;
        } else if (dir == Direction.DOWN) {
            return row > ROWS - 1;
        }
        return false;
    }

    //========================================================================//
    //Move as peças do jogo
    private void moveTiles(Direction dir) {
        boolean canMove = false;
        int horizontalDirection = 0;
        int verticalDirection = 0;

        if (dir == Direction.LEFT) {
            horizontalDirection = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLUMNS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    } else {
                        move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                }
            }
        } else if (dir == Direction.RIGHT) {
            horizontalDirection = +1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = COLUMNS - 1; col >= 0; col--) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    } else {
                        move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                }
            }
        } else if (dir == Direction.UP) {
            verticalDirection = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLUMNS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    } else {
                        move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                }
            }
        } else if (dir == Direction.DOWN) {
            verticalDirection = 1;
            for (int row = ROWS - 1; row >= 0; row--) {
                for (int col = 0; col < COLUMNS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    } else {
                        move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                }
            }
        } else {
            System.out.println(dir + "is not a valid direction.");
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Tile current = board[row][col];
                if (current == null) {
                    continue;
                }
                current.setCanCombine(true);
            }
        }
        if (canMove) {
            spawnRandomTile();
            checkDead();
        }

    }

    //========================================================================//
    /*
     Checa fim do jogo
     Percorre todas as peças checando a combinação nos arredores
     Se não houver cobinação possível -> end game
     */
    public boolean checkDead() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                if (board[row][col] == null) {
                    return false;
                }
                if (checkSurroundingTiles(row, col, board[row][col])) {
                    return false;
                }
            }
        }

        if (currentScore >= highScore) {
            highScore = currentScore;
        }
        setScore();

        return true;
    }

    //========================================================================//
    //Checa arredores das peças
    private boolean checkSurroundingTiles(int row, int col, Tile current) {
        if (row > 0) {
            Tile check = board[row - 1][col];
            if (check == null) {
                return true;
            }
            if (current.getValue() == check.getValue()) {
                return true;
            }
        }
        if (row < ROWS - 1) {
            Tile check = board[row + 1][col];
            if (check == null) {
                return true;
            }
            if (current.getValue() == check.getValue()) {
                return true;
            }
        }
        if (col > 0) {
            Tile check = board[row][col - 1];
            if (check == null) {
                return true;
            }
            if (current.getValue() == check.getValue()) {
                return true;
            }
        }
        if (col < COLUMNS - 1) {
            Tile check = board[row][col + 1];
            if (check == null) {
                return true;
            }
            if (current.getValue() == check.getValue()) {
                return true;
            }
        }
        return false;
    }

    //========================================================================//
    //Checa qual tecla foi pressionada para mover a peça
    private void checkTypedKeys() {
        //LEFT
        if (KeyboardInput.typed(KeyEvent.VK_LEFT)) {
            moveTiles(Direction.LEFT);
            if (!gameStarted) {
                gameStarted = true;
            }
        }
        if (KeyboardInput.typed(KeyEvent.VK_A)) {
            moveTiles(Direction.LEFT);
            if (!gameStarted) {
                gameStarted = true;
            }
        }

        //RIGHT
        if (KeyboardInput.typed(KeyEvent.VK_RIGHT)) {
            moveTiles(Direction.RIGHT);
            if (!gameStarted) {
                gameStarted = true;
            }
        }
        if (KeyboardInput.typed(KeyEvent.VK_D)) {
            moveTiles(Direction.RIGHT);
            if (!gameStarted) {
                gameStarted = true;
            }
        }

        //UP
        if (KeyboardInput.typed(KeyEvent.VK_UP)) {
            moveTiles(Direction.UP);
            if (!gameStarted) {
                gameStarted = true;
            }
        }
        if (KeyboardInput.typed(KeyEvent.VK_W)) {
            moveTiles(Direction.UP);
            if (!gameStarted) {
                gameStarted = true;
            }
        }

        //DOWN
        if (KeyboardInput.typed(KeyEvent.VK_DOWN)) {
            moveTiles(Direction.DOWN);
            if (!gameStarted) {
                gameStarted = true;
            }
        }
        if (KeyboardInput.typed(KeyEvent.VK_S)) {
            moveTiles(Direction.DOWN);
            if (!gameStarted) {
                gameStarted = true;
            }
        }
    }

    //========================GETTERS e SETTERS===============================//
    public int getTileX(int col) {
        return SPACING + col * Tile.WIDTH + col * SPACING;
    }

    public int getTileY(int row) {
        return SPACING + row * Tile.HEIGHT + row * SPACING;
    }
}

package game_2048;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Gameboard {

    public static final int ROWS = 4;
    public static final int COLS = 4;

    private final int startingTile = 2; //number of tiles spawning in the begining
    private Tile[][] board;
    private boolean dead;   //loose
    private boolean won;    //win
    private BufferedImage gameBoard;//background of the gameboard -gray background etc
    private BufferedImage finalBoard;
    private int x;
    private int y;

    private static int SPACING = 10; //SPACE BETWEEN TILES IN PIXELS
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH; //COLUNAS +1 * ESPACO ENTRE TILES + COLUNAS * COMPRIMENTO DE TILES
    public static int BOARD_HEIGHT = (ROWS + 1) * SPACING + ROWS * Tile.HEIGHT;

    private boolean hasStarted;

    //constructor
    public Gameboard(int x, int y) {
        this.x = x;
        this.y = y;
        board = new Tile[ROWS][COLS];
        gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        finalBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        createBoardImage();
        start();
    }

    private void createBoardImage() {
        /*creat the background of the gameboard*/
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.lightGray);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = SPACING + SPACING * col + Tile.WIDTH * col;
                int y = SPACING + SPACING * row + Tile.HEIGHT * row;
                g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
            }
        }

    }

    private void start() {
        for (int i = 0; i < startingTile; i++) {
            spawnRandom();
        }

    }

    private void spawnRandom() {
        Random random = new Random();
        boolean notvalid = true;
        while (notvalid) {
            int location = random.nextInt(ROWS * COLS);
            int row = location / ROWS;        //THIS 2 IS TO GET THE LOCATION OF AN EMPTY SPACE
            int col = location % COLS;
            Tile currently = board[row][col];
            if (currently == null) {
                int value = random.nextInt(10) < 9 ? 2 : 4; //condense if statement 0 to 9 if its less than 9 going 2 else 4(90%-2 10%-4)
                Tile tile = new Tile(value, getTileX(col), getTileY(row));
                board[row][col] = tile;
                notvalid = false;
            }
        }
    }

    public int getTileX(int col) {
        return SPACING + col * Tile.WIDTH + col * SPACING;    //the space in pixels 
    }                                                       //lembrar o acesso de cada linha linha e apenas mudando a coluna

    public int getTileY(int row) {
        return SPACING + row * Tile.HEIGHT + row * SPACING; //lembrar o acesso de cada coluna se da por manter a coluna e mudar a linha
    }

    public void render(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) finalBoard.getGraphics();
        g2d.drawImage(gameBoard, 0, 0, null);

        //draw the tiles
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile currently = board[row][col];
                if (currently == null) {
                    continue;
                }
                currently.render(g2d);
            }
        }

        g.drawImage(finalBoard, x, y, null);
        g2d.dispose();
    }

    public void update() {
        checkKeys();

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) {
                    continue;
                }
                current.update();
                //reset the position
                resetPosition(current, row, col);
                if (current.getValue() == 2048) {
                    won = true;
                }
            }
        }
    }

    private void resetPosition(Tile current, int row, int col) {
        if (current == null) {
            return;
        }

        int x = getTileX(col);
        int y = getTileY(row);
        int distX = current.getX() - x;
        int distY = current.getY() - y;

        if (Math.abs(distX) < Tile.SLIDE_SPEED) {
            current.setX(current.getX() - distX); //para nao ter flic na animacao 
        }
        if (Math.abs(distY) < Tile.SLIDE_SPEED) {
            current.setY(current.getY() - distY); //para nao ter flic na animacao 
        }

        if (distX < 0) {
            current.setX(current.getX() + Tile.SLIDE_SPEED);
        }
        if (distY < 0) {
            current.setY(current.getY() + Tile.SLIDE_SPEED);
        }
        if (distX > 0) {
            current.setX(current.getX() - Tile.SLIDE_SPEED);
        }
        if (distY > 0) {
            current.setY(current.getY() - Tile.SLIDE_SPEED);
        }
    }

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
            newCol += horizontalDirection;
            newRow += verticalDirection;
            if (checkOutOfBounds(dir, newRow, newCol)) {
                break;
            }
            if (board[newRow][newCol] == null) { //se esta vazio
                board[newRow][newCol] = current;
                board[newRow - verticalDirection][newCol - horizontalDirection] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
                canMove = true;
            } else if (board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].canCombine()) { //se da pra combinar
                board[newRow][newCol].setCanCombine(false);
                board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);
                canMove = true;
                board[newRow - verticalDirection][newCol - horizontalDirection] = null;
                board[newRow][newCol].setSlideTo(new Point(newRow, newCol));
                //    board[newRow][newCol].setCombineAnimation(true);
                //add to score
            } else {
                move = false;
            }
        }
        return canMove;
    }

    private boolean checkOutOfBounds(Direction dir, int row, int col) {
        if (dir == Direction.LEFT) {
            return col < 0;
        } else if (dir == Direction.RIGHT) {
            return col > COLS - 1;
        } else if (dir == Direction.UP) {
            return row < 0;
        } else if (dir == Direction.DOWN) {
            return row > ROWS - 1;
        }
        return false;
    }

    private void moveTiles(Direction dir) {
        boolean canMove = false; //if hit the direction its eable to do it
        int horizontalDirection = 0;
        int verticalDirection = 0;

        //da pra fazer com switch case
        if (dir == Direction.LEFT) {
            horizontalDirection = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {   //se nao foi movido ainda
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    } else {
                        move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                }
            }
        } else if (dir == Direction.RIGHT) {
            horizontalDirection = +1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = COLS - 1; col >= 0; col--) {
                    if (!canMove) {   //se nao foi movido ainda
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    } else {
                        move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                }
            }
        } else if (dir == Direction.UP) {
            verticalDirection = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {   //se nao foi movido ainda
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    } else {
                        move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                }
            }
        } else if (dir == Direction.DOWN) {
            verticalDirection = 1;
            for (int row = ROWS - 1; row >= 0; row--) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {   //se nao foi movido ainda
                        canMove = move(row, col, horizontalDirection, verticalDirection, dir);
                    } else {
                        move(row, col, horizontalDirection, verticalDirection, dir);
                    }
                }
            }
        } else { //this is like a catch
            System.out.println(dir + "is not a valid direction.");
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) {
                    continue;
                }
                current.setCanCombine(true);
            }
        }
        if (canMove) {
            spawnRandom();
            checkDead();
        }

    }

    private void checkDead() {
        //it will go at all tiles and check it`s surrounding to se if there is any possible combination
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == null) {
                    return;
                }
                if (checkSurroundingTiles(row, col, board[row][col])) {
                    return;
                }
            }
        }
        dead = true;
        //setHighScore(score);
    }

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
        if (col < COLS - 1) {
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

    private void checkKeys() {
        if (Keyboard.typed(KeyEvent.VK_LEFT)) {
            //move tiles left
            moveTiles(Direction.LEFT);
            if (!hasStarted) {
                hasStarted = true;
            }
        }
        if (Keyboard.typed(KeyEvent.VK_RIGHT)) {
            //move tiles right
            moveTiles(Direction.RIGHT);
            if (!hasStarted) {
                hasStarted = true;
            }
        }
        if (Keyboard.typed(KeyEvent.VK_UP)) {
            //move tiles up
            moveTiles(Direction.UP);
            if (!hasStarted) {
                hasStarted = true;
            }
        }
        if (Keyboard.typed(KeyEvent.VK_DOWN)) {
            //move tiles down
            moveTiles(Direction.DOWN);
            if (!hasStarted) {
                hasStarted = true;
            }
        }
    }
}

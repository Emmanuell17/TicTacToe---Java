import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TicTacToe implements Serializable {
    public static final char EMPTY = ' ';
    public static final char PLAYER_X = 'X';
    public static final char PLAYER_O = 'O';
    private char[][] board;
    private boolean isPlayerXTurn;
    private boolean isGameOver;
    private char winner;
    private List<String> moveHistory;

    public TicTacToe() {
        board = new char[3][3];
        moveHistory = new ArrayList<>();
        resetGame();
    }

    public void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = EMPTY;
            }
        }
        isPlayerXTurn = true;
        isGameOver = false;
        winner = EMPTY;
        moveHistory.clear();
    }

    public boolean makeMove(int row, int col) {
        if (isGameOver || board[row][col] != EMPTY) {
            return false;
        }
        board[row][col] = isPlayerXTurn ? PLAYER_X : PLAYER_O;
        moveHistory.add(row + "," + col);
        isPlayerXTurn = !isPlayerXTurn;
        checkGameOver();
        return true;
    }

    private void checkGameOver() {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != EMPTY && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                winner = board[i][0];
                isGameOver = true;
                return;
            }
            if (board[0][i] != EMPTY && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                winner = board[0][i];
                isGameOver = true;
                return;
            }
        }
        if (board[0][0] != EMPTY && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            winner = board[0][0];
            isGameOver = true;
        } else if (board[0][2] != EMPTY && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            winner = board[0][2];
            isGameOver = true;
        }

        // Check for a tie (board is full, no winner)
        boolean fullBoard = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY) {
                    fullBoard = false;
                    break;
                }
            }
        }
        if (fullBoard && winner == EMPTY) {
            isGameOver = true; // It's a tie
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public char getWinner() {
        return winner;
    }

    public char[][] getBoard() {
        return board;
    }

    public List<String> getMoveHistory() {
        return moveHistory;
    }

    public boolean isPlayerXTurn() {
        return isPlayerXTurn;
    }

    public void saveGame(String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this); // Save the entire game object
        }
    }

    public static TicTacToe loadGame(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (TicTacToe) in.readObject(); // Load the entire game object
        }
    }
}

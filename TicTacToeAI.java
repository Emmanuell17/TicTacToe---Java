import java.util.Random;

public class TicTacToeAI {

    public static int[] getNextMove(TicTacToe game) {
        Random rand = new Random();
        char[][] board = game.getBoard();

        // Try to make a move for AI (Player O)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == TicTacToe.EMPTY) {
                    return new int[] { i, j };  // Return first empty spot (basic AI)
                }
            }
        }

        // If no empty space is found (shouldn't happen in a valid game)
        return null;
    }
}
